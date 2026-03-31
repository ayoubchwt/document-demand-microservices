package tn.citypulse.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.citypulse.shared.enums.DocumentType;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeResponseDTO {
    private DocumentType type;
}
