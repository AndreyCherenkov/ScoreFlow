package ru.andreycherenkov.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.service.DocumentService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/document")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/generate/{applicationId}/{userId}") //todo delete userId
    public ResponseEntity<UUID> generateApplicationPdf(@PathVariable UUID applicationId, @PathVariable UUID userId) {
        return ResponseEntity.ok(documentService.generateApplicationReport(applicationId, userId));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getApplicationPdf(@PathVariable UUID fileId) {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf")
//                .body();
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
