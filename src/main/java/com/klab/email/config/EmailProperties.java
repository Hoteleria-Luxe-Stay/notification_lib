package com.klab.email.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// ⭐ Spring ahora leerá todas las propiedades que comienzan con 'klab.mail'
@Component
@ConfigurationProperties(prefix = "klab.mail")
public class EmailProperties {

    // Los nombres de los campos DEBEN coincidir con las propiedades del archivo:
    // klab.mail.host  -> host
    // klab.mail.username -> username

    private String host;
    private int port;
    private String username;
    private String password;
    private String fromEmail; // klab.mail.from-email
    private String fromName;  // klab.mail.from-name
    private boolean startTls; // klab.mail.start-tls

    // Spring no necesita constructor si proporcionas getters/setters

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFromEmail() { return fromEmail; }
    public void setFromEmail(String fromEmail) { this.fromEmail = fromEmail; }

    public String getFromName() { return fromName; }
    public void setFromName(String fromName) { this.fromName = fromName; }

    public boolean isStartTls() { return startTls; }
    public void setStartTls(boolean startTls) { this.startTls = startTls; }
}