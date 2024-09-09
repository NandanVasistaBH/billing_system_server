package com.telstra.billing_system.service; 

import com.telstra.billing_system.model.Payment;
import com.telstra.billing_system.model.Payment.PaymentGateway;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.telstra.billing_system.repository.PaymentRepository; 
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime; 

@Service public class PaymentService 
{   
    @Qualifier("PaymentRepository")
    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${razorpay.key.id}")
    private String razorPayKey;
    
    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    private RazorpayClient client;
    
    public Payment addPayment(String razorpayPaymentId) throws Exception
    {

        RazorpayClient razorpay =  new RazorpayClient(razorPayKey,razorPaySecret);
        com.razorpay.Payment rzPay = razorpay.payments.fetch(razorpayPaymentId);
        Payment payment = new Payment();
        // Double amountt = new Double(amount);
        System.out.println(rzPay);
        String amount = rzPay.get("amount") + "";
        System.out.println(amount);
        if(rzPay.get("amount") instanceof Double) {
            System.out.println("double");
        } else {
            System.out.println("not double");
        }
        payment.setAmountPaid(Double.valueOf(amount)/100);
        payment.setStatus(rzPay.get("status"));
        payment.setPaymentMethod(rzPay.get("method"));
        payment.setPaymentGateway(PaymentGateway.RazorPay);
        payment.setTransactionDate(LocalDateTime.now());
        paymentRepository.save(payment);
        return payment;
    }

    public Payment createPayment(Payment payment) throws Exception
    {
        JSONObject paymentRequest=new JSONObject();

        paymentRequest.put("amount", payment.getAmountPaid()*100);
        paymentRequest.put("currency", "INR");
        // paymentRequest.put("receipt", paymentRepository.findCustomerEmailByCustomerId());

        this.client=new RazorpayClient(razorPayKey,razorPaySecret);

        Order razorPayPayment = client.orders.create(paymentRequest);

        System.out.println(razorPayPayment);
        payment.setStatus(razorPayPayment.get("status"));
        

        System.out.println(razorPayPayment);

        // paymentRepository.save(payment);

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