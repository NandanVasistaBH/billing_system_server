package com.telstra.billing_system.service; 

import com.telstra.billing_system.model.Payment;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.telstra.billing_system.model.Invoice; 
import com.telstra.billing_system.repository.PaymentRepository; 
import com.telstra.billing_system.repository.InvoiceRepository;

import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service; 
import java.math.BigDecimal; 
import java.time.LocalDate; 
import java.time.LocalDateTime; 
import java.util.Set; 

@Service public class PaymentService 
{
    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${razorpay.key.id}")
    private String razorPayKey;
    
    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    private RazorpayClient client;



    
    public Payment createPayment(Payment payment) throws Exception
    {
        JSONObject paymentRequest=new JSONObject();

        paymentRequest.put("amount", payment.getAmountPaid() * 100);
        paymentRequest.put("currency", "INR");
        // paymentRequest.put("receipt", paymentRepository.findCustomerEmailByCustomerId());

        this.client=new RazorpayClient(razorPayKey,razorPaySecret);

        Order razorPayPayment = client.orders.create(paymentRequest);

        System.out.println(razorPayPayment);

        payment.setRazorpayid(razorPayPayment.get("id"));
        payment.setStatus(razorPayPayment.get("status"));
        

        System.out.println(razorPayPayment);

        paymentRepository.save(payment);

        return payment;
    }

    public Payment updatePayment(Integer payId, Payment paymentDetails) 
    {
        Payment payment = paymentRepository.findById(payId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        payment.setPaymentGateway(paymentDetails.getPaymentGateway());
        payment.setAmountPaid(paymentDetails.getAmountPaid());
        payment.setStatus(paymentDetails.getStatus());
        payment.setLastUpdate(LocalDateTime.now());
        payment.setTransactionDate(paymentDetails.getTransactionDate());
        return paymentRepository.save(payment);
    }
    
    public void deletePayment(Integer payId) 
    {
        paymentRepository.deleteById(payId);
    }

    public Payment getPayment(Integer payId) 
    {
        System.out.println("hiuuuuuu");
        return paymentRepository.findById(payId).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<Payment> getAllPayment() 
    {
        return paymentRepository.findAll();
    }

}