package com.prajwal.messagingservice.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {


    @Value("${user.email}")
    private String userEmail;

    @Value("${user.password}")
    private String password;
    public boolean send(String body, String address) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userEmail, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));

        message.setSubject("Two Factor Authentication code from our Service");
        message.setText("Your Two Factor Authentication code is:"+body);
        Transport.send(message);
        return true;

    }
}
