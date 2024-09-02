package com.telstra.billing_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.password;
    }
    public void setName(String name){
         this.name=name;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public Admin() {
    }
    public Admin(String name,String password){
        this.name=name;
        this.password=password;
    }
    

}
