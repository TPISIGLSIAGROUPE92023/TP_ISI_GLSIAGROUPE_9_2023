package com.example.api_bank.repo;

import com.example.api_bank.model.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirementRepo extends JpaRepository<Virement, String> {
    List<Virement> findByNumeroCompteSource(String NumeroCompteSource);
    List<Virement> findByNumeroCompteDest(String numeroCompteDest);
}
