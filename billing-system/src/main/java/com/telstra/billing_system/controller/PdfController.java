package com.telstra.billing_system.controller;
 
import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<InputStreamResource> createPdf(@RequestParam Integer invoiceId){
        if(invoiceId==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        ByteArrayInputStream pdf= pdfService.createPdf(invoiceId);
        if(pdf==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content_Disposition", "inline;file=invoice.pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
    }
}