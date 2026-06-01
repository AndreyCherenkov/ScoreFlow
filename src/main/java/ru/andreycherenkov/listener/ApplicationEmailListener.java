package ru.andreycherenkov.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.andreycherenkov.listener.events.ApplicationStatusChangeEvent;
import ru.andreycherenkov.service.EmailService;

@RequiredArgsConstructor
@Component
public class ApplicationEmailListener {

    private final EmailService emailService;

    @Async("emailExecutor")
    @EventListener
    public void handleStatusChanged(ApplicationStatusChangeEvent event) {
        emailService.sendStatusNotification(
                event.getEmail(),
                event.getOldStatus(),
                event.getNewStatus()
        );
    }
}
