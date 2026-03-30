package tn.citypulse.documentdemand.model;

import jakarta.persistence.*;
import lombok.Data;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.shared.enums.DocumentType;
import tn.citypulse.shared.enums.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DocumentDemand")
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private DemandStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "attachment_id", nullable = true)
    private Attachment attachment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proof_id", nullable = true)
    private Proof proof;

    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
