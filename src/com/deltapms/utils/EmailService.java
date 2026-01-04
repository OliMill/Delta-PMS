package com.deltapms.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private static final String SENDER_EMAIL = "deltahotels.management@gmail.com";
    private static final String APP_PASSWORD = "qwpv wepk imyf ejwq";

    public static void sendEmail(String recipientEmail, String subject, String htmlBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);

            // This makes the sender appear as "Delta Hotels Management" 
            message.setFrom(new InternetAddress(SENDER_EMAIL, "Delta Hotels Management"));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            //set as html for formatting
            message.setContent(htmlBody, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
