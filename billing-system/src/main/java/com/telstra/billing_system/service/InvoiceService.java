package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Invoice> getInvoiceById(Integer id) {
        return invoiceRepository.findById(id);
    }
}
