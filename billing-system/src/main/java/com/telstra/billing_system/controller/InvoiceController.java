package com.telstra.billing_system.controller;

import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create-postpaid")
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice) {
        try {
            String resp = invoiceService.createInvoice(invoice);
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable("id") Integer id) {
        InvoiceResponseDTO resp= invoiceService.getInvoiceById(id);
        if(resp==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
}
