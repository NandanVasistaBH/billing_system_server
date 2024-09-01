package com.telstra.billing_system.service;

import java.util.Optional;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription getSubscriptionById(Integer id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        return subscription.orElse(null);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription updateSubscription(Integer id, Subscription subscriptionDetails) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            subscription.setSubscriptionType(subscriptionDetails.getSubscriptionType());
            subscription.setServiceDays(subscriptionDetails.getServiceDays());
            subscription.setNoOfDays(subscriptionDetails.getNoOfDays());
            return subscriptionRepository.save(subscription);
        }
        return null;
    }

    public void deleteSubscription(Integer id) {
        subscriptionRepository.deleteById(id);
    }
}