package com.telstra.billing_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.service.CustomerService;
@RestController
public class CustomerController {
    @Autowired
    private CustomerService service;
    @PostMapping("/customer/register")
    public ResponseEntity<String> register(@RequestBody Customer cust){
        System.out.println(cust);
        if(cust.getUser()==null || cust.getUser().getName()==null || cust.getUser().getPassword()==null || !cust.getUser().getRole().equals("CUSTOMER")){
            return new ResponseEntity<>("insuffient information provided for customer creation",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.register(cust),HttpStatus.OK);
    }
    @PostMapping("/customer/login")
    public ResponseEntity<String> login(@RequestBody Customer cust){
        if(cust.getUser()==null || cust.getUser().getName()==null || cust.getUser().getPassword()==null || !cust.getUser().getRole().equals("CUSTOMER")){
            return new ResponseEntity<>("insuffient information provided for customer verification",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.verify(cust),HttpStatus.OK);
    }
    @GetMapping("/customer/hello-world")
    public String helloWorld(){
        System.out.println("asdsadsadas");
        return "hello world";
    }
}