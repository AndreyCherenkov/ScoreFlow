package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity(name = "document_contents")
public class DocumentContent {

    @Id
    private UUID documentId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Lob
    @Column(nullable = false)
    private byte[] content;


}
