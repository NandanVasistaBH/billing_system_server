package com.telstra.billing_system.service;

import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.dto.SupplierDTO;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("InvoiceService")
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public String createInvoice(Invoice invoice) {
        try {
            if(invoice.getAmountPaid()!=0) return "can't pre pay in a postpaid service plan";
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
        return new InvoiceResponseDTO(customerDTO, supplierDTO, invoice.getSubscription(), invoice);
        
    }
    public List<InvoiceResponseDTO> getAllInvoiceByCustomerID(Integer customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        
        return invoices.stream().map(invoice -> {
            CustomerDTO customerDTO = new CustomerDTO(invoice.getCustomer());
            SupplierDTO supplierDTO = new SupplierDTO(invoice.getSupplier());
            return new InvoiceResponseDTO(customerDTO, supplierDTO, invoice.getSubscription(), invoice);
        }).collect(Collectors.toList());
    }
}
