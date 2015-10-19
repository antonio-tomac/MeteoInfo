package com.tomac.meteoinfo;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class Email {
	
	private final String fromEmail;
	private final String[] toEmails;
	private final String title;
	private final String body;

	public Email(String fromEmail, String title, String body, String... toEmails) {
		this.fromEmail = fromEmail;
		this.toEmails = toEmails;
		this.title = title;
		this.body = body;
	}

	public void send() throws MessagingException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.localhost", "localhost");
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromEmail));
		for (String toEmail : toEmails) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		}
		message.setSubject(title);
		message.setContent(body, "text/html; charset=utf-8");
		Transport.send(message);
	}

	public static void main(String[] args) throws MessagingException {
		new Email(
				"watchdog@mediatoolkit.com", 
				"Watchog action", 
				"<h2>Restart</h2><p>Process name: <strong>FooBar</strong></p>",
				"antonio.tomac@mediatoolkit.com" 
		).send();
	}

}
