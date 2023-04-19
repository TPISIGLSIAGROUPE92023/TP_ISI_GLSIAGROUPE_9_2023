package com.example.api_bank.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private Date dateNaissance;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @Column
    private String adresse;
    @Column(nullable = true, unique = true)
    private String numTel;

    @Column(nullable = true,unique = true)
    private String courriel;
    @Column(nullable = true)
    private String nationalite;
    @JsonIgnore
    @OneToMany(mappedBy = "proprietaire",cascade = CascadeType.ALL)
    public List<Compte> comptes;

    public long getId(){
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public enum Sexe{
        M,F
    }

    public List<Compte> getComptes(){
        return comptes;
    }

}
