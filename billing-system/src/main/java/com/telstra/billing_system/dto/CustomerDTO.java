package com.telstra.billing_system.dto;

import com.telstra.billing_system.model.Customer;

import lombok.Data;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String custEmail;
    private String custPhoneNo;
    public CustomerDTO(Customer customer){
        this.id=customer.getId();
        this.name=customer.getUser().getName();
        this.custEmail=customer.getCustEmail();
        this.custPhoneNo=customer.getCustPhoneNo();
    }
    
}
