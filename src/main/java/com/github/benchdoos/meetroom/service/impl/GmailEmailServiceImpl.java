package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
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
import java.util.UUID;

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
        final String subject = "Meetroom - Reset password";
        final String emailMessage = internalConfiguration.getEmailSettings().getResetPasswordEmailMessage()
                .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                .replaceAll("\\{resetPasswordLink\\}", createResetPasswordUrl(publicFullApplicationUrl, passwordResetRequest))
                .replaceAll("\\{meetroomMainPage\\}", configurationInfoBean.getPublicFullApplicationUrl());

        try {
            sendEmailToUser(user.getEmail(), subject, emailMessage);
        } catch (MessagingException e) {
            log.warn("Could not send message to user: {}", user.getUsername(), e);
        }

        log.debug("Successfully sent password reset notification for user: {}", user.getUsername());
    }

    @Async
    @Override
    public void sendAccountActivation(String publicFullApplicationUrl, User user, AccountActivationRequest accountActivationRequest) {
        final String subject = "Meetroom - Activate account password";
        final String emailMessage = internalConfiguration.getEmailSettings().getAccountActivationEmailMessage()
                .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                .replaceAll("\\{activateAccountLink\\}", createAccountActivationUrl(publicFullApplicationUrl, accountActivationRequest))
                .replaceAll("\\{meetroomMainPage\\}", configurationInfoBean.getPublicFullApplicationUrl());

        try {
            sendEmailToUser(user.getEmail(), subject, emailMessage);
        } catch (final MessagingException e) {
            log.warn("Could not send message to user: {}", user.getUsername(), e);
        }

        log.debug("Successfully sent activation account notification for user: {}", user.getUsername());
    }

    @Override
    public void sendEmailUpdateRequests(String publicFullApplicationUrl,
                                        String oldEmail,
                                        String newEmail,
                                        User user,
                                        UserEmailUpdateRequest emailUpdateRequest) {

        final String subject = "Meetroom - Update email request";
        try {

            if (emailUpdateRequest.getOldEmailConfirmation() != null) { //can be null if user had no email first
                final String oldEmailMessage = internalConfiguration.getEmailSettings().getUserEmailUpdateOldMessage()
                        .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                        .replaceAll("\\{newEmailAddress\\}", newEmail)
                        .replaceAll("\\{submitAddressLink\\}", createEmailUpdateSubmitLink(publicFullApplicationUrl, emailUpdateRequest.getOldEmailConfirmation()))
                        .replaceAll("\\{meetroomMainPage\\}", publicFullApplicationUrl);
                sendEmailToUser(oldEmail, subject, oldEmailMessage);
            }

            final String newEmailMessage = internalConfiguration.getEmailSettings().getUserEmailUpdateNewMessage()
                    .replaceAll("\\{userFullName\\}", user.getFirstName() + " " + user.getLastName())
                    .replaceAll("\\{submitAddressLink\\}", createEmailUpdateSubmitLink(publicFullApplicationUrl, emailUpdateRequest.getNewEmailConfirmation()))
                    .replaceAll("\\{meetroomMainPage\\}", publicFullApplicationUrl);

            sendEmailToUser(newEmail, subject, newEmailMessage);
        } catch (final MessagingException e) {
            log.warn("Could not send message to user: {}", user.getUsername(), e);
        }
        log.debug("Successfully sent email update notifications for user: {}", user.getUsername());
    }

    /**
     * Create email update submit link
     *
     * @param publicFullApplicationUrl full public url to application
     * @param emailId email id. One of {@link UserEmailUpdateRequest#getOldEmailConfirmation()},
     * {@link UserEmailUpdateRequest#getNewEmailConfirmation()}
     * @return url for confirmation
     */
    private String createEmailUpdateSubmitLink(String publicFullApplicationUrl, UUID emailId) {
        return publicFullApplicationUrl + "/user/submit-email-update/" + emailId;
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

    /**
     * Send email message
     *
     * @param email email to send
     * @param subject email subject
     * @param emailMessage message
     */
    private void sendEmailToUser(String email, String subject, String emailMessage) throws MessagingException {
        final MimeMessage simpleMailMessage = emailSender.createMimeMessage();

        final MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, CharEncoding.UTF_8);
        helper.setFrom(sendingEmail);
        helper.setTo(email);
        helper.setSubject(subject);

        helper.setText(emailMessage, true);

        simpleMailMessage.setSentDate(new Date());

        emailSender.send(simpleMailMessage);
    }
}
