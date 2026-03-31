package tn.citypulse.documentdemand.service;

import tn.citypulse.documentdemand.model.Attachment;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.model.Proof;
import tn.citypulse.shared.enums.PaymentStatus;

import java.util.List;

public interface IDemandService {

    Demand createDemand(Demand demand);

    List<Demand> getAllDemands();

    Demand getDemandById(Long id);

    List<Demand> getDemandsByUser(Long userId);

    //List<Demand> getDemandsByMunicipality(Long municipalityId);

    List<Demand> getDemandsByStatus(DemandStatus demandStatus);

    Demand updateDemandStatus(Demand demand);

    Demand updatePaymentStatus(Long id , PaymentStatus paymentStatus);

    Demand attachDocument(Long id , Attachment attachment);

    Demand proofDocument(Long id , Proof proof);

    void deleteDemand(Long id);
}
