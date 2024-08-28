package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Payment;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.repository.PaymentRepository;
import com.telstra.billing_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // @Autowired
    // private InvoiceRepository invoiceRepository;

    public Payment createPayment(Payment payment) {
        payment.setLastUpdate(LocalDateTime.now());
        System.out.println("service");
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(String payId, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(payId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentGateway(paymentDetails.getPaymentGateway());
        payment.setType(paymentDetails.getType());
        payment.setAmountPaid(paymentDetails.getAmountPaid());
        payment.setStatus(paymentDetails.getStatus());
        payment.setLastUpdate(LocalDateTime.now());
        payment.setTransactionDate(paymentDetails.getTransactionDate());

        return paymentRepository.save(payment);
    }

    public void deletePayment(String payId) {
        paymentRepository.deleteById(payId);
    }

    public Payment getPayment(String payId) {
        return paymentRepository.findById(payId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Set<Invoice> getInvoicesForPayment(String payId) {
        Payment payment = paymentRepository.findById(payId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return payment.getInvoices();
    }
}
