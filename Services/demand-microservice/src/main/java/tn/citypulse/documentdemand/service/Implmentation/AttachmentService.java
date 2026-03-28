package tn.citypulse.documentdemand.service.Implmentation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.citypulse.documentdemand.exception.NoSuchDemandExistsException;
import tn.citypulse.documentdemand.exception.NullAttachmentException;
import tn.citypulse.documentdemand.model.Attachment;
import tn.citypulse.documentdemand.repository.AttachmentRepository;
import tn.citypulse.documentdemand.service.IAttachmentService;
@Service
@AllArgsConstructor
public class AttachmentService implements IAttachmentService {
    private final AttachmentRepository attachmentRepository;
    @Override
    public Attachment getAttachmentById(Long id){
        return getAttachmentOrThrow(id);
    }
    @Override
    public Attachment getAttachmentByDemandId(Long demandId){
        if(demandId == null) {
            throw new NullAttachmentException("The provided demand Id for the attachment is null");
        }
        return attachmentRepository.findByDemandId(demandId);
    }
    @Override
    public Attachment createAttachment(Attachment attachment) {
        if(attachment == null) {
            throw new NullAttachmentException();
        }
        return attachmentRepository.save(attachment);
    }
    @Override
    public void deleteAttachment(Long id){
        Attachment attachment = getAttachmentOrThrow(id);
        attachmentRepository.delete(attachment);
    }

    // helper
    private Attachment getAttachmentOrThrow(Long id){
        if (id == null) throw new NullAttachmentException("The provided attachment Id is null");
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchDemandExistsException("Attachment with ID " + id + " does not exist"));
    }
}

