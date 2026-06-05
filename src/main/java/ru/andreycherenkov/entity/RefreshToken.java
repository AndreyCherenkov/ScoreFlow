package ru.andreycherenkov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

//todo хранить в куки?
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "refresh_tokens")
public class RefreshToken {

    public RefreshToken(String tokenHash, LocalDateTime createdAt, LocalDateTime expirationDate, boolean revoked, Customer customer) {
        this.tokenHash = tokenHash;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.revoked = revoked;
        this.customer = customer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id")
    private UUID tokenId;

    @Column(name = "token_hash", nullable = false, unique = true)
    private String tokenHash;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; //todo сделать однонаправленную связь
}
