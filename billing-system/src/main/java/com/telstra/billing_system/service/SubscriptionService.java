package com.telstra.billing_system.service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.telstra.billing_system.dto.SubscriptionDTO;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.repository.InvoiceRepository;
import com.telstra.billing_system.repository.SubscriptionRepository;
import com.telstra.billing_system.repository.UserRepository;
import com.telstra.billing_system.utils.ExtractJwtToken;

@Service("SubscriptionService")
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private JwtService jwtService;

    @Qualifier("UserRepository")
    @Autowired
    private UserRepository userRepository;

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String MASTER_ADMIN_ROLE = "MASTER_ADMIN";
    public boolean create(Subscription subscription, String authorizationHeader) {
        try {
             String token = ExtractJwtToken.extractToken(authorizationHeader);
             if(jwtService.isTokenExpired(token)){
                 return false;
             }
             User user =  userRepository.findByName(jwtService.extractUsername(token));
             if(user==null || !user.getRole().equals(ADMIN_ROLE)){
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
     // only those subscription which are are LIVE
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepo.findByStatus(Subscription.SubscriptionStatus.LIVE);
    }
    // MASTER_ADMIN can access all subscription of all status
    public List<SubscriptionDTO> getAllSubscriptionsOfAllTypes(String authorizationHeader ){
        try {
            String token = ExtractJwtToken.extractToken(authorizationHeader);
             if(jwtService.isTokenExpired(token)){
                 return null;
             }
             
             User user =  userRepository.findByName(jwtService.extractUsername(token));
             if(user==null || !user.getRole().equals(MASTER_ADMIN_ROLE)){
                 return null;
             }
             List<Subscription> list= subscriptionRepo.findAll();
             List<SubscriptionDTO> resp = new ArrayList<>();
             for(Subscription sub : list){
                Integer noOfActiveSub = invoiceRepo.countActiveSubscribersBySubscriptionId(sub.getId());
                resp.add(new SubscriptionDTO(sub, noOfActiveSub));
             }
             return resp;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
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
            User user =  userRepository.findByName(jwtService.extractUsername(token));
            System.out.println(user);
            if(user!=null && (user.getRole().equals(ADMIN_ROLE) || user.getRole().equals(MASTER_ADMIN_ROLE))){
                Optional<Subscription> subscription = subscriptionRepo.findById(id);
                System.out.println("delete this sub"+subscription);
                if (subscription.isPresent()) {
                    Subscription sub = subscription.get();
                    if(user.getRole().equals(ADMIN_ROLE)) sub.setStatus(Subscription.SubscriptionStatus.PENDING_FOR_APPROVAL_CLOSED);
                    else if(user.getRole().equals(MASTER_ADMIN_ROLE)) sub.setStatus(Subscription.SubscriptionStatus.CLOSED);
                    else return false;
                    subscriptionRepo.save(sub);
                    return true;
                }
            }
            else return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean approve(Integer subscriptionId, String authorizationHeader) {
        try {
             String token = ExtractJwtToken.extractToken(authorizationHeader);
             if(jwtService.isTokenExpired(token)){
                 return false;
             }
             User user =  userRepository.findByName(jwtService.extractUsername(token));
             System.out.println("MASTER_ADMIN"+user);
             if(user==null || !user.getRole().equals(MASTER_ADMIN_ROLE)){
                 return false;
             }
             Optional<Subscription> optional = subscriptionRepo.findById(subscriptionId);
             if(optional.isEmpty()) return false;
             Subscription subscription = optional.get();
             subscription.setStatus(Subscription.SubscriptionStatus.LIVE);
             subscriptionRepo.save(subscription);
             return true;
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return false;
     }
     public boolean reject(Integer subscriptionId, String authorizationHeader) {
        try {
             String token = ExtractJwtToken.extractToken(authorizationHeader);
             if(jwtService.isTokenExpired(token)){
                 return false;
             }
             User user =  userRepository.findByName(jwtService.extractUsername(token));
             if(user==null || !user.getRole().equals(MASTER_ADMIN_ROLE)){
                 return false;
             }
             Optional<Subscription> optional = subscriptionRepo.findById(subscriptionId);
             if(optional.isEmpty()) return false;
             Subscription subscription = optional.get();
             subscription.setStatus(Subscription.SubscriptionStatus.CLOSED);
             subscriptionRepo.save(subscription);
             return true;
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return false;
     }
}
