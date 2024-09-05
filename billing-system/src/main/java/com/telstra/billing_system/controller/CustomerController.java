package com.telstra.billing_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.service.CustomerService;
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer cust){
        System.out.println(cust);
        if(cust.getUser()==null || cust.getUser().getName()==null || cust.getUser().getPassword()==null || !cust.getUser().getRole().equals("CUSTOMER")){
            return new ResponseEntity<>("insuffient information provided for customer creation",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.register(cust),HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer cust){
        if(cust.getUser()==null || cust.getUser().getName()==null || cust.getUser().getPassword()==null || !cust.getUser().getRole().equals("CUSTOMER")){
            return new ResponseEntity<>("insuffient information provided for customer verification",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.verify(cust),HttpStatus.OK);
    }
    @GetMapping("/hello-world")
    public String helloWorld(){
        return "hello world";
    }
    @GetMapping("/me")
    public ResponseEntity<CustomerDTO> getCustomerFromName(@RequestParam String name) {
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        CustomerDTO resp = service.getCustomerFromName(name);
        if (resp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


}