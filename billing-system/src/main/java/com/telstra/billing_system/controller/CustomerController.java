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
    @PostMapping("/customer-register")
    public ResponseEntity<String>  register(@RequestBody Customer customer){
        if(customer==null || customer.getName()==null || customer.getCustPassword()==null || customer.getCustEmail()==null || customer.getCustPhoneNo()==null){
            return new ResponseEntity<>("need to provide both name,password,phone and email",HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( service.register(customer),HttpStatus.OK);
    }
    @PostMapping("/customer-login")
    public ResponseEntity<String> login(@RequestBody Customer customer){
        if(customer==null || customer.getName()==null || customer.getCustPassword()==null){
            return new ResponseEntity<>("need to provide both name and password",HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(service.verify(customer),HttpStatus.OK);
    }
    @GetMapping("/hello-world")
    public String helloWorld(){
        System.out.println("asdsadsadas");
        return "hello world";
    }
}
