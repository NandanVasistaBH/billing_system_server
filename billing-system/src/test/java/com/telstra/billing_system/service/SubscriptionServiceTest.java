package com.telstra.billing_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.model.Subscription.SubscriptionType;
import com.telstra.billing_system.repository.SubscriptionRepository;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private Subscription sampleSubscription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleSubscription = new Subscription();
        sampleSubscription.setId(1);
        sampleSubscription.setType(SubscriptionType.POSTPAID);
        //sampleSubscription.setName("Sample Subscription");
    }

    @Test
    void testCreate_Success() {
        String authorizationHeader = "Bearer valid_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(false);
        when(jwtService.extractUsername(anyString())).thenReturn("admin");

        boolean result = subscriptionService.create(sampleSubscription, authorizationHeader);

        assertTrue(result);
        verify(subscriptionRepo, times(1)).save(sampleSubscription);
    }

    @Test
    void testCreate_TokenExpired() {
        String authorizationHeader = "Bearer expired_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(true);

        boolean result = subscriptionService.create(sampleSubscription, authorizationHeader);

        assertFalse(result);
        verify(subscriptionRepo, never()).save(any());
    }

    @Test
    void testCreate_InvalidUser() {
        String authorizationHeader = "Bearer valid_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(false);
        when(jwtService.extractUsername(anyString())).thenReturn("user");

        boolean result = subscriptionService.create(sampleSubscription, authorizationHeader);

        assertFalse(result);
        verify(subscriptionRepo, never()).save(any());
    }

    @Test
    void testGetAllSubscriptions() {
        when(subscriptionRepo.findAll()).thenReturn(Arrays.asList(sampleSubscription));

        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();

        assertNotNull(subscriptions);
        assertEquals(1, subscriptions.size());
        assertEquals(sampleSubscription, subscriptions.get(0));
    }

    @Test
    void testGetAllPostpaidSubscriptions() {
        when(subscriptionRepo.findByType(SubscriptionType.POSTPAID)).thenReturn(Arrays.asList(sampleSubscription));

        List<Subscription> subscriptions = subscriptionService.getAllPostpaidSubscriptions();

        assertNotNull(subscriptions);
        assertEquals(1, subscriptions.size());
        assertEquals(sampleSubscription, subscriptions.get(0));
    }

    @Test
    void testGetAllPrepaidSubscriptions() {
        Subscription prepaidSubscription = new Subscription();
        prepaidSubscription.setId(2);
        prepaidSubscription.setType(SubscriptionType.PREPAID);
        //prepaidSubscription.setName("Prepaid Subscription");

        when(subscriptionRepo.findByType(SubscriptionType.PREPAID)).thenReturn(Arrays.asList(prepaidSubscription));

        List<Subscription> subscriptions = subscriptionService.getAllPrepaidSubscriptions();

        assertNotNull(subscriptions);
        assertEquals(1, subscriptions.size());
        assertEquals(prepaidSubscription, subscriptions.get(0));
    }

    @Test
    void testDeleteSubscription_Success() {
        String authorizationHeader = "Bearer valid_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(false);
        when(jwtService.extractUsername(anyString())).thenReturn("admin");
        when(subscriptionRepo.findById(1)).thenReturn(Optional.of(sampleSubscription));

        boolean result = subscriptionService.deleteSubscription(1, authorizationHeader);

        assertTrue(result);
        verify(subscriptionRepo, times(1)).delete(sampleSubscription);
    }

    @Test
    void testDeleteSubscription_TokenExpired() {
        String authorizationHeader = "Bearer expired_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(true);

        boolean result = subscriptionService.deleteSubscription(1, authorizationHeader);

        assertFalse(result);
        verify(subscriptionRepo, never()).delete(any());
    }

    @Test
    void testDeleteSubscription_InvalidUser() {
        String authorizationHeader = "Bearer valid_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(false);
        when(jwtService.extractUsername(anyString())).thenReturn("user");

        boolean result = subscriptionService.deleteSubscription(1, authorizationHeader);

        assertFalse(result);
        verify(subscriptionRepo, never()).delete(any());
    }

    @Test
    void testDeleteSubscription_NotFound() {
        String authorizationHeader = "Bearer valid_token";

        when(jwtService.isTokenExpired(anyString())).thenReturn(false);
        when(jwtService.extractUsername(anyString())).thenReturn("admin");
        when(subscriptionRepo.findById(1)).thenReturn(Optional.empty());

        boolean result = subscriptionService.deleteSubscription(1, authorizationHeader);

        assertFalse(result);
        verify(subscriptionRepo, never()).delete(any());
    }
}
