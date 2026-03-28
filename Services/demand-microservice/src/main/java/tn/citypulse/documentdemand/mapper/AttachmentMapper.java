package tn.citypulse.documentdemand.mapper;

import org.mapstruct.Mapper;
import tn.citypulse.documentdemand.dto.Attachment.AttachmentResponseDTO;
import tn.citypulse.documentdemand.dto.Attachment.CreateAttachmentDTO;
import tn.citypulse.documentdemand.model.Attachment;


@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    Attachment toEntity(CreateAttachmentDTO dto);
    AttachmentResponseDTO toDTO(Attachment attachment);
}
