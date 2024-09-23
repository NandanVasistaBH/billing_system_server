package com.telstra.billing_system.dto;

import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.model.Subscription.SubscriptionStatus;
import com.telstra.billing_system.model.Subscription.SubscriptionType;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private  SubscriptionType type;
    private SubscriptionStatus status;
    private Integer id,noOfDays;
    private double price;
    private String description;
    private Integer noOfActiveSubscribers;
    public SubscriptionDTO(Subscription sub,Integer noOfActiveSubscribers){
        this.type=sub.getType();
        this.status=sub.getStatus();
        this.id=sub.getId();
        this.noOfDays=sub.getNoOfDays();
        this.price=sub.getPrice();
        this.noOfActiveSubscribers=noOfActiveSubscribers;
    }
    
}
