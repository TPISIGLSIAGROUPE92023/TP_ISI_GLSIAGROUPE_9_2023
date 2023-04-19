package com.example.api_bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Random;

@Entity
public class Virement {
    @Id
    private String txnid;
    @Column
    private String numeroCompteSource;
    @Column
    private String numeroCompteDest;
    @Column
    private double montant;
    @Column
    private LocalDate date_virement = LocalDate.now();

    public Virement() {
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


    public LocalDate getDate_virement() {
        return date_virement;
    }


    public String getNumeroCompteSource() {
        return numeroCompteSource;
    }

    public void setNumeroCompteSource(String numeroCompteSource) {
        this.numeroCompteSource = numeroCompteSource;
    }

    public String getNumeroCompteDest() {
        return numeroCompteDest;
    }

    public void setNumeroCompteDest(String numeroCompteDest) {
        this.numeroCompteDest = numeroCompteDest;
    }
}