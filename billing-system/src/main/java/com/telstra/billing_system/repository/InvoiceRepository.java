package com.telstra.billing_system.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.telstra.billing_system.model.Invoice;
@Repository("InvoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByCustomerId(Integer customerId);
    @Query("SELECT i FROM Invoice i WHERE i.customer.id = :customerId ORDER BY i.invoiceIssueDate DESC limit 1")
    Optional<Invoice> findLatestInvoiceByCustomerId(@Param("customerId") String customerId);
    @Query("SELECT i.subscription, Count(*) AS totalAmount " +
    "FROM Invoice i " +
    "GROUP BY i.subscription " +
    "ORDER BY totalAmount DESC")
    List<Object[]> findTopSubscriptions();

    @Query(value = "SELECT COUNT(i.id) FROM invoice i " +
                   "JOIN subscription s ON i.subscription_id = s.id " +
                   "WHERE i.subscription_id = :subscriptionId " +
                   "AND CURDATE() BETWEEN i.invoice_issue_date AND DATE_ADD(i.invoice_issue_date, INTERVAL s.no_of_days DAY)",
           nativeQuery = true)
    Integer countActiveSubscribersBySubscriptionId(@Param("subscriptionId") Integer subscriptionId);
}
