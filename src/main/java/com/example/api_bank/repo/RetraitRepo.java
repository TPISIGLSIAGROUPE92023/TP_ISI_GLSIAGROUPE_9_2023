package com.example.api_bank.repo;

import com.example.api_bank.model.Retrait;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetraitRepo extends JpaRepository<Retrait, String> {
    List<Retrait> findByNumCompte(String numCompte);

}
