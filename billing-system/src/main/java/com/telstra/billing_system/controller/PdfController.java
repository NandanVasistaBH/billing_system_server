package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.telstra.billing_system.service.PdfService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
 
@Controller
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;
 
    @GetMapping("/create")
    public ResponseEntity<String> createPdf(@RequestParam Integer invoiceId){
        if(invoiceId==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        String pdf= pdfService.createPdf(invoiceId);
        if(pdf==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        System.out.println("s3 link "+pdf);
        return new ResponseEntity<>(pdf,HttpStatus.OK);
    }
}