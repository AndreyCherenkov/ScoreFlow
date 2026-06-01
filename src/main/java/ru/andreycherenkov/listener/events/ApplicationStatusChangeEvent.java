package ru.andreycherenkov.listener.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.andreycherenkov.enums.ApplicationStatus;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ApplicationStatusChangeEvent {
    private UUID applicationId;
    private String email;
    private ApplicationStatus oldStatus;
    private ApplicationStatus newStatus;
}
