package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.service.PdfService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/document")
public class DocumentController {

    private final PdfService pdfService;

    @PostMapping("/generate/{applicationId}")
    public ResponseEntity<UUID> generateApplicationPdf(@PathVariable UUID applicationId) {
        return ResponseEntity.ok(pdfService.generateApplicationReport(applicationId));
    }

    @GetMapping("/fileId")
    public ResponseEntity<byte[]> getApplicationPdf(@PathVariable UUID fileId) {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf")
//                .body();
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
