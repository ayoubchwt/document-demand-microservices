package tn.citypulse.documentdemand.model;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.OneToOne;
import tn.citypulse.documentdemand.model.Enum.ProofType;

import java.time.LocalDateTime;

@Entity
@Data
public class Proof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProofType type;

    private String fileUrl;

    @Column()
    private LocalDateTime createdAt;

    @Column()
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "proof")
    private Demand demand;

    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
