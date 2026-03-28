package tn.citypulse.documentdemand.dto.Attachment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAttachmentDTO {

    @NotBlank(message = "File name is required")
    private String fileName;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    @NotBlank(message = "Municipality ID is required")
    private String municipalityId;
}
