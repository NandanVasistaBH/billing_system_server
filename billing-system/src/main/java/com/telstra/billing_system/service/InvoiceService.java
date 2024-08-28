package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Integer invoiceId, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

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

    public void deleteInvoice(Integer invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    public Invoice getInvoice(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }
}
