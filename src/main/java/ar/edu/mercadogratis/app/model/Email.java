package ar.edu.mercadogratis.app.model;

import java.io.File;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class Email {

	private static Email instance = null;
	private MultiPartEmail mail = new MultiPartEmail();
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Email() throws EmailException {
		mailConfiguration();
	}

	private void mailConfiguration() throws EmailException {
		mail.setHostName("smtp.gmail.com");
		// mail.setTLS(true);
		mail.setStartTLSEnabled(true);
		mail.setSmtpPort(465);
		// mail.setSSL(true);
		mail.setSSLOnConnect(true);
		mail.setAuthentication("mercadogratisunq@gmail.com", "MercadoGratis2021");
		mail.setFrom("mercadogratisunq@gmail.com");
	}

	public Email(MultiPartEmail email) throws EmailException {
		this.mail = email;
		mailConfiguration();
	}

	public Email getInstance() throws EmailException {
		if (instance == null) {
			instance = new Email();
		}
		return instance;
	}

	public void sendConAdjunto(String to, String subject, String mensagge) throws EmailException {
		if (file != null) {
			File fileScreenshot = new File(file);
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(fileScreenshot.getPath());
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Attachment");
			attachment.setName(fileScreenshot.getName());

			mail.attach(attachment);
		}
		mail.addTo(to);
		mail.setSubject(subject);
		mail.setMsg(mensagge);
		mail.send();
	}

	public void send(String to, String subject, String mensagge) throws EmailException {
		mail.addTo(to);
		mail.setSubject(subject);
		mail.setMsg(mensagge);
		mail.send();
	}

	public void sendMasivo(List<String> tos, String subject, String mensagge) throws EmailException {
		for (String to : tos) {
			mail.addTo(to);
		}
		mail.setSubject(subject);
		mail.setMsg(mensagge);
		mail.send();
	}
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
}