package com.example.api_bank.repo;

import com.example.api_bank.model.Depot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepoRepo extends JpaRepository<Depot, String> {
    List<Depot> findByNumCompte(String numCompte);
}
