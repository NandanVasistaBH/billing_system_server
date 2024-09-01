package com.telstra.billing_system.controller;

import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Payment;
import com.telstra.billing_system.service.PaymentService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class PaymentController 
{
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/")
    public String init() 
    {
        return "index";
    }
    
    @PostMapping(value="/create",produces="application/json")
    @ResponseBody
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) throws Exception
    {
        System.out.println("hiuuuuuu");
        Payment createedPayment = paymentService.createPayment(payment);
        return new ResponseEntity<>(createedPayment, HttpStatus.CREATED);
    }

    @PutMapping("/{payId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Integer payId, @RequestBody Payment paymentDetails) 
    {
        return ResponseEntity.ok(paymentService.updatePayment(payId, paymentDetails));
    }
    
    @DeleteMapping("/{payId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer payId) 
    {
        paymentService.deletePayment(payId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{payId}")
    public ResponseEntity<Payment> getPayment(@PathVariable Integer payId) 
    {
        System.out.println("hiuuuuuu");
        return ResponseEntity.ok(paymentService.getPayment(payId));
    }

    

}