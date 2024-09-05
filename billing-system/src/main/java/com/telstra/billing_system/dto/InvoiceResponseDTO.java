package com.telstra.billing_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.telstra.billing_system.model.Invoice;
import com.telstra.billing_system.model.Subscription;

import lombok.Data;
@Data
public class InvoiceResponseDTO {
    private CustomerDTO customerDTO;
    private SupplierDTO supplierDTO;
    private Subscription subscription;
    private Double amountPaid,tax;
    private LocalDate invoiceIssueDate;
    private LocalDateTime lastUpdatedAt;
    public InvoiceResponseDTO(CustomerDTO customerDTO,SupplierDTO supplierDTO,Subscription subscription,Invoice invoice){
        this.customerDTO=customerDTO;
        this.supplierDTO=supplierDTO;
        this.subscription=subscription;
        this.amountPaid=invoice.getAmountPaid();
        this.tax=invoice.getTax();
        this.invoiceIssueDate=invoice.getInvoiceIssueDate();
        this.lastUpdatedAt=invoice.getLastUpdatedAt();
    }
}