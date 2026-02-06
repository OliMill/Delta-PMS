package com.deltapms.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailService {

    private static final String SENDER_EMAIL = "deltahotels.management@gmail.com";
    private static final String APP_PASSWORD = "qwpv wepk imyf ejwq";

    /**
     * Sends an HTML email. Handles internal validation and catches exceptions
     * to prevent thread crashes.
     *
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
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    public static String sendEmailConfirmation(String email) {
        if (isValidEmail(email)) {
            // Generate a random 6-digit code
            String code = String.valueOf((int) ((Math.random() * 900000) + 100000));

            // HTML Email body
            String htmlContent
                    = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; "
                    + "border: 1px solid #e0e0e0; border-radius: 8px; padding: 20px;'>"
                    + "  <h2 style='color: #590414; text-align: center; margin-bottom: 10px;'>"
                    + "    DeltaPMS Verification"
                    + "  </h2>"
                    + "  <hr style='border: none; height: 3px; background-color: #f2441d; margin: 20px 0;' />"
                    + "  <p style='font-size: 16px; color: #555;'>Hello,</p>"
                    + "  <p style='font-size: 16px; color: #555;'>"
                    + "    Use the code below to complete your sign-in process. "
                    + "  </p>"
                    + "  <div style='background-color: #f9f2f3; padding: 20px; text-align: center; "
                    + "  border-radius: 6px; margin: 20px 0; border: 2px solid #f2441d;'>"
                    + "    <span style='font-size: 34px; font-weight: bold; letter-spacing: 6px; "
                    + "    color: #f2441d;'>" + code + "</span>"
                    + "  </div>"
                    + "  <p style='font-size: 12px; color: #999; text-align: center;'>"
                    + "    If you did not request this code, please ignore this email."
                    + "  </p>"
                    + "</div>";

            sendEmail(email, "Your DeltaPMS confirmation code", htmlContent);
            return code;
        }
        return null;
    }

}
