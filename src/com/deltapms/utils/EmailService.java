package com.deltapms.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailService {

    private static final String SENDER_EMAIL = "deltahotels.management@gmail.com";
    private static final String APP_PASSWORD = "qwpv wepk imyf ejwq";

    /**
     * Sends an HTML email. 
     * Handles internal validation and catches exceptions to prevent thread crashes.
     * @param recipientEmail
     * @param subject
     * @param htmlBody
     */
    public static void sendEmail(String recipientEmail, String subject, String htmlBody) {
        // Validate the email format before starting the session
        if (!isValidEmail(recipientEmail)) {
            System.err.println("Skipping invalid/dummy email: " + recipientEmail);
            return;
        }

        // Setup SMTP Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create Session with Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });

        try {
            // Construct the Message
            Message message = new MimeMessage(session);

            // "Delta Hotels Management" is the display name, SENDER_EMAIL is the actual address
            message.setFrom(new InternetAddress(SENDER_EMAIL, "Delta Hotels Management"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            
            // Set content as HTML
            message.setContent(htmlBody, "text/html; charset=utf-8");

            // Send
            Transport.send(message);

        } catch (SendFailedException e) {
            System.err.println("Server rejected the address format: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("SMTP/Connection Error: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Encoding Error (Display Name): " + e.getMessage());
        }
    }

    /**
     * Basic Regex to check if the email format is valid.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}