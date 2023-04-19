package com.example.api_bank.repo;

import com.example.api_bank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByCourriel(String courriel);
    Client findByNumTel(String numTel);

}
