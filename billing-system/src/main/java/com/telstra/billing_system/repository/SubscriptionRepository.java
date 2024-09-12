package com.telstra.billing_system.repository;

import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.model.Subscription.SubscriptionType;
import com.telstra.billing_system.model.Subscription.SubscriptionStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SubscriptionRepository")
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findByType(SubscriptionType type);

    List<Subscription> findByStatus(SubscriptionStatus status);
}
