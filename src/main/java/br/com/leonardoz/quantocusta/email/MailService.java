package br.com.leonardoz.quantocusta.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	private JavaMailSender mailSender;
	private MailContentBuilder builder;
	
	@Value("${spring.mail.username}")
	private String email;
	
	@Autowired
    public MailService(JavaMailSender mailSender, MailContentBuilder builder) {
        this.mailSender = mailSender;
		this.builder = builder;
    }

	@Async
	public void prepareAndSend(String recipient, String url, String subject, String template) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(email);
			messageHelper.setTo(recipient);
			messageHelper.setSubject(subject);
			String content = builder.build(url, template);
	        messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
}
