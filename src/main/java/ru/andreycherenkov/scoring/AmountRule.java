package ru.andreycherenkov.scoring;

import org.springframework.stereotype.Component;
import ru.andreycherenkov.entity.LoanApplication;

import java.math.BigDecimal;

@Component
public class AmountRule implements ScoringRule {

    @Override
    public int evaluate(LoanApplication application) {
        if (application.getAmount().compareTo(new BigDecimal("1000000")) > 0) {
            return -50;
        }
        return 100;
    }
}
