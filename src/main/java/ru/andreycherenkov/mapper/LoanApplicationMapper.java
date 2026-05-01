package ru.andreycherenkov.mapper;

import org.springframework.stereotype.Component;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.api.dto.LoanApplicationResponse;

@Component
public class LoanApplicationMapper {

    public LoanApplicationResponse fromEntity(LoanApplication application) {
        return new LoanApplicationResponse(
                application.getApplicationId(),
                application.getAmount(),
                application.getApplicationStatus(),
                application.getTermMonth(),
                application.getPurpose(),
                application.getCreatedAt()
        );
    }

}
