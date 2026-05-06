package ru.andreycherenkov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.andreycherenkov.entity.Document;
import ru.andreycherenkov.entity.DocumentContent;
import ru.andreycherenkov.entity.LoanApplication;
import ru.andreycherenkov.enums.DocumentType;
import ru.andreycherenkov.repository.ApplicationRepository;
import ru.andreycherenkov.repository.CustomerRepository;
import ru.andreycherenkov.repository.DocumentRepository;

import java.util.UUID;

//todo в шаблоне заявке определить поведение для всех ApplicationStatus
@Service
@AllArgsConstructor
public class DocumentService {

    private final CustomerRepository customerRepository;
    private final ApplicationRepository applicationRepository;
    private final DocumentRepository documentRepository;

    private final TemplateEngine templateEngine;

    //todo saving in db/MinIO OR in another objective storage (yandex for example)
    //todo получать пользователя не из БД, а из контекста безопасности
    //todo генерация другим способом?
    //todo method safety
    @Transactional
    public UUID generateApplicationReport(UUID applicationId, UUID customerId) {

        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found: " + applicationId));

        var customer = customerRepository.getReferenceById(customerId);

        var context = new Context();
        context.setVariable(LoanApplication.Fields.applicationId, application.getApplicationId());
        context.setVariable(LoanApplication.Fields.amount, application.getAmount());
        context.setVariable(LoanApplication.Fields.termMonth, application.getTermMonth());
        context.setVariable(LoanApplication.Fields.purpose, application.getPurpose());

        var html = templateEngine.process("application-report.html", context);
        var bytes = html.getBytes();

        var document = Document.builder()
                .contentType("application/pdf")
                .fileSize(bytes.length)
                .documentType(DocumentType.APPLICATION_REPORT)
                .storageType("DB")
                .storagePath("./reports")
                .customer(customer)
                .build();

        var content = new DocumentContent();
        content.setContent(html.getBytes());

        document.setContent(content);

        documentRepository.save(document);

        return document.getDocumentId();
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
