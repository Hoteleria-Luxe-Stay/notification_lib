package com.klab.email;

import com.klab.email.config.EmailProperties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
public class EmailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private final EmailProperties properties;

    public EmailSender(EmailProperties properties) {
        this.properties = properties;
    }

    public EmailSender() {
        this.properties = null;
    }

    public void sendEmail(String htmlContent, String recipientEmail, String subject, String optionalHeader)
            throws MessagingException, UnsupportedEncodingException {
        if (properties == null) {
            throw new MessagingException("EmailProperties not configured");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", String.valueOf(isAuthEnabled()));
        props.put("mail.smtp.starttls.enable", String.valueOf(properties.isStartTls()));
        props.put("mail.smtp.starttls.required", String.valueOf(properties.isStartTls()));
        props.put("mail.smtp.host", properties.getHost());
        props.put("mail.smtp.port", String.valueOf(properties.getPort()));
        if (properties.getHost() != null && !properties.getHost().isBlank()) {
            props.put("mail.smtp.ssl.trust", properties.getHost());
        }

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getUsername(), properties.getPassword());
            }
        });
        session.setDebug(false);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(buildFromAddress());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            if (optionalHeader != null) {
                message.setHeader("X-Optional-Data", optionalHeader);
            }

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("ERROR DE ENVIO SMTP: Fallo al enviar correo a {}. Detalles: {}", recipientEmail, e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("ERROR INESPERADO: Fallo desconocido al procesar el correo. Detalles: {}", e.getMessage(), e);
        }
    }

    private boolean isAuthEnabled() {
        return properties.getUsername() != null && !properties.getUsername().isBlank();
    }

    private InternetAddress buildFromAddress() throws MessagingException, UnsupportedEncodingException {
        String fromEmail = properties.getFromEmail() != null && !properties.getFromEmail().isBlank()
                ? properties.getFromEmail()
                : properties.getUsername();
        if (properties.getFromName() != null && !properties.getFromName().isBlank()) {
            return new InternetAddress(fromEmail, properties.getFromName());
        }
        return new InternetAddress(fromEmail);
    }
}
