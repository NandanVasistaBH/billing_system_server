package com.telstra.billing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    public UserRepository userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    // public User registerCustomer(Customer cust){
    //     User user = new User();
    //     user.setName(cust.getName());
    //     user.setRole("CUSTOMER_USER");
    //     user.setPassword(encoder.encode(cust.getPassword()));
    //     return userRepo.save(user);
    // }    
    // public User registerAdmin(Admin admin){
    //     User user = new User();
    //     user.setUsername(admin.getName());
    //     user.setRole("ADMIN_USER");
    //     user.setPassword(encoder.encode(admin.getPassword()));
    //     return userRepo.save(user);
    // }    
    // public User registerSupplier(Supplier supplier){
        // User user = new User();
        // user.setUsername(supplier.getName());
        // user.setRole("SUPPLIER_USER");
        // user.setPassword(encoder.encode(supplier.getBranchPassword()));
        // return userRepo.save(user);
    // }    
}