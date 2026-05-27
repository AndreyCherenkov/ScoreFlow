package ru.andreycherenkov.scoring;

import ru.andreycherenkov.entity.LoanApplication;

//todo описание, что это упрощенная реализация скоринга, основная реализация основана на ML
public interface ScoringRule {

    int evaluate(LoanApplication application);

}
