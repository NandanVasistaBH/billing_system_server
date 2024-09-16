package com.telstra.billing_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.repository.SubscriptionRepository;
import com.telstra.billing_system.repository.UserRepository;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepo;

    @Mock
    private UserRepository userRepository;

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
    // Arrange: Create a sample subscription and set up mock behavior
    Subscription sampleSubscription = new Subscription();
    sampleSubscription.setStatus(Subscription.SubscriptionStatus.PENDING_FOR_APPROVAL_LIVE); // Ensure the status is set properly in case it's needed
    String authorizationHeader = "Bearer valid_token";

    // Mock behavior for JWT service
    when(jwtService.isTokenExpired(anyString())).thenReturn(false);
    when(jwtService.extractUsername(anyString())).thenReturn("admin");

    // Mock behavior for user repository to return a user with ADMIN_ROLE
    User adminUser = new User();
    adminUser.setRole("ADMIN");
    when(userRepository.findByName("admin")).thenReturn(adminUser);

    // Act: Call the create method of the subscription service
    boolean result = subscriptionService.create(sampleSubscription, authorizationHeader);

    // Assert: Verify that the method returns true (successful creation)
    assertTrue(result);

    // Assert: Verify that the save method of the repository is called with the subscription
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
    // Arrange: Create a sample subscription with LIVE status
    Subscription sampleSubscription = new Subscription();
    sampleSubscription.setStatus(Subscription.SubscriptionStatus.LIVE);
    sampleSubscription.setType(Subscription.SubscriptionType.POSTPAID); // or PREPAID, as needed
    sampleSubscription.setDescription("Test Subscription");
    sampleSubscription.setPrice(100.0);
    sampleSubscription.setNoOfDays(30);    
    when(subscriptionRepo.findByStatus(Subscription.SubscriptionStatus.LIVE))
        .thenReturn(Arrays.asList(sampleSubscription));    
    List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
    assertNotNull(subscriptions);
    assertEquals(1, subscriptions.size());
    assertEquals(sampleSubscription, subscriptions.get(0));
}


    @Test
    void testGetAllPostpaidSubscriptions() {
        Subscription sampleSubscription = new Subscription();
    sampleSubscription.setStatus(Subscription.SubscriptionStatus.LIVE);
    sampleSubscription.setType(Subscription.SubscriptionType.POSTPAID); // or PREPAID, as needed
    sampleSubscription.setDescription("Test Subscription");
    sampleSubscription.setPrice(100.0);
    sampleSubscription.setNoOfDays(30);
        when(subscriptionRepo.findByType(SubscriptionType.POSTPAID)).thenReturn(Arrays.asList(sampleSubscription));

        List<Subscription> subscriptions = subscriptionService.getAllPostpaidSubscriptions();

        assertNotNull(subscriptions);
        assertEquals(1, subscriptions.size());
        assertEquals(sampleSubscription, subscriptions.get(0));
    }

   @Test
void testGetAllPrepaidSubscriptions() {
    Subscription prepaidSubscription = new Subscription();
    prepaidSubscription.setStatus(Subscription.SubscriptionStatus.LIVE); 
    //prepaidSubscription.setId(2);
    prepaidSubscription.setType(SubscriptionType.PREPAID);
    prepaidSubscription.setDescription("Test Subscription");
    prepaidSubscription.setPrice(100.0);
    prepaidSubscription.setNoOfDays(30);

    when(subscriptionRepo.findByType(SubscriptionType.PREPAID)).thenReturn(Arrays.asList(prepaidSubscription));

    List<Subscription> subscriptions = subscriptionService.getAllPrepaidSubscriptions();

    assertNotNull(subscriptions, "Subscriptions list should not be null");
   assertEquals(1, subscriptions.size(), "Expected list size to be 1");
    assertEquals(prepaidSubscription, subscriptions.get(0), "Expected the subscription to match the prepaidSubscription");

    System.out.println("Subscriptions: " + subscriptions);
}


// @Test
// void testDeleteSubscription_Success() {
//     String authorizationHeader = "Bearer valid_token";
//     when(jwtService.isTokenExpired(anyString())).thenReturn(true);
//     when(jwtService.extractUsername(anyString())).thenReturn("admin");
//     when(subscriptionRepo.findById(1)).thenReturn(Optional.of(sampleSubscription));

//     boolean result = subscriptionService.deleteSubscription(1, authorizationHeader);

    
//     assertTrue(result, "Expected deleteSubscription to return true, but it returned false");

//     verify(subscriptionRepo, times(1)).delete(sampleSubscription);

//     System.out.println("Result of deleteSubscription: " + result);
//     System.out.println("Expected subscription to be deleted: " + sampleSubscription);
// }


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
