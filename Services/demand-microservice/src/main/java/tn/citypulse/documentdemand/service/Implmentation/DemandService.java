package tn.citypulse.documentdemand.service.Implmentation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.citypulse.documentdemand.exception.InvalidDemandStateException;
import tn.citypulse.documentdemand.exception.InvalidPaymentStateException;
import tn.citypulse.documentdemand.exception.NoSuchDemandExistsException;
import tn.citypulse.documentdemand.exception.NullDemandException;
import tn.citypulse.documentdemand.model.Attachment;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.model.Enum.ProofType;
import tn.citypulse.documentdemand.model.Proof;
import tn.citypulse.documentdemand.repository.DemandRepository;
import tn.citypulse.documentdemand.service.ICloudinaryService;
import tn.citypulse.documentdemand.service.IDemandService;
import tn.citypulse.shared.enums.DocumentType;
import tn.citypulse.shared.enums.PaymentStatus;

import java.util.List;

@Service
@AllArgsConstructor
public class DemandService implements IDemandService {

    private final DemandRepository demandRepository;
    private final ICloudinaryService cloudinaryService;
    @Override
    public Demand createDemand(Demand demand){
        if(demand == null) {
            throw new NullDemandException();
        }
        demand.setStatus(DemandStatus.IN_REVIEW);
        demand.setPaymentStatus(PaymentStatus.UNPAID);
        return demandRepository.save(demand);
    }

    @Override
    public List<Demand> getAllDemands(){
        return demandRepository.findAll();
    }

    @Override
    public Demand getDemandById(Long id){
        return getDemandOrThrow(id);
    }

    @Override
    public List<Demand> getDemandsByUser(Long userId){
        if(userId == null) {
            throw new NullDemandException("The provided user Id for the demand is null");
        }
        return demandRepository.findAllByUserId(userId);
    }
    @Override
    public DocumentType getDemandTypeById(Long id) {
        Demand demand = getDemandOrThrow(id);
        return demand.getDocumentType();
    }
    /*
    @Override
    public List<Demand> getDemandsByMunicipality(Long municipalityId){
        if(municipalityId == null) {
            throw new NullDemandException("The provided municipality Id for the demand is null");
        }
        return demandRepository.findAllByMunicipalityId(municipalityId);
    }
    */
    @Override
    public List<Demand> getDemandsByStatus(DemandStatus demandStatus){
        if(demandStatus == null) {
            throw new NullDemandException("The provided demand Status for the demand is null");
        }
        return demandRepository.findAllByStatus(demandStatus);
    }
    
    @Override
    public Demand updateDemandStatus(Demand demand){
        if(demand.getStatus() == null) {
            throw new NullDemandException("The provided demand status for the demand is null");
        }
        Demand demandToUpdate = getDemandOrThrow(demand.getId());
        if (demandToUpdate.getStatus() != DemandStatus.IN_REVIEW) {
            throw new InvalidDemandStateException("Only IN_REVIEW demands can be updated");
        }
        demandToUpdate.setStatus(demand.getStatus());
        demandToUpdate.setMunicipalityId(demand.getMunicipalityId());
        return demandRepository.save(demandToUpdate);
    }
    @Override
    public Demand updatePaymentStatus(Long id , PaymentStatus paymentStatus){
        if(paymentStatus == null) {
            throw new NullDemandException("The provided payment status for the demand is null");
        }
        Demand demand = getDemandOrThrow(id);
        if (demand.getStatus() != DemandStatus.APPROVED) {
            throw new InvalidDemandStateException("Only IN_REVIEW demands can be updated");
        }
        demand.setPaymentStatus(paymentStatus);
        return demandRepository.save(demand);
    }

    @Override
    @Transactional
    public Demand attachDocument(Long demandId, MultipartFile file) {
        Demand demand = getDemandOrThrow(demandId);
        if (demand.getStatus() != DemandStatus.APPROVED) {
            throw new InvalidDemandStateException("Cannot attach document: demand is not approved");
        }
        if (demand.getPaymentStatus() != PaymentStatus.PAID) {
            throw new InvalidPaymentStateException("Cannot attach document: payment not completed");
        }
        Attachment attachment = new Attachment();
        String uploadUrl = cloudinaryService.uploadFile(file, "Attachment");
        attachment.setFileUrl(uploadUrl);
        demand.setAttachment(attachment);
        return demandRepository.save(demand);
    }

    @Override
    @Transactional
    public Demand proofDocument(Long demandId, MultipartFile file, ProofType type) {
        Demand demand = getDemandOrThrow(demandId);
        Proof proof = new Proof();
        proof.setType(type);
        String uploadUrl = cloudinaryService.uploadFile(file, "Proof");
        proof.setFileUrl(uploadUrl);
        demand.setProof(proof);
        return demandRepository.save(demand);
    }
    @Override
    public void deleteDemand(Long id){
        Demand demand = getDemandOrThrow(id);
        demandRepository.delete(demand);
    }
    // helper
    private Demand getDemandOrThrow(Long id){
        if (id == null) throw new NullDemandException("The provided demand Id is null");
        return demandRepository.findById(id)
                .orElseThrow(() -> new NoSuchDemandExistsException("Demand with ID " + id + " does not exist"));
    }
}
