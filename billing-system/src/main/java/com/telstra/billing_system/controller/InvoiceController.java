package com.telstra.billing_system.controller;

import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceService.createInvoice(invoice));
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Integer invoiceId, @RequestBody Invoice invoiceDetails) {
        return ResponseEntity.ok(invoiceService.updateInvoice(invoiceId, invoiceDetails));
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Integer invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoice(invoiceId));
    }
}
