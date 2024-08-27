package com.telstra.billing_system.model;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Integer custId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cust_email", nullable = false, unique = true)
    private String custEmail;

    @Column(name = "cust_phone_no")
    private String custPhoneNo;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Supplier supplier;

    @Column(name = "cust_password", nullable = false)
    private String custPassword;

    @OneToMany(mappedBy = "customer")
    private Set<Invoice> invoices;
}
