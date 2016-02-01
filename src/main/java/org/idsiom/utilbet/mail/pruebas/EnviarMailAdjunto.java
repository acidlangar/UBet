package org.idsiom.utilbet.mail.pruebas;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * Ejemplo de envio de un correo de texto con una imagen adjunta con javamail
 *
 * @author Chuidiang
 *
  */

public class EnviarMailAdjunto {
	/**
     * @param args se ignoran
     */
    public static void main(String[] args)
    {
        try
        {
          // se obtiene el objeto Session. La configuración es para
          // una cuenta de gmail.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "coe.inter@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Primera prueba de adjunto... lista");

            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(
                new DataHandler(new FileDataSource("D:/adjuntos_gmail/MathinSportsCourseCalendar.pdf")));
            adjunto.setFileName("MathinSportsCourseCalendar.pdf");

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("coe.inter@gmail.com"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress("javier.rodriguez@accusys.com.ar"));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("pablo.genise@accusys.com.ar"));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("isaac.ortiz@accusys.com.ar"));
            message.setSubject("Primera prueba de adjunto... lista");
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect("coe.inter@gmail.com", "internacional123*");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
