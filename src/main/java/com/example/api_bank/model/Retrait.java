package com.example.api_bank.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Random;

@Entity
public class Retrait {
    @Id
    private String txnid;
    @Column
    private String numCompte;
    @Column
    private double montant;
    @Column
    private LocalDate dateRetrait = LocalDate.now();

    public Retrait() {
        // Génère aléatoirement une chaîne de 13 caractères numériques
        Random rand = new Random();
        int num = rand.nextInt(1000000000);
        this.txnid = String.format("%013d", num);
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }



    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }
}