package org.idsiom.utilbet.mail.pruebas;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.idsiom.utilbet.currentuse.util.UtilProperties;

	
public class MainSendMail {	
	
	
	private UtilProperties utilProperties;
	private Session session;
	private Properties properties;
	
	public MainSendMail() throws IOException {
		utilProperties = UtilProperties.getInstance();
		this.session = utilProperties.getSession();
		this.properties = utilProperties.getProp();
	}
    
    public void send(String asunto, String mensaje) {
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress((String) properties.get("mail.smtp.mail.to")));
            message.setSubject(asunto);
            message.setText(mensaje);
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
            
            /*
            System.out.println("message " + message.toString());
            System.out.println("messageRecipients " + message.getAllRecipients());
            */
            System.out.println("Estoy justo antes del sendMessage...");
            
            t.sendMessage(message, message.getAllRecipients());
            
            System.out.println("----- >>>> ");
            System.out.println("----- >>>> ");
            System.out.println("----- >>>> sendMessage ejecutado...");
            
            t.close();
        } catch (MessagingException e) {
        	e.printStackTrace();
        	
        	return;
        }
    }

	/*
	public static void main(String[] args) {
		String asunto = "CORREO DE PRUEBA";
		String contenido = "Isaac eres el mejor!!!";
		String destino = "isaac.ortiz@accusys.com.ar";
		
		System.out.println("Prueba inicial para mandar un correito!!!");
		
		Scanner in = new Scanner(System.in);
		
		MainSendMail main = new MainSendMail();
		try {
			main.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			in.close();
			return;
		}
			
		
		System.out.println("Indique el destinatario...");
		destino = in.nextLine();
		
		if(destino.trim().length() == 0) {
			System.out.println("Debe ingresar un valor");
			in.close();
			return;
		}
		
		System.out.println("Indique el asunto...");
		asunto = in.nextLine();
		
		if(asunto.trim().length() == 0) {
			System.out.println("Debe ingresar un valor");
			in.close();
			return;
		}
		
		System.out.println("Indique el contenido...");
		contenido = in.nextLine();
		
		if(contenido.trim().length() == 0) {
			System.out.println("Debe ingresar un valor");
			in.close();
			return;
		}
		
		
		System.out.println("Con " + (String) main.getProp().get("mail.smtp.user") + "... a " + destino + " con contenido :: " + contenido);
		
		
		main.send(asunto, contenido);
		
		
		in.close();
		
		System.out.println("Bye Bye");
		

	}*/

}
