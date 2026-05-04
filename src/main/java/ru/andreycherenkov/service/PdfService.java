package ru.andreycherenkov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.repository.ApplicationRepository;

import java.util.UUID;

//todo в шаблоне заявке определить поведение для всех ApplicationStatus
@Service
@AllArgsConstructor
public class PdfService {

    private final ApplicationRepository applicationRepository;

    private final TemplateEngine templateEngine;

    //todo method safety
    public UUID generateApplicationReport(UUID applicationId) {
        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found: " + applicationId)); //todo define exception

        var context = new Context();
        context.setVariable(LoanApplication.Fields.applicationId, application.getApplicationId());
        context.setVariable(LoanApplication.Fields.amount, application.getAmount());
        context.setVariable(LoanApplication.Fields.termMonth, application.getTermMonth());
        context.setVariable(LoanApplication.Fields.purpose, application.getPurpose());

        var html = templateEngine.process("application-report.html", context);
        //todo saving in db/MinIO OR in another objective storage (yandex for example)
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    public byte[] generateScoreReport(UUID applicationId) {
//        try {
//            var context = new Context();
//            context.setVariable("name", scoreDto.getName());
//            context.setVariable("score", scoreDto.getScore());
//            context.setVariable("approved", scoreDto.isApproved());
//
//            String html = templateEngine.process("score-report", context);
//
//            try (var outputStream = new ByteArrayOutputStream()) {
//
//                var builder = new PdfRendererBuilder();
//                builder.useFastMode();
//                builder.withHtmlContent(html, null);
//                builder.toStream(outputStream);
//                builder.run();
//
//                return outputStream.toByteArray();
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Ошибка генерации PDF", e);
//        }
//    }
}
