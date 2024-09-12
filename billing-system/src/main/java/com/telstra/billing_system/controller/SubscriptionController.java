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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.service.InvoiceService;
import com.telstra.billing_system.service.SubscriptionService;
@RequestMapping("/subscriptions")
@RestController
public class SubscriptionController {
    @Qualifier("SubscriptionService")
    @Autowired
    private SubscriptionService service;
    @Qualifier("InvoiceService")
    @Autowired
    private InvoiceService invoiceService;
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
    @GetMapping("/top")
    public ResponseEntity<List<Subscription>> getTopSubscriptions(){
        List<Subscription> resp =invoiceService.getTopSubscription();
        if(resp==null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
    @PutMapping("/approve-subscription")
    public ResponseEntity<String> approveSubscription(@RequestParam Integer subscriptionId, @RequestHeader("Authorization") String authorizationHeader){
        if(subscriptionId==null){
            return new ResponseEntity<>("Insuffient information provided",HttpStatus.BAD_REQUEST);
        }
        boolean result = service.approve(subscriptionId,authorizationHeader);

        if(result)return new ResponseEntity<>("Subscription is now live",HttpStatus.OK);
        return new ResponseEntity<>("only MASTER_ADMIN can aprove",HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/reject-subscription")
    public ResponseEntity<String> rejectSubscription(@RequestParam Integer subscriptionId, @RequestHeader("Authorization") String authorizationHeader){
        if(subscriptionId==null ){
            return new ResponseEntity<>("Insuffient information provided",HttpStatus.BAD_REQUEST);
        }
        boolean result = service.reject(subscriptionId,authorizationHeader);

        if(result)return new ResponseEntity<>("Subscription is rejected",HttpStatus.OK);
        return new ResponseEntity<>("only MASTER_ADMIN can reject",HttpStatus.UNAUTHORIZED);
    }
}
