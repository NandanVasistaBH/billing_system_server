package com.telstra.billing_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Admin;
import com.telstra.billing_system.service.AdminService;
@RestController
public class AdminController {
    @Autowired
    private AdminService service;
    
    @PostMapping("/admin/login")
    public ResponseEntity<String> login(@RequestBody Admin admin){
        if(admin.getUser()==null || admin.getUser().getName()==null || admin.getUser().getPassword()==null || !admin.getUser().getRole().equals("ADMIN")){
            return new ResponseEntity<>("insuffient information provided for customer verification",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.verify(admin),HttpStatus.OK);
    }
    
    @GetMapping("/admin/hello-world")
    public String helloWorld(){
        System.out.println("asdsadsadas");
        return "hello world";
    }
}
