package tn.citypulse.documentdemand.dto.Attachment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentResponseDTO {

    private Long id;

    private String fileUrl;

    private String municipalityId;
}
