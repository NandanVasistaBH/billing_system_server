package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Integer id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice.orElse(null);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice updateInvoice(Integer id, Invoice invoiceDetails) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setCustomer(invoiceDetails.getCustomer());
            invoice.setSupplier(invoiceDetails.getSupplier());
            invoice.setSubscription(invoiceDetails.getSubscription());
            invoice.setPayment(invoiceDetails.getPayment());
            invoice.setAmountPaid(invoiceDetails.getAmountPaid());
            invoice.setAmountDue(invoiceDetails.getAmountDue());
            invoice.setTax(invoiceDetails.getTax());
            invoice.setInvoiceIssueDate(invoiceDetails.getInvoiceIssueDate());
            invoice.setDueDate(invoiceDetails.getDueDate());
            return invoiceRepository.save(invoice);
        }
        return null;
    }

    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }
}
