package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.andreycherenkov.enums.DocumentType;

import java.time.Instant;
import java.util.UUID;

//todo DocumentVersions entity
@NoArgsConstructor
@Getter
@Entity(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "document_id")
    private UUID documentId;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "storage_type")
    private String storageType;

    @Column(name = "storage_path")
    private String storagePath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private DocumentContent content;
}
