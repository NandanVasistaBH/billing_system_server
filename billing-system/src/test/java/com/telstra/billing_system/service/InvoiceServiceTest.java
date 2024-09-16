package com.telstra.billing_system.service;

import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.dto.SupplierDTO;
import com.telstra.billing_system.exceptions.MailException;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.repository.InvoiceRepository;
import com.telstra.billing_system.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private InvoiceService invoiceService;

    private Invoice sampleInvoice;
    private Subscription sampleSubscription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup sample data
        sampleSubscription = new Subscription();
        sampleSubscription.setId(1);
        sampleSubscription.setPrice(100.0);

        sampleInvoice = new Invoice();
        sampleInvoice.setId(1);
        sampleInvoice.setAmountPaid(100.0);
        sampleInvoice.setSubscription(sampleSubscription);
    }

    @Test
    void testCreatePostpaidInvoice_Success() {
        sampleInvoice.setAmountPaid(0.0);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(sampleInvoice);

        String result = invoiceService.createPostpaidInvoice(sampleInvoice);

        assertEquals("success", result);
        verify(invoiceRepository, times(1)).save(sampleInvoice);
    }

    @Test
    void testCreatePostpaidInvoice_Failure() {
        sampleInvoice.setAmountPaid(50.0);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(sampleInvoice);

        String result = invoiceService.createPostpaidInvoice(sampleInvoice);

        assertEquals("can't pre pay in a postpaid service plan", result);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testUpdatePostpaidInvoice_Success() {
        when(invoiceRepository.findById(1)).thenReturn(Optional.of(sampleInvoice));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(sampleInvoice);

        String result = invoiceService.updatePostpaidInvoice(sampleInvoice);

        assertEquals("success", result);
        verify(invoiceRepository, times(1)).save(sampleInvoice);
    }

    @Test
    void testUpdatePostpaidInvoice_NotFound() {
        when(invoiceRepository.findById(1)).thenReturn(Optional.empty());

        String result = invoiceService.updatePostpaidInvoice(sampleInvoice);

        assertEquals("Invoice not found", result);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testCreatePrepaidInvoice_Success() {
        when(subscriptionRepository.findById(1)).thenReturn(Optional.of(sampleSubscription));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(sampleInvoice);

        String result = invoiceService.createPrepaidInvoice(sampleInvoice);

        assertEquals("success", result);
        verify(invoiceRepository, times(1)).save(sampleInvoice);
    }

    @Test
    void testCreatePrepaidInvoice_NoSubscription() {
        when(subscriptionRepository.findById(1)).thenReturn(Optional.empty());

        String result = invoiceService.createPrepaidInvoice(sampleInvoice);

        assertEquals("no subscription like that exists", result);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testCreatePrepaidInvoice_AmountMismatch() {
        sampleInvoice.setAmountPaid(50.0);
        when(subscriptionRepository.findById(1)).thenReturn(Optional.of(sampleSubscription));

        String result = invoiceService.createPrepaidInvoice(sampleInvoice);

        assertEquals("amount not paid correctly", result);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }


    @Test
    void testGetInvoiceById_NotFound() throws MessagingException {
        when(invoiceRepository.findById(1)).thenReturn(Optional.empty());

        InvoiceResponseDTO response = invoiceService.getInvoiceById(1);

        assertNull(response);
        verify(mailSender, never()).sendEmail(anyString(), anyString(), anyString());
    }

    // @Test
    // void testGetAllInvoiceByCustomerID() {
    //     Customer sampleCustomer = new Customer();
    //     sampleCustomer.setId(1); 
    //     when(invoiceRepository.findByCustomerId(1)).thenReturn(Arrays.asList(sampleInvoice));

    //     List<InvoiceResponseDTO> response = invoiceService.getAllInvoiceByCustomerID(1);

    //     assertNotNull(response);
    //     assertEquals(1, response.size());
    //     assertEquals(new CustomerDTO(sampleInvoice.getCustomer()), response.get(0).getCustomerDTO());
    // }

    @Test
    void testGetTopSubscription() {
        Subscription topSubscription = new Subscription();
        topSubscription.setId(2);
        topSubscription.setPrice(150.0);

        when(invoiceRepository.findTopSubscriptions()).thenReturn(
                Arrays.asList(new Object[]{topSubscription}, new Object[]{sampleSubscription})
        );

        List<Subscription> topSubscriptions = invoiceService.getTopSubscription();

        assertNotNull(topSubscriptions);
        assertEquals(2, topSubscriptions.size()); // Adjust the expected size as necessary
        assertTrue(topSubscriptions.contains(sampleSubscription));
    }
}
