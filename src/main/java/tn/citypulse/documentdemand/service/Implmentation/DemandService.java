package tn.citypulse.documentdemand.service.Implmentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.citypulse.documentdemand.exception.NoSuchAttachmentExistsException;
import tn.citypulse.documentdemand.exception.NoSuchDemandExistsException;
import tn.citypulse.documentdemand.exception.NullDemandException;
import tn.citypulse.documentdemand.model.Attachment;
import tn.citypulse.documentdemand.model.Demand;
import tn.citypulse.documentdemand.model.Enum.DemandStatus;
import tn.citypulse.documentdemand.repository.DemandRepository;
import tn.citypulse.documentdemand.service.IDemandService;

import java.util.List;

@Service
@AllArgsConstructor
public class DemandService implements IDemandService {

    private final DemandRepository demandRepository;

    @Override
    public Demand createDemand(Demand demand){
        if(demand == null) {
            throw new NullDemandException();
        }
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
    public List<Demand> getDemandsByMunicipality(Long municipalityId){
        if(municipalityId == null) {
            throw new NullDemandException("The provided municipality Id for the demand is null");
        }
        return demandRepository.findAllByMunicipalityId(municipalityId);
    }

    @Override
    public List<Demand> getDemandsByStatus(DemandStatus demandStatus){
        if(demandStatus == null) {
            throw new NullDemandException("The provided demand Status for the demand is null");
        }
        return demandRepository.findAllByStatus(demandStatus);
    }

    @Override
    public Demand updateDemandStatus(Long id , DemandStatus demandStatus){
        if(demandStatus == null) {
            throw new NullDemandException("The provided demand status for the demand is null");
        }
        Demand demand = getDemandOrThrow(id);
        demand.setStatus(demandStatus);
        return demandRepository.save(demand);
    }

    @Override
    public Demand attachDocument(Long id , Attachment attachment){
        if(attachment == null) {
            throw new NullDemandException("The provided attachment for the demand is null");
        }
        Demand demand = getDemandOrThrow(id);
        demand.setAttachment(attachment);
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
                .orElseThrow(() -> new NoSuchAttachmentExistsException("Demand with ID " + id + " does not exist"));
    }
}
