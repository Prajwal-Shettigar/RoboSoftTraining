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
        props.put("mail.smtp.ssl.trust", "true");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail,password);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);

        mimeMessage.setFrom(new InternetAddress(userEmail));
        mimeMessage.setSubject("Message sent from message controller..");
        mimeMessage.setText(body);
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(address));

        Transport.send(mimeMessage);

        return true;

    }
}
