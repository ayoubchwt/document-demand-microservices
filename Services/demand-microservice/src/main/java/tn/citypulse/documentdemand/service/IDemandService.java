package tn.citypulse.documentdemand.service;

import org.springframework.web.multipart.MultipartFile;
import tn.citypulse.documentdemand.model.Attachment;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.model.Proof;
import tn.citypulse.shared.enums.DocumentType;
import tn.citypulse.shared.enums.PaymentStatus;

import java.util.List;

public interface IDemandService {

    Demand createDemand(Demand demand);

    List<Demand> getAllDemands();

    Demand getDemandById(Long id);

    List<Demand> getDemandsByUser(Long userId);

    DocumentType getDemandTypeById(Long id);

    //List<Demand> getDemandsByMunicipality(Long municipalityId);

    List<Demand> getDemandsByStatus(DemandStatus demandStatus);

    Demand updateDemandStatus(Demand demand);

    Demand updatePaymentStatus(Long id , PaymentStatus paymentStatus);

    Demand attachDocument(Long demandId, MultipartFile file);

    Demand proofDocument(Long demandId, MultipartFile file, String type);

    void deleteDemand(Long id);
}
