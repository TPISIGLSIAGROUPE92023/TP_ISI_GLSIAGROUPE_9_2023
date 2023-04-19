package com.example.api_bank.repo;

import com.example.api_bank.model.Client;
import com.example.api_bank.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompteRepo extends JpaRepository<Compte, String> {


    List<Compte> findByProprietaire(Client proprietaire);

}
