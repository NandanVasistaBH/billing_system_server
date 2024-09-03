package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.service.SupplierService;
import com.telstra.billing_system.model.Supplier;

@RestController
public class SupplierController {
    @Autowired
    private SupplierService service;
        @PostMapping("/supplier/register")
    public ResponseEntity<String>  register(@RequestBody Supplier supplier){
        if(supplier==null || supplier.getName()==null || supplier.getBranchPassword()==null || supplier.getBranchEmail()==null || supplier.getBranchPhoneNo()==null){
            return new ResponseEntity<>("need to provide both name,password,phone and email",HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( service.register(supplier),HttpStatus.OK);
    }
    @PostMapping("/supplier/login")
    public ResponseEntity<String> login(@RequestBody Supplier supplier){
        if(supplier==null || supplier.getName()==null || supplier.getBranchPassword()==null){
            return new ResponseEntity<>("need to provide both name and password",HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(service.verify(supplier),HttpStatus.OK);
    }
    @GetMapping("/supplier/hello")
    public String hello(){
        return "hello";
    }
}