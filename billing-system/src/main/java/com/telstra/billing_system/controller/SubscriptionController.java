package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.service.SubscriptionService;

@RestController
public class SubscriptionController {
    @Qualifier("SubscriptionService")
    @Autowired
    private SubscriptionService service;
    @PostMapping("/create-subscription")
    public ResponseEntity<String> createSubscription(@RequestBody Subscription subscription, @RequestHeader("Authorization") String authorizationHeader){
        if(subscription==null || subscription.getType()==null || subscription.getPrice()==null || subscription.getNoOfDays()==null){
            return new ResponseEntity<>("Insuffient information provided",HttpStatus.BAD_REQUEST);
        }
        boolean result = service.create(subscription,authorizationHeader);

        if(result)return new ResponseEntity<>("Created",HttpStatus.OK);
        return new ResponseEntity<>("only ADMIN can create",HttpStatus.UNAUTHORIZED);
    }
}
