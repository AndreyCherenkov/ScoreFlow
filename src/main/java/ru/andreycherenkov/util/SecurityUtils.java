package ru.andreycherenkov.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.andreycherenkov.security.CustomerDetails;

import java.util.UUID;

public class SecurityUtils {

    private SecurityUtils() {}

    public static UUID currentUserId() {

        var authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        var principal = (CustomerDetails) authentication.getPrincipal();

        return principal.getUserId();
    }
}
