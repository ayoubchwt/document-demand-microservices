package tn.citypulse.documentdemand.model;

import jakarta.persistence.*;
import lombok.Data;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.model.Enum.DocumentType;
import tn.citypulse.documentdemand.model.Enum.PaymentStatus;

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
    private Long municipalityId;

    @Column(nullable = false)
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

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;

    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
