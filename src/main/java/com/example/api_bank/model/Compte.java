package com.example.api_bank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Random;

import java.time.LocalDate;

@Entity
public class Compte {
    @Id
    private String numCompte;
    @Column
    private TypeCompte typeCompte;
    @Column
    private LocalDate dateCreation = LocalDate.now();
    @Column
    private double solde = 0;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client proprietaire;

    public String getNumCompte() {
        return numCompte;
    }



    public void setNumCompte(String numCompte) {
        this.genererNumCompte();
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }


    public Compte() {
        this.genererNumCompte();
    }

    public Client getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Client proprietaire) {
        this.proprietaire = proprietaire;
    }

    public enum TypeCompte {
        COURANT,EPARGNE
    }

    public void genererNumCompte() {
        String prefixe = generateRandomAlphaNumeric(5);
        String annee = String.valueOf(dateCreation.getYear());
        this.numCompte = prefixe + annee;
    }

    private String generateRandomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    public Compte(TypeCompte typeCompte,Client proprietaire ){
        this.genererNumCompte();
        this.typeCompte = typeCompte;
        this.proprietaire = proprietaire;
    }
}

