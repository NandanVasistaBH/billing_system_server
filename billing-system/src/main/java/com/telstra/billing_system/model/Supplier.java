package com.telstra.billing_system.model;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Supplier")
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "branch_loc", nullable = false)
    private String branchLoc;

    @Column(name = "branch_manager", nullable = false)
    private String branchManager;

    @Column(name = "branch_email", nullable = false, unique = true)
    private String branchEmail;

    @Column(name = "branch_phone_no")
    private String branchPhoneNo;

    @Column(name = "branch_password", nullable = false)
    private String branchPassword;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "supplier")
    private Set<Customer> customers;

    @OneToMany(mappedBy = "supplier")
    private Set<Invoice> invoices;

    public String getName(){
        return this.name;
    }
    public String getBranchPassword(){
        return this.branchPassword;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setBranchPassword(String password){
        this.branchPassword=password;
    }
    public Supplier(Integer branchId, String branchLoc, String branchManager, String branchEmail, String branchPhoneNo,
            String branchPassword, String name, Set<Customer> customers, Set<Invoice> invoices) {
        this.branchId = branchId;
        this.branchLoc = branchLoc;
        this.branchManager = branchManager;
        this.branchEmail = branchEmail;
        this.branchPhoneNo = branchPhoneNo;
        this.branchPassword = branchPassword;
        this.name = name;
        this.customers = customers;
        this.invoices = invoices;
    }
    
}
