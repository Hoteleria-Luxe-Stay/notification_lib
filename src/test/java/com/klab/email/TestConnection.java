package com.klab.email;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.AuthenticationFailedException;
import java.util.Properties;

public class TestConnection {

    public static void main(String[] args) {
        // Datos proporcionados
        String host = "mail.keola.club";
        String port = "587";
        String user = "notificacionesinclub@inclub.world";
        String password = "Inclub12345+";

        System.out.println("--- INICIANDO TEST DE CONEXIÓN SMTP ---");
        System.out.println("Host: " + host);
        System.out.println("Port: " + port);
        System.out.println("User: " + user);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Importante para puerto 587
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", host);
        // props.put("mail.debug", "true"); // Descomenta esto si quieres ver el log detallado del servidor

        Session session = Session.getInstance(props);

        try (Transport transport = session.getTransport("smtp")) {
            System.out.println("Intentando conectar al servidor...");

            // Este método SOLO valida usuario y contraseña sin enviar nada
            transport.connect(host, user, password);

            System.out.println("✅ ¡ÉXITO! Las credenciales son CORRECTAS.");
            System.out.println("El servidor aceptó la conexión y el usuario/password.");

        } catch (AuthenticationFailedException e) {
            System.err.println("❌ ERROR DE AUTENTICACIÓN:");
            System.err.println("El servidor rechazó el usuario o la contraseña.");
            System.err.println("Verifica que no haya espacios en blanco al inicio o final.");
        } catch (MessagingException e) {
            System.err.println("❌ ERROR DE CONEXIÓN (Posiblemente Host/Puerto/Red):");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}