package ru.andreycherenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.enums.ApplicationStatus;

@RequiredArgsConstructor
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String smtpEmail;

    private final JavaMailSender mailSender;

    public void sendStatusNotification(String to, ApplicationStatus oldStatus, ApplicationStatus newStatus) {
        var message = new SimpleMailMessage();

        message.setFrom(smtpEmail);
        message.setTo(to);
        message.setSubject("Статус кредитной заявки");

        message.setText(
                String.format(
                        "Статус вашей кредитной заявки изменен с %s на %s",
                        oldStatus.getRusTranslator().get(),
                        newStatus.getRusTranslator().get()
                )
        );

        mailSender.send(message);
    }
}
