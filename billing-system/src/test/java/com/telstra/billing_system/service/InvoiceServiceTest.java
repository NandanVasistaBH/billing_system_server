package com.telstra.billing_system.service;

import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.dto.SupplierDTO;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.model.User;
import com.telstra.billing_system.repository.InvoiceRepository;
import com.telstra.billing_system.repository.SubscriptionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePostpaidInvoice_Success() {
        Invoice invoice = new Invoice();
        invoice.setAmountPaid(0.0);
        
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        
        String result = invoiceService.createPostpaidInvoice(invoice);
        
        assertEquals("success", result);
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    void testCreatePostpaidInvoice_Failure() {
        Invoice invoice = new Invoice();
        invoice.setAmountPaid(100.0);  // Postpaid invoice cannot have prepayment
        
        String result = invoiceService.createPostpaidInvoice(invoice);
        
        assertEquals("can't pre pay in a postpaid service plan", result);
        verify(invoiceRepository, never()).save(invoice);
    }

    @Test
    void testCreatePrepaidInvoice_Success() {
        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setPrice(100.0);

        Invoice invoice = new Invoice();
        invoice.setAmountPaid(100.0);
        invoice.setSubscription(subscription);
        
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        
        String result = invoiceService.createPrepaidInvoice(invoice);
        
        assertEquals("success", result);
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    void testCreatePrepaidInvoice_SubscriptionNotFound() {
        Invoice invoice = new Invoice();
        Subscription subscription = new Subscription();
        subscription.setId(1);
        invoice.setSubscription(subscription);
        
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.empty());
        
        String result = invoiceService.createPrepaidInvoice(invoice);
        
        assertEquals("no subscription like that exists", result);
        verify(invoiceRepository, never()).save(invoice);
    }

    @Test
    void testCreatePrepaidInvoice_IncorrectAmountPaid() {
        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setPrice(100.0);

        Invoice invoice = new Invoice();
        invoice.setAmountPaid(50.0);  // Incorrect amount
        invoice.setSubscription(subscription);
        
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        
        String result = invoiceService.createPrepaidInvoice(invoice);
        
        assertEquals("amount not paid correctly", result);
        verify(invoiceRepository, never()).save(invoice);
    }

    @Test
    void testGetInvoiceById_Success() {
        // Initialize User, Customer, and Supplier objects properly
        User customerUser = new User();
        customerUser.setName("John Doe");

        Customer customer = new Customer();
        customer.setUser(customerUser);  // Set a non-null User object

        User supplierUser = new User();
        supplierUser.setName("Supplier User");

        Supplier supplier = new Supplier();
        supplier.setUser(supplierUser);  // Set a non-null User object

        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setCustomer(customer);
        invoice.setSupplier(supplier);
        
        when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        
        InvoiceResponseDTO responseDTO = invoiceService.getInvoiceById(1);
        
        assertNotNull(responseDTO);
        assertEquals(new CustomerDTO(customer), responseDTO.getCustomerDTO());  // Updated method name
        assertEquals(new SupplierDTO(supplier), responseDTO.getSupplierDTO());  // Updated method name
        verify(invoiceRepository, times(1)).findById(1);
    }

    @Test
    void testGetInvoiceById_NotFound() {
        when(invoiceRepository.findById(1)).thenReturn(Optional.empty());
        
        InvoiceResponseDTO responseDTO = invoiceService.getInvoiceById(1);
        
        assertNull(responseDTO);
        verify(invoiceRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllInvoiceByCustomerID() {
        // Initialize User, Customer, and Supplier objects properly
        User customerUser = new User();
        customerUser.setName("Jane Doe");

        Customer customer = new Customer();
        customer.setId(1);
        customer.setUser(customerUser);  // Set a non-null User object

        User supplierUser = new User();
        supplierUser.setName("Supplier User");

        Supplier supplier = new Supplier();
        supplier.setUser(supplierUser);  // Set a non-null User object

        Subscription subscription = new Subscription();

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setSupplier(supplier);
        invoice.setSubscription(subscription);

        when(invoiceRepository.findByCustomerId(1)).thenReturn(Collections.singletonList(invoice));
        
        List<InvoiceResponseDTO> result = invoiceService.getAllInvoiceByCustomerID(1);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new CustomerDTO(customer), result.get(0).getCustomerDTO());  // Updated method name
        assertEquals(new SupplierDTO(supplier), result.get(0).getSupplierDTO());  // Updated method name
        verify(invoiceRepository, times(1)).findByCustomerId(1);
    }
}
