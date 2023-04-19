package com.example.api_bank.controller;

import com.example.api_bank.model.*;
import com.example.api_bank.payload.PostCompte;
import com.example.api_bank.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/comptes")
public class CompteController {
    @Autowired
    private DepoRepo depoRepo;
    @Autowired
    private RetraitRepo retraitRepo;
    @Autowired
    public CompteRepo compteRepo;
    @Autowired
    public ClientRepo clientRepo;
    @Autowired
    public VirementRepo virementRepo;
    @GetMapping
    public List<Compte> getComptes(){
        return compteRepo.findAll();
    }

    @GetMapping(value="/{numCpt}")
    public ResponseEntity<?> getCompteById(@PathVariable String numCpt) {
        Optional<Compte> optionalCompte = compteRepo.findById(numCpt);
        if (optionalCompte.isPresent()) {
            Compte compte = optionalCompte.get();
            return ResponseEntity.ok(compte);
        } else {
            String message = "compte  " + numCpt + " non trouvé";
            Map<String, String> response = new HashMap<>();
            response.put("error", message);
            response.put("status", "NOT_FOUND");
            response.put("code", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value ="/{numCpt}")
    public ResponseEntity<?> supprimerCompte(@PathVariable String numCpt){
        Optional<Compte> optionalCompte = compteRepo.findById(numCpt);
        if (optionalCompte.isPresent()) {
            Compte compteSuppr = optionalCompte.get();
            compteRepo.delete(compteSuppr);
            return ResponseEntity.ok().body("Compte " + numCpt + " supprimé");
        } else {
            String message = "Le compte " + numCpt + " non trouvé";
            Map<String, String> response = new HashMap<>();
            response.put("error", message);
            response.put("status", "NOT_FOUND");
            response.put("code", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping
    public ResponseEntity<?> enregistrerCompte(@RequestBody PostCompte postcompte) {
//        if (compte.getSolde()!=0){
//            String errorMessage = "Le solde d'un compte à sa création est null, cette opération est impossible";
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//        }
        try {
            Client proprietaire = clientRepo.findById(postcompte.getProprietaire()).get();
            Compte compteSaved = new Compte(postcompte.getTypeCompte(),proprietaire);
            compteRepo.save(compteSaved);
            return ResponseEntity.ok("Compte " + compteSaved.getNumCompte() + " créé");
        } catch (Exception e) {
            String errorMessage = "Erreur lors de l'enregistrement du compte: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


    @GetMapping("/{numCpt}/proprietaire")
    public ResponseEntity<?> getProprietaireByCompteId(@PathVariable String numCpt) {
        Optional<Compte> optionalCompte = compteRepo.findById(numCpt);
        if (optionalCompte.isPresent()) {
            Compte compte = optionalCompte.get();
            return ResponseEntity.ok(compte.getProprietaire());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compte " + numCpt + " non trouvé");
        }
    }


    @PutMapping("/depot")
    public ResponseEntity<?> faireDepot(@RequestBody Depot depot) {
        try {
            Optional<Compte> Ocpt = compteRepo.findById(depot.getNumCompte());
            if(Ocpt.isPresent()){
                Compte cpt = Ocpt.get();
                cpt.setSolde(depot.getMontant()+ cpt.getSolde());
                compteRepo.save(cpt);
                depoRepo.save(depot);
                return ResponseEntity.ok(depot);
            }else {
                String message = "compte " + depot.getNumCompte() + " non trouvé ";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }

        }catch(Exception e){
            String message = "Erreur lors de l'opération, compte "+depot.getNumCompte()+" non trouvé , relisez bien la documentation.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }


    }
    @PutMapping("/retrait")
    public ResponseEntity<?> faireRetrait(@RequestBody Retrait retrait) {
        try {
            Optional<Compte> Ocpt = compteRepo.findById(retrait.getNumCompte());
            if(Ocpt.isPresent()){
                Compte cpt = Ocpt.get();
                double nouveauSolde = cpt.getSolde() - retrait.getMontant();
                if (nouveauSolde<0){
                    return ResponseEntity.badRequest().body("Le solde du compte n'est pas suffisant pour un retrait ");
                }else{
                    cpt.setSolde(nouveauSolde);
                    compteRepo.save(cpt);
                    retraitRepo.save(retrait);
                    return ResponseEntity.ok(retrait);
                }


            }else {
                String message = "compte " + retrait.getNumCompte() + " non trouvé ";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }

        }catch(Exception e){
            String message = "Erreur lors de l'opération, compte "+retrait.getNumCompte() +" non trouvé , relisez bien la documentation.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }

    }


    @PutMapping("/virement")
    public ResponseEntity<?> faireVirement(@RequestBody Virement virement) {
        Optional<Compte> optionalCompteSource = compteRepo.findById(virement.getNumeroCompteSource());
        Optional<Compte> optionalCompteDest = compteRepo.findById(virement.getNumeroCompteDest());
//        if (optionalCompteSource.isEmpty()){
//            return ResponseEntity.badRequest().body("Compte "+virement.getNumero_compte_source()+" non trouvé ");
//        }
//        if (optionalCompteDest.isEmpty()){
//            return ResponseEntity.badRequest().body("Compte "+virement.getNumero_compte_dest()+" non trouvé ");
//        }

        if (optionalCompteSource.isPresent() && optionalCompteDest.isPresent()) {
            Compte compteSource = optionalCompteSource.get();
            Compte compteDest = optionalCompteDest.get();
            double nouveauSoldeSource = compteSource.getSolde() - virement.getMontant();
            if (nouveauSoldeSource < 0) {
                return ResponseEntity.badRequest().body("Le solde du compte n'est pas suffisant pour un virement ");
            }
            double nouveauSoldeDest = compteDest.getSolde() + virement.getMontant();
            compteSource.setSolde(nouveauSoldeSource);
            compteDest.setSolde(nouveauSoldeDest);
            compteRepo.saveAll(Arrays.asList(compteSource, compteDest));
            virementRepo.save(virement);
            return ResponseEntity.ok(virement);
        } else {
            String message = "Erreur lors de l'opération,l'un ou les 2 comptes n'existent pas, relisez bien la documentation.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @GetMapping("/{numCompte}/retraits")
    public ResponseEntity<?> retraitCompte(@PathVariable String numCompte){
        Optional<Compte> Ocpt = compteRepo.findById(numCompte);
        if (Ocpt.isPresent()){
            List<Retrait> retraits = retraitRepo.findByNumCompte(numCompte);
            return ResponseEntity.ok(retraits);
        }
        String message = "Compte " + numCompte + " non trouvé";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/{numCompte}/depots")
    public ResponseEntity<?> depotCompte(@PathVariable String numCompte){
        Optional<Compte> Ocpt = compteRepo.findById(numCompte);
        if (Ocpt.isPresent()){
            List<Depot> depots = depoRepo.findByNumCompte(numCompte);
            return ResponseEntity.ok(depots);
        }
        String message = "Compte " + numCompte + " non trouvé";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/{numCompte}/virementsRecu")
    public ResponseEntity<?> virementsRecu(@PathVariable String numCompte){
        Optional<Compte> Ocpt = compteRepo.findById(numCompte);
        if (Ocpt.isPresent()){
            List<Virement> virements = virementRepo.findByNumeroCompteDest(numCompte);
            return ResponseEntity.ok(virements);
        }
        String message = "Compte " + numCompte + " non trouvé";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/{numCompte}/virementsEnvoye")
    public ResponseEntity<?> virementsEnvoye(@PathVariable String numCompte){
        Optional<Compte> Ocpt = compteRepo.findById(numCompte);
        if (Ocpt.isPresent()){
            List<Virement> virements = virementRepo.findByNumeroCompteSource(numCompte);
            return ResponseEntity.ok(virements);
        }
        String message = "Compte " + numCompte + " non trouvé";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


}
