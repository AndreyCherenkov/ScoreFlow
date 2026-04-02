package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatus {
    NEW("new"),
    IN_REVIEW("in_review"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String status;
}
