package com.telstra.billing_system.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.telstra.billing_system.exceptions.MailException;
import java.util.Properties;

@Service("MailSender")
public class MailSender {

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.smtp.useSSL}")
    private boolean useSSL;

    @Value("${mail.smtp.rejectUnauthorized}")
    private boolean rejectUnauthorized;

    public void sendEmail(String to, String subject, String text) throws MailException,MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");

        // Enable STARTTLS (true if using STARTTLS)
        props.put("mail.smtp.starttls.enable", "true");
        
        // Only needed if you want to trust self-signed certificates
        if (!rejectUnauthorized) {
            props.put("mail.smtp.ssl.trust", host);
        }

        // For SSL/TLS connections
        if (useSSL) {
            props.put("mail.smtp.ssl.enable", "true");
        }

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);
    }
}
