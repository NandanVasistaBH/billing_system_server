package com.telstra.billing_system.repository;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // Use a test profile
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private User user;


    @Test
    public void testFindByName() {
        // Arrange
        String customerName = "Test Customer";

        // Act
        Customer foundCustomer = customerRepository.findByName(customerName);

        // Assert
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    public void testFindBySupplierId() {
        // Arrange
        Integer supplierId = 1;

        // Act
        Optional<List<Customer>> customers = customerRepository.findBySupplierId(supplierId);

        // Assert
        assertThat(customers).isPresent();
        assertThat(customers.get()).isNotEmpty();
    }

    @Test
    public void testFindBySupplierId_NoCustomers() {
        // Arrange
        Integer supplierId = 999; // Assuming this ID does not exist

        // Act
        Optional<List<Customer>> customers = customerRepository.findBySupplierId(supplierId);

        // Assert
        assertThat(customers).isPresent();
        assertThat(customers.get()).isEmpty();
    }
}
