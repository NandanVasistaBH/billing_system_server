package com.telstra.billing_system.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.repository.SubscriptionRepository;
import com.telstra.billing_system.utils.ExtractJwtToken;

@Service("SubscriptionService")
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @Autowired
    private JwtService jwtService;

    private static final String ADMIN = "admin";
    public boolean create(Subscription subscription, String authorizationHeader) {
        try {
             String token = ExtractJwtToken.extractToken(authorizationHeader);
             System.out.println(token);
             if(jwtService.isTokenExpired(token)){
                 return false;
             }
             System.out.println("token not expired");
             if(!jwtService.extractUsername(token).equals(ADMIN)){
                 return false;
             }
             subscription.setStatus(Subscription.SubscriptionStatus.PENDING_FOR_APPROVAL_LIVE);
             subscriptionRepo.save(subscription);
             return true;
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return false;
     }
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepo.findByStatus(Subscription.SubscriptionStatus.LIVE);
    }
    public List<Subscription> getAllPostpaidSubscriptions() {
        List<Subscription> postpaidSubscriptions = subscriptionRepo.findByType(Subscription.SubscriptionType.POSTPAID);
        return postpaidSubscriptions.stream()
                                    .filter(subscription -> subscription.getStatus() == Subscription.SubscriptionStatus.LIVE)
                                    .toList();
    }
    public List<Subscription> getAllPrepaidSubscriptions() {
        List<Subscription> prepaidSubscriptions = subscriptionRepo.findByType(Subscription.SubscriptionType.PREPAID);
        return prepaidSubscriptions.stream()
                                   .filter(subscription -> subscription.getStatus() == Subscription.SubscriptionStatus.LIVE)
                                   .toList();
    }
    public boolean deleteSubscription(Integer id, String authorizationHeader) {
        try {
            String token = ExtractJwtToken.extractToken(authorizationHeader);
            if (jwtService.isTokenExpired(token)) {
                return false;
            }
            System.out.println(jwtService.extractUsername(token));
            if (!jwtService.extractUsername(token).equals(ADMIN)) {
                return false;
            }
            Optional<Subscription> subscription = subscriptionRepo.findById(id);
            System.out.println("delete this sub"+subscription);
            if (subscription.isPresent()) {
                Subscription sub = subscription.get();
                sub.setStatus(Subscription.SubscriptionStatus.PENDING_FOR_APPROVAL_CLOSED);
                subscriptionRepo.save(sub);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
