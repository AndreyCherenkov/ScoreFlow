package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanPurpose {
    CAR("car"),
    ;

    private final String purpose;

}
