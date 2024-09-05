package com.telstra.billing_system.controller;

import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.service.InvoiceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Qualifier("InvoiceService")
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create-postpaid")
    public ResponseEntity<String> createPostpaidInvoice(@RequestBody Invoice invoice) {
        try {
            String resp = invoiceService.createPostpaidInvoice(invoice);
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create-prepaid")
    public ResponseEntity<String> createPrepaidInvoice(@RequestBody Invoice invoice) {
        try {
            String resp = invoiceService.createPrepaidInvoice(invoice);
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
    @GetMapping("/all-invoice-customer/{id}")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoiceByCustomerId(@PathVariable("id") Integer id) {
        List<InvoiceResponseDTO> resp= invoiceService.getAllInvoiceByCustomerID(id);
        if(resp==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
}

