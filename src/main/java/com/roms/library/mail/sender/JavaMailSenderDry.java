package com.roms.library.mail.sender;

import com.roms.library.context.DatabaseDrivenMessageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.mail.internet.MimeMessage;

/**
 * Simulate email sending
 * It only log an info by email
 */
public class JavaMailSenderDry extends JavaMailSenderImpl {

    private static final Logger logger = LogManager.getLogger(DatabaseDrivenMessageSource.class);

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
        try {
            for (int i = 0; i < mimeMessages.length; i++) {
                MimeMessage mimeMessage = mimeMessages[i];
                String recipients = mimeMessage.getAllRecipients().toString();
                String subject = mimeMessage.getSubject();
                logger.info("Send email to 'recipients' with subject 'subject'");
            }
        } catch (Exception e) {
            throw new MailSendException("Error during Dry sending email");
        }
    }
}
