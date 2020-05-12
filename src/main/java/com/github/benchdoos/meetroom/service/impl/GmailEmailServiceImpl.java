package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Gmail email service
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GmailEmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final SpringConfigurationInfoBean configurationInfoBean;
    private final InternalConfiguration internalConfiguration;

    @Value("spring.mail.username")
    private String sendingEmail;

    @Async
    @Override
    public void sendResetPasswordNotification(User user, PasswordResetRequest passwordResetRequest) throws MessagingException {
        final MimeMessage simpleMailMessage = emailSender.createMimeMessage();

        final MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, CharEncoding.UTF_8);
        helper.setFrom(sendingEmail);
        helper.setTo(user.getEmail());
        helper.setSubject("Meetroom - Reset password");

        final String emailMessage = internalConfiguration.getEmailSettings().getResetPasswordEmailMessage()
                .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                .replaceAll("\\{resetPasswordLink\\}", createResetPasswordUrl(passwordResetRequest));

        helper.setText(emailMessage, true);

        simpleMailMessage.setSentDate(new Date());

        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAccountActivation(User user) {
        throw new UnsupportedOperationException("Not realized yet");
    }

    /**
     * Create reset password url link
     *
     * @param passwordResetRequest passwordResetRequest to create link
     * @return ready to go link
     */
    private String createResetPasswordUrl(PasswordResetRequest passwordResetRequest) {
        return configurationInfoBean.getPublicFullApplicationUrl()
                + "/user/reset-password/" + passwordResetRequest.getId();
    }
}
