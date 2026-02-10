package com.klab.email;

import com.klab.email.config.EmailProperties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test unitario para la clase EmailSender utilizando JUnit 5 y Mockito,
 * evitando el uso de whenNew.
 */
@ExtendWith(MockitoExtension.class)
public class EmailSenderTest {

    @Mock
    private EmailProperties mockConfig;

//    @InjectMocks
//    private EmailSender emailSender;

    private final String HTML_CONTENT = "<h1>Cuerpo del Correo</h1>";
    private final String RECIPIENT = "jhonnysota67@gmail.com";
    private final String SUBJECT = "Asunto de Prueba";
    private final String HEADER = "X-Custom-Header: value";

    @BeforeEach
    void setUp() {
//        when(mockConfig.isStartTls()).thenReturn(true);
//        when(mockConfig.getUsername()).thenReturn("notificacionesinclub@inclub.world");
//        when(mockConfig.getFromName()).thenReturn("KLAB Notificaciones");
//        when(mockConfig.getFromEmail()).thenReturn(null);
    }
    @Test
    void testSendEmail_CallsTransportSend() throws Exception {

        EmailSender emailSender = new EmailSender();

        try (MockedStatic<Transport> mockedTransport = mockStatic(Transport.class)) {

            emailSender.sendEmail(
                    HTML_CONTENT,
                    RECIPIENT,
                    SUBJECT,
                    HEADER
            );

            // Verificar 1 llamada
            mockedTransport.verify(() -> Transport.send(any(Message.class)), times(1));
        }
    }
    @Test
    void testEnviarCorreoReal() throws Exception {

        EmailSender emailSender = new EmailSender();

        emailSender.sendEmail(
                "<h1>Correo REAL de prueba</h1>",
                "jhonnysota67@gmail.com",
                "Prueba REAL desde Java",
                null
        );
    }

}