package com.telstra.billing_system.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.service.SubscriptionService;
@RequestMapping("/subscriptions")
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
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = service.getAllSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/postpaid")
    public ResponseEntity<List<Subscription>> getAllPostpaidSubscriptions() {
        List<Subscription> subscriptions = service.getAllPostpaidSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/prepaid")
    public ResponseEntity<List<Subscription>> getAllPrepaidSubscriptions() {
        List<Subscription> subscriptions = service.getAllPrepaidSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
        boolean result = service.deleteSubscription(id, authorizationHeader);
        if (result) {
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Only ADMIN can delete or Subscription not found", HttpStatus.UNAUTHORIZED);
    }
}
