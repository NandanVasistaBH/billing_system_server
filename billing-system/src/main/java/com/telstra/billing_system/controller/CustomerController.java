package com.telstra.billing_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.Subscription;
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
    @GetMapping("/details-from-token")
    public ResponseEntity<CustomerDTO> getCustomerFromToken() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username;
    if (principal instanceof UserDetails) {
        username = ((UserDetails) principal).getUsername();
    } else {
        username = principal.toString();
    }
 
    if (username == null || username.isEmpty()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    CustomerDTO resp = service.getCustomerFromName(username);
    if (resp == null) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(resp, HttpStatus.OK);
}
    @GetMapping("/latest-subscription")
    public ResponseEntity<Subscription> getLastSubscriptionOfACustomer(@RequestParam String customerId){
        System.out.println(customerId+" lastest subs");
        if(customerId==null)  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Subscription resp = service.getLastSubscriptionOfACustomer(customerId);
        if (resp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    @GetMapping("is-name-unique")
    public ResponseEntity<Boolean> isNameUnique(@RequestParam String name){
        if(name==null) return new ResponseEntity<>(Boolean.FALSE,HttpStatus.NOT_FOUND);// empty string not allowed
        return new ResponseEntity<>( service.isNameUnique(name),HttpStatus.OK);
    }

}