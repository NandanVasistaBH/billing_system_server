package com.telstra.billing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.telstra.billing_system.dto.MailDto;
import com.telstra.billing_system.service.MailSender;
@RestController
@RequestMapping("/mail")
public class MailController {
    @Qualifier("MailSender")
    @Autowired
    private MailSender mailSender;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailDto emailRequest) {
        try {
            mailSender.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return new ResponseEntity<>( "Email sent successfully",HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>( "Error sending email",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
