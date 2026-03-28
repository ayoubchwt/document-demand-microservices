package tn.citypulse.documentdemand.service.Implmentation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.citypulse.documentdemand.exception.NoSuchProofExistsException;
import tn.citypulse.documentdemand.exception.NullProofException;
import tn.citypulse.documentdemand.model.Proof;
import tn.citypulse.documentdemand.repository.ProofRepository;
import tn.citypulse.documentdemand.service.IProofService;

@Service
@AllArgsConstructor
public class ProofService implements IProofService {
    private final ProofRepository proofRepository;
    @Override
    public Proof createProof(Proof proof){
        if (proof == null) {
            throw new NullProofException();
        }
        return proofRepository.save(proof);
    }

    @Override
    public Proof getProofById(Long id){
        return getProofOrThrow(id);
    }

    @Override
    public Proof getProofByDemandId(Long demandId){
        if(demandId == null) {
            throw new NullProofException("the provided demand Id for the proof is null");
        }
        return proofRepository.findByDemandId(demandId);
    }

    @Override
    public void deleteProof(Long id){
        Proof proof = getProofOrThrow(id);
        proofRepository.delete(proof);
    }

    // helper
    private Proof getProofOrThrow(Long id){
        if (id == null) throw new NullProofException("The provided proof Id is null");
        return proofRepository.findById(id)
                .orElseThrow(() -> new NoSuchProofExistsException("Proof with ID " + id + " does not exist"));
    }
}
