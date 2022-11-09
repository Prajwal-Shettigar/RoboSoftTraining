package com.prajwal.messagingservice.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private final static String ACCOUNT_SID = "ACd1ece504ef220e2478f7cb5671193868";
    private final static String AUTH_ID = "822deff0f134cf13c02dc5b3382b2f54";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }
    public boolean send(String message, String mobileNumber) {

        Message.creator(new PhoneNumber(mobileNumber), new PhoneNumber("+14245443454"), message).create();

        return true;
    }
}
