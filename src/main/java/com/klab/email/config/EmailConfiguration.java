package com.klab.email.config;
import com.klab.email.EmailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// ⭐ Esta clase le dice a Spring cómo inicializar EmailSender
@Configuration
public class EmailConfiguration {

    // Spring inyecta automáticamente EmailProperties (del paso 1)
    private final EmailProperties properties;

    // Inyección de dependencias por constructor
    public EmailConfiguration(EmailProperties properties) {
        this.properties = properties;
    }

    // ⭐ Crea el Bean de EmailSender y lo inicializa con las propiedades leídas de application.properties
    @Bean
    public EmailSender emailSender() {
        return new EmailSender(properties);
    }
}
