package ru.andreycherenkov.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.listener.events.ApplicationStatusChangeEvent;

@Component
public class EventUtil {

    private EventUtil() {}

    public static void publishEmailEvent(
            ApplicationEventPublisher publisher,
            LoanApplication application,
            ApplicationStatus oldStatus,
            ApplicationStatus newStatus
    ) {

        publisher.publishEvent(new ApplicationStatusChangeEvent(
                application.getApplicationId(),
                application.getCustomer().getEmail(),
                oldStatus,
                newStatus
        ));
    }
}
