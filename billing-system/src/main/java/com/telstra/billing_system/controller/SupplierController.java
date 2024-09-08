package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.service.SupplierService;
import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.dto.SupplierDTO;
import com.telstra.billing_system.model.Supplier;
import java.util.List;
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Qualifier("SupplierService")
    @Autowired
    private SupplierService service;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Supplier supplier){
        System.out.println(supplier);
        if(supplier.getUser()==null || supplier.getUser().getName()==null || supplier.getUser().getPassword()==null || !supplier.getUser().getRole().equals("SUPPLIER")){
            return new ResponseEntity<>("insuffient information provided for supplier creation",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.register(supplier),HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Supplier supplier){
        if(supplier.getUser()==null || supplier.getUser().getName()==null || supplier.getUser().getPassword()==null || !supplier.getUser().getRole().equals("SUPPLIER")){
            return new ResponseEntity<>("insuffient information provided for supplier verification",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.verify(supplier),HttpStatus.OK);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/list-of-customers")
    public ResponseEntity<List<CustomerDTO>> listOfCustomerOfASupplier(@RequestParam String supplierId) {
        System.out.println("List of customers for supplier id: " + supplierId);
        if (supplierId == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        List<CustomerDTO> customers = service.listOfCustomerOfASupplier(Integer.parseInt(supplierId));
        if (customers == null || customers.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/me")
    public ResponseEntity<SupplierDTO> getSupplierFromName(@RequestParam String name) {
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        SupplierDTO resp = service.getSupplierFromName(name);
        if (resp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}