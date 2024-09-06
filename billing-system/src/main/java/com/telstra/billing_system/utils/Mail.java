package com.telstra.billing_system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.telstra.billing_system.dto.MailDto;

@Component
public class Mail {

    private RestTemplate restTemplate;
    
    public Mail(RestTemplate rt){
        this.restTemplate = rt;
    }
    
    private static final String URL = "http://localhost:5001/send-mail";

    public  boolean sendMail(String to, String body, String subject) {
        System.out.println("sending mail to " + to);
        MailDto mailDto = new MailDto();
        mailDto.setText(body);
        mailDto.setSubject(subject);
        mailDto.setTo(to);

        System.out.println("mail dto     "+mailDto.getText());
        String response = restTemplate.postForObject(URL, mailDto, String.class); 
        System.out.println(response);   
        if(response.equals("Email sent successfully")) {
            System.out.println("Email sent !!");
            return true;
        } else {
            System.out.println("Error:  "  + response );
        }
        return false;
    }
}
