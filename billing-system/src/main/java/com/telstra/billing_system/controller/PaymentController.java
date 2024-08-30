package com.telstra.billing_system.controller;

import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Payment;
import com.telstra.billing_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        System.out.println("hiuuuuuu");
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @PutMapping("/{payId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable String payId, @RequestBody Payment paymentDetails) {
        return ResponseEntity.ok(paymentService.updatePayment(payId, paymentDetails));
    }

    @DeleteMapping("/{payId}")
    public ResponseEntity<Void> deletePayment(@PathVariable String payId) {
        paymentService.deletePayment(payId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{payId}")
    public ResponseEntity<Payment> getPayment(@PathVariable String payId) {
        System.out.println("hiuuuuuu");
        return ResponseEntity.ok(paymentService.getPayment(payId));
    }

    @GetMapping("/{payId}/invoices")
    public ResponseEntity<Set<Invoice>> getInvoicesForPayment(@PathVariable String payId) {
        return ResponseEntity.ok(paymentService.getInvoicesForPayment(payId));
    }
}
