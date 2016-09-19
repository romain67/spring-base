package com.roms.library.mail.sender;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Replaces any email recipient by email to which redirect
 */
public class JavaMailSenderRedirect extends JavaMailSenderImpl {

    private String redirectTo;

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
            for (int i = 0; i < mimeMessages.length; i++) {
                MimeMessage mimeMessage = mimeMessages[i];
                try {
                    mimeMessage.setRecipients(Message.RecipientType.TO, redirectTo);
                    mimeMessage.setRecipients(Message.RecipientType.BCC, "");
                    mimeMessage.setRecipients(Message.RecipientType.CC, "");
                } catch (MessagingException e) {
                    throw new MailSendException("Can not set email '" + redirectTo + "' as recipient");
                }
            }
            super.doSend(mimeMessages, originalMessages);
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

}
