package tn.citypulse.documentdemand.service;

import tn.citypulse.documentdemand.model.Attachment;

public interface IAttachmentService {

    Attachment getAttachmentById(Long id);

    Attachment getAttachmentByDemandId(Long demandId);

    Attachment saveAttachment(Attachment attachment);

    void deleteAttachment(Long id);
}
