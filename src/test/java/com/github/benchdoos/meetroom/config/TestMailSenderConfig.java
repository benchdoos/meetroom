package com.github.benchdoos.meetroom.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

/**
 * Configuration for test mail sender
 */
@Slf4j
@TestConfiguration
public class TestMailSenderConfig {

    @Bean
    public JavaMailSender mailSender() {
        log.debug("Mocking JavaMailSender");
        return new MockMailSender();
    }

    /**
     * Create mail sender mocked
     */
    public static class MockMailSender extends JavaMailSenderImpl {
        @Override
        public void send(final @NotNull MimeMessagePreparator mimeMessagePreparator) throws MailException {
            final MimeMessage mimeMessage = createMimeMessage();
            try {
                mimeMessagePreparator.prepare(mimeMessage);
                final String content = (String) mimeMessage.getContent();
                final Properties javaMailProperties = getJavaMailProperties();
                javaMailProperties.setProperty("mailContent", content);

                log.info("{} sending email message. FROM: {}, TO: {}, SUBJECT: {}, MESSAGE: [{}]",
                        MockMailSender.class.getName(),
                        mimeMessage.getFrom(),
                        mimeMessage.getReplyTo(),
                        mimeMessage.getSubject(),
                        mimeMessage.getContent()
                        );
            } catch (final Exception e) {
                throw new MailPreparationException(e);
            }
        }
    }
}