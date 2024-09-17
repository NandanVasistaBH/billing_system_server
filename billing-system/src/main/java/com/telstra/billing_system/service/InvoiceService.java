package com.telstra.billing_system.service;

import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.dto.SupplierDTO;
import com.telstra.billing_system.exceptions.MailException;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.repository.InvoiceRepository;
import com.telstra.billing_system.repository.SubscriptionRepository;
import com.telstra.billing_system.utils.ExtractJwtToken;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("InvoiceService")
public class InvoiceService {
    private static final int NUMBER_OF_TOP_SUBSCRIPTION=3;
    @Qualifier("InvoiceRepository")
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    public MailSender mailSender;

    @Qualifier("SubscriptionRepository")
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private JwtService jwtService;
    public String createPostpaidInvoice(Invoice invoice) {
        try {
            if(invoice.getAmountPaid()!=0) return "can't pre pay in a postpaid service plan";
            invoiceRepository.save(invoice);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "failure";
        }
    }
    public String updatePostpaidInvoice(Invoice invoice) {
        try {
            Optional<Invoice> existingInvoice = invoiceRepository.findById(invoice.getId());
            if (!existingInvoice.isPresent()) {
                return "Invoice not found";
            }
            Invoice invoiceToUpdate = existingInvoice.get();
            invoiceToUpdate.setAmountPaid(invoice.getAmountPaid());
            invoiceRepository.save(invoiceToUpdate);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "failure";
        }
    }
    public String createPrepaidInvoice(Invoice invoice) {
        try {
            System.out.println("invoice    --->  "+invoice);
            Optional<Subscription> optionalSubscription = subscriptionRepository.findById(invoice.getSubscription().getId());
            if(optionalSubscription.isEmpty()) return "no subscription like that exists";
            Subscription subscription = optionalSubscription.get();
            double amountPaid = invoice.getAmountPaid();
            double subscriptionPrice = subscription.getPrice();
            if(subscriptionPrice!=amountPaid) return "amount not paid correctly";
            invoiceRepository.save(invoice);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    public InvoiceResponseDTO getInvoiceById(Integer id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            return null;
        }
        Invoice invoice = optionalInvoice.get();
        CustomerDTO customerDTO = new CustomerDTO(invoice.getCustomer());
        SupplierDTO supplierDTO = new SupplierDTO(invoice.getSupplier());
        InvoiceResponseDTO response = new InvoiceResponseDTO(customerDTO, supplierDTO, invoice.getSubscription(), invoice);
        System.out.println(response.toString());
        try {
            mailSender.sendEmail(response.getCustomerDTO().getCustEmail(), "invoice from telstra", response.toString());
        } catch (MailException e) {
            // Log the error or handle it as needed
            System.err.println("Error sending email: " + e.getMessage());
        }
        catch (MessagingException e) {
            // Log the error or handle it as needed
            System.err.println("Error sending email: " + e.getMessage());
        }
        return response;
        
    }
    public List<InvoiceResponseDTO> getAllInvoiceByCustomerID(Integer customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        
        return invoices.stream().map(invoice -> {
            CustomerDTO customerDTO = new CustomerDTO(invoice.getCustomer());
            SupplierDTO supplierDTO = new SupplierDTO(invoice.getSupplier());
            return new InvoiceResponseDTO(customerDTO, supplierDTO, invoice.getSubscription(), invoice);
        }).collect(Collectors.toList());
    }
    public List<Subscription> getTopSubscription(){
        List<Object[]> results = invoiceRepository.findTopSubscriptions();
        return results.stream()
                      .limit(NUMBER_OF_TOP_SUBSCRIPTION)
                      .map(result -> (Subscription) result[0])
                      .collect(Collectors.toList());
    }
    public Integer getNumberOfActiveSubscribersOfASubscription(Subscription subscription,String authorizationHeader) {
        String token = ExtractJwtToken.extractToken(authorizationHeader);
        if(token==null || token.length()==0) return null;
        String username = jwtService.extractUsername(token);
        //hardcoded as there is one master admin
        if(username==null || username.length()==0 || !username.equals("adminmaster")) return null;
        return invoiceRepository.countActiveSubscribersBySubscriptionId(subscription.getId());
    }
    
}
