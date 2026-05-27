package ru.andreycherenkov.scoring;

import ru.andreycherenkov.entity.LoanApplication;

public class TermRule implements ScoringRule {

    @Override
    public int evaluate(LoanApplication application) {
        if (application.getTermMonth() != null && application.getTermMonth() < 12) {
            return 150;
        }
        return 50;
    }
}
