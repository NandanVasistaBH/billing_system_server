package com.telstra.billing_system.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Admin;
import com.telstra.billing_system.repository.AdminRepository;
import com.telstra.billing_system.repository.UserRepository;

@Service
public class AdminService {
    @Qualifier("UserRepository")
    @Autowired
    private UserRepository userRepo;

    @Qualifier("AdminRepository")
    @Autowired
    private AdminRepository adminRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;

    public String verifyMasterAdmin(Admin admin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getUser().getName(), admin.getUser().getPassword())
            );
            System.out.println(admin.getUser().getName());
            if(!admin.getUser().getRole().equals("MASTER_ADMIN")){
                // HARDCODED
                return "only adminmaster can login";
            }
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(admin.getUser().getName());
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }
    public String verifyAdmin(Admin admin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getUser().getName(), admin.getUser().getPassword())
            );
            System.out.println(admin.getUser().getName());
            if(!admin.getUser().getRole().equals("ADMIN")){
                // HARDCODED
                return "only admin can login";
            }
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(admin.getUser().getName());
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;    
    }
}
