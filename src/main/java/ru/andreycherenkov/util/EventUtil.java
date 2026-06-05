package ru.andreycherenkov.util;

import org.springframework.context.ApplicationEventPublisher;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.enums.ApplicationStatus;
import ru.andreycherenkov.listener.events.ApplicationStatusChangeEvent;

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
