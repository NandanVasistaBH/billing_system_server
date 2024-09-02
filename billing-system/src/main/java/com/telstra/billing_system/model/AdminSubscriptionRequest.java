package com.telstra.billing_system.model;
public class AdminSubscriptionRequest {
    private Admin admin;
    private Subscription subscription;
    public AdminSubscriptionRequest(){

    }
    public Admin getAdmin() {
        return admin;
    }
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public Subscription getSubscription() {
        return subscription;
    }
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
    public AdminSubscriptionRequest(Admin admin,Subscription subscription){
        this.admin=admin;
        this.subscription=subscription;
    }
}
