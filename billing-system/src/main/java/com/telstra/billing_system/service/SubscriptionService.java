package com.telstra.billing_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.model.Subscription.SubscriptionType;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.repository.SubscriptionRepository;

@Qualifier("SubscriptionService")
@Service
public class SubscriptionService {
    private static final String ADMIN="admin";
    @Qualifier("SubscriptionRepository")
    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;

    public boolean create(Subscription subscription, String authorizationHeader) {
       try {
            String token = extractToken(authorizationHeader);
            System.out.println(token);
            if(jwtService.isTokenExpired(token)){
                return false;
            }
            System.out.println("token not expired");
            if(!jwtService.extractUsername(token).equals(ADMIN)){
                return false;
            }
            subscriptionRepo.save(subscription);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepo.findAll();
    }

    // Method to get all postpaid subscriptions
    public List<Subscription> getAllPostpaidSubscriptions() {
        return subscriptionRepo.findByType(SubscriptionType.POSTPAID);
    }

    // Method to get all prepaid subscriptions
    public List<Subscription> getAllPrepaidSubscriptions() {
        return subscriptionRepo.findByType(SubscriptionType.PREPAID);
    }
    
}
