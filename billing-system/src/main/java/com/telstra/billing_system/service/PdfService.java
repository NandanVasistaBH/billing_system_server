package com.telstra.billing_system.service;

import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;  
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.telstra.billing_system.dto.CustomerDTO;
import com.telstra.billing_system.dto.SupplierDTO;
import com.telstra.billing_system.model.Subscription;
import com.telstra.billing_system.dto.InvoiceResponseDTO;
import com.telstra.billing_system.service.InvoiceService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
@Service("PdfService")
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    @Autowired
    private InvoiceService invoiceService; 
    @Autowired
    private AmazonS3 amazonS3; // Inject the S3 client
    public ByteArrayInputStream createPdf(Integer invoiceId) {
        logger.info("Creating PDF for invoiceId: {}", invoiceId);
        InvoiceResponseDTO invoiceResponse = invoiceService.getInvoiceById(invoiceId);

        if (invoiceResponse == null) {
            logger.error("Invoice not found for id: {}", invoiceId);
            return new ByteArrayInputStream(null);
        }

        CustomerDTO customer = invoiceResponse.getCustomerDTO();
        SupplierDTO supplier = invoiceResponse.getSupplierDTO();
        Subscription subscription = invoiceResponse.getSubscription();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Add a background color to the entire page
            PdfContentByte cb = writer.getDirectContentUnder();
            PdfTemplate template = cb.createTemplate(document.getPageSize().getWidth(), document.getPageSize().getHeight());
            template.setColorFill(new Color(255, 253, 208)); // Cream color
            template.rectangle(0, 0, document.getPageSize().getWidth(), document.getPageSize().getHeight());
            template.fill();
            cb.addTemplate(template, 0, 0);

           
            Font companyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 40, Font.BOLD, new Color(0, 0, 0)); // Black color
            Paragraph companyName = new Paragraph("TELSTRA", companyFont);
            companyName.setAlignment(Element.ALIGN_CENTER);
            document.add(companyName);

            // Add a spacer
            document.add(new Paragraph("\n"));

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
            Paragraph titlePara = new Paragraph("Invoice Details", titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            document.add(titlePara);

            // Add content
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            StringBuilder content = new StringBuilder();
            content.append("Invoice ID: ").append(invoiceResponse.getId()).append("\n");
            content.append("Customer Information:\n");
            content.append("  ID: ").append(customer.getId()).append("\n");
            content.append("  Name: ").append(customer.getName()).append("\n");
            content.append("  Email: ").append(customer.getCustEmail()).append("\n");
            content.append("  Phone No: ").append(customer.getCustPhoneNo()).append("\n");
            content.append("Supplier Information:\n");
            content.append("  ID: ").append(supplier.getId()).append("\n");
            content.append("  Name: ").append(supplier.getName()).append("\n");
            content.append("  Branch Location: ").append(supplier.getBranchLoc()).append("\n");
            content.append("  Branch Manager: ").append(supplier.getBranchManager()).append("\n");
            content.append("  Branch Email: ").append(supplier.getBranchEmail()).append("\n");
            content.append("  Branch Phone No: ").append(supplier.getBranchPhoneNo()).append("\n");
            content.append("Subscription Information:\n");
            content.append("  ID: ").append(subscription.getId()).append("\n");
            content.append("  Type: ").append(subscription.getType()).append("\n");
            content.append("  Description: ").append(subscription.getDescription()).append("\n");
            content.append("  Price: ").append(subscription.getPrice()).append("\n");
            content.append("  Number of Days: ").append(subscription.getNoOfDays()).append("\n");
            content.append("  Status: ").append(subscription.getStatus()).append("\n");
            content.append("Amount Paid: ").append(invoiceResponse.getAmountPaid()).append("\n");
            content.append("Tax: ").append(invoiceResponse.getTax()).append("\n");
            content.append("Issue Date: ").append(invoiceResponse.getInvoiceIssueDate()).append("\n");
            content.append("Last Updated: ").append(invoiceResponse.getLastUpdatedAt()).append("\n");

            Paragraph paragraph = new Paragraph(content.toString(), paraFont);
            document.add(paragraph);

            // Add footer with address
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Paragraph footer = new Paragraph(
                "Address:\nPrimrose Embassy, Tech Village,\nBelendur, Bangalore,\nKarnataka, India", 
                footerFont
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            uploadToS3(out.toByteArray(), "invoice_" + invoiceId + ".pdf");
        } catch (Exception e) {
            logger.error("Error while creating PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    private void uploadToS3(byte[] pdfBytes, String fileName) {
        try (InputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(pdfBytes.length);
            amazonS3.putObject(new PutObjectRequest("invoice-telecom-billing", fileName, inputStream, metadata));
            logger.info("Successfully uploaded PDF to S3: {}", fileName);
        } catch (IOException e) {
            logger.error("Error uploading PDF to S3", e);
        }
    }
}
