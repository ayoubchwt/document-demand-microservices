package tn.citypulse.documentdemand.service;
import tn.citypulse.documentdemand.model.Proof;

public interface IProofService {

    Proof createProof(Proof proof);

    Proof getProofById(Long id);

    Proof getProofByDemandId(Long demandId);

    void deleteProof(Long id);
}
