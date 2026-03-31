package tn.citypulse.documentdemand.dto.Attachment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAttachmentDTO {

    @NotNull(message = "File is required")
    private MultipartFile file;

}
