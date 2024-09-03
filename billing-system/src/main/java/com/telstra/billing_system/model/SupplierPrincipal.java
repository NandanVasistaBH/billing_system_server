package com.telstra.billing_system.model;
import java.util.Collection;
import java.util.Collections;
import com.telstra.billing_system.model.Supplier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class SupplierPrincipal  implements UserDetails {

    private final Supplier supplier;

    public SupplierPrincipal(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("SUPPLIER")); 
    }

    @Override
    public String getPassword() {
        return supplier.getBranchPassword();
    }

    @Override
    public String getUsername() {
        return supplier.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement as needed
    }
}
