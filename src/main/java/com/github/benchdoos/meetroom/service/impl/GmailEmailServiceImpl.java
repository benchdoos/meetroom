package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
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
 * Of course it's better to use MQ, but... sorry no normal free hosts available))
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
    public void sendResetPasswordNotification(String publicFullApplicationUrl, User user, PasswordResetRequest passwordResetRequest) {
        try {
            final MimeMessage simpleMailMessage = emailSender.createMimeMessage();

            final MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, CharEncoding.UTF_8);
            helper.setFrom(sendingEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Meetroom - Reset password");

            final String emailMessage = internalConfiguration.getEmailSettings().getResetPasswordEmailMessage()
                    .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                    .replaceAll("\\{resetPasswordLink\\}", createResetPasswordUrl(publicFullApplicationUrl, passwordResetRequest));

            helper.setText(emailMessage, true);

            simpleMailMessage.setSentDate(new Date());

            emailSender.send(simpleMailMessage);
            log.debug("Successfully sent password reset notification for user: {}", user.getUsername());
        } catch (final MessagingException e) {
            log.warn("Could not send message to user: {}", user.getUsername(), e);
        }
    }

    @Async
    @Override
    public void sendAccountActivation(String publicFullApplicationUrl, User user, AccountActivationRequest accountActivationRequest) {
        try {
            final MimeMessage simpleMailMessage = emailSender.createMimeMessage();

            final MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, CharEncoding.UTF_8);
            helper.setFrom(sendingEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Meetroom - Activate account password");

            final String emailMessage = internalConfiguration.getEmailSettings().getAccountActivationEmailMessage()
                    .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                    .replaceAll("\\{activateAccountLink\\}", createAccountActivationUrl(publicFullApplicationUrl, accountActivationRequest));

            helper.setText(emailMessage, true);

            simpleMailMessage.setSentDate(new Date());

            emailSender.send(simpleMailMessage);
            log.debug("Successfully sent activation account notification for user: {}", user.getUsername());
        } catch (final MessagingException e) {
            log.warn("Could not send activation account email for user: {}", user.getUsername(), e);
        }
    }

    /**
     * Create reset password url link
     *
     * @param publicFullApplicationUrl full public url to application
     * @param passwordResetRequest passwordResetRequest to create link
     * @return ready to go link
     */
    private String createResetPasswordUrl(String publicFullApplicationUrl, PasswordResetRequest passwordResetRequest) {
        return publicFullApplicationUrl + "/user/reset-password/" + passwordResetRequest.getId();
    }

    /**
     * Create account activation link
     *
     * @param publicFullApplicationUrl full public url to application
     * @param accountActivationRequest request to create link
     * @return ready to go link
     */
    private String createAccountActivationUrl(String publicFullApplicationUrl, AccountActivationRequest accountActivationRequest) {
        return publicFullApplicationUrl + "/user/activate/" + accountActivationRequest.getId();
    }
}
