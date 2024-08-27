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

    @OneToMany(mappedBy = "supplier")
    private Set<Customer> customers;

    @OneToMany(mappedBy = "supplier")
    private Set<Invoice> invoices;
}
