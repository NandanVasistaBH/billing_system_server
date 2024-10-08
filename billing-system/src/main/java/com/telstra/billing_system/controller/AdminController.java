package com.telstra.billing_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Admin;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.model.AdminSubscriptionRequest;
import com.telstra.billing_system.service.AdminService;
@RestController
public class AdminController {
    @Autowired
    private AdminService service;
    
    @PostMapping("/admin/login")
    public ResponseEntity<String> login(@RequestBody Admin admin){
        if(admin==null || admin.getName()==null || admin.getPassword()==null){
            return new ResponseEntity<>("need to provide both name and password",HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(service.verify(admin),HttpStatus.OK);
    }
    @GetMapping("/admin/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/admin/create-subscription")
    public ResponseEntity<String> createSubscription(@RequestBody AdminSubscriptionRequest req){
        if(req==null){
            return new ResponseEntity<>("insuffient data",HttpStatus.BAD_GATEWAY);
        }
        else return new ResponseEntity<>(service.createSubscription(req.getAdmin(),req.getSubscription()),HttpStatus.OK);
    }
}
