package com.telstra.billing_system.dto;

import com.telstra.billing_system.model.Supplier;

import lombok.Data;

@Data
public class SupplierDTO {
    private String name,branchLoc,branchManager,branchEmail,branchPhoneNo;
    public SupplierDTO(Supplier supplier){
        this.name=supplier.getUser().getName();
        this.branchLoc=supplier.getBranchLoc();
        this.branchEmail=supplier.getBranchEmail();
        this.branchManager=supplier.getBranchManager();
        this.branchPhoneNo=supplier.getBranchPhoneNo();
    }

}
