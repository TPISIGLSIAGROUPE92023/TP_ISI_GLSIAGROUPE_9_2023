# API de gestion des clients et des comptes



## Introduction
La société bancaire(fictive) « Ega » voudrait mettre en place un système de gestion de ses clients et des comptes que ces derniers possèdent. Un client peut avoir plusieurs comptes. 
La banque met à disposition des clients deux types de comptes : un compte épargne et un compte courant. 
Un compte est caractérisé par le numéro de compte, le type de compte, la date de création du compte, le solde du compte et le propriétaire du compte. 
Pour le client on retiendra les informations suivantes : nom, prénom, date de naissance, sexe, adresse, numéro de téléphone, courriel et nationalité. 
Le numéro de compte est un ensemble de 5 caractères majuscules alphanumériques générés aléatoirement auquel on concatène l’année de création du compte. 
Le numéro de compte est unique pour un compte. 
Lors de la création du compte son solde est nul. 

### Objectifs:
1.	Mettre en place une API CRUD pour gérer les comptes et les clients.
2.	Ajouter les possibilités pour le client de : 
-	Faire un versement sur son compte ;
-	Faire un retrait sur son compte si le solde le permet ;
-	Faire un virement d’un compte à un autre


## Modèle

### Client
| Champ | Type | Description |
|-------|------|-------------|
| id | int | Identifiant unique du client générer automatiquement |
| nom | string | Nom du client |
| prenom | string | Prenom du client |
| dateNaissance | date | Date de de naissance du client (format "yyyy-mm-dd") |
| adresse | string | Adresse du client |
| sexe | string | sexe du client, il doit obligatirement être "M" ou "F" |
| numTel | string | Numéro de télephone du client |
| courriel | string | Courriel du client |
| nationalite | string | Nationalité du client |

### Compte
| Champ | Type | Description |
|-------|------|-------------|
| numCompte | int | Numéro de compte générer automatiquement |
| typeCompte | string | Type de compte, il doit obligatoirement être "COURANT" ou "EPARGNE" |
| solde | double | Solde du compte, default=0 |
| dateCreation | String | Date de création générer automatiquement avec la date du jour |
| proprietaire | Client | Client proprietaire du compte |

## Endpoints

### Opérations sur les clients

| Méthode | URL | Description | Exemple de corps de requête valide |
|---------|-----|-------------|------------------------------------|
| GET | /clients | Récupère tous les clients |  |
| GET | /clients/{id} | Récupère un client spécifique |  |
| POST | /clients | Créer un nouveau client | [JSON](#creerclient) |
| PUT | /clients/{id} | Met à jour un client existant | [JSON](#modifierclient) |
| DELETE | /clients/{id} | Supprime un client existant |   |
| GET | /clients/{id}/comptes | Récupère les comptes d'un client spécifique |   |

### Opérations sur les comptes

| Méthode | URL | Description | Exemple de corps de requête valide |
|---------|-----|-------------|------------------------------------|
| GET | /comptes | Récupère tous les comptes |   |
| GET | /comptes/{numCompte} | Récupère un compte spécifique |   |
| POST | /comptes | Crée un nouveau compte | [JSON](#creercompte) |
| DELETE | /comptes/{numCompte} | Supprime un compte existant |   |
| GET | /comptes/{numCpt}/proprietaire | Récupère le proprietaire du compte |    |
| PUT | /comptes/retrait| Faire le retrait d'un montant sur un compte | [JSON](#retrait)  |
| PUT | /comptes/depot | Faire le depot d'un montant sur un compte | [JSON](#depot) |
| PUT | /comptes/virement | Faire le virement d'un compte(numCompteSource) source à un compte de destination (numCompteDest) avec un montant sur un compte |  [JSON](#virement)   |
| GET | /comptes/{numCompte}/depots | Récupère les dépots effectués sur un compte |    |
| GET | /comptes/{numCompte}/retrait | Récupère les retraits effectués sur un compte |    |
| GET | /comptes/{numCompte}/virementsRecu | Récupère les virements reçus par un compte |    |
| GET | /comptes/{numCompte}/virementsEnvoye | Récupère les virements envoyés par un compte |    |

### Informations sur les opérations(dépot, retraits, virements)

| Méthode | URL | Description | Exemple de corps de requête valide |
|---------|-----|-------------|------------------------------------|
| GET | /operations/depots | Récupère tous les dépots effectués |   |
| GET | /operations/depots/{txnid} | Récupère un dépot effectué grâce à son identifiant de transaction|   |
| GET | /operations/retraits |  Récupère tous les retraits effectués ||
| GET | /operations/retraits/{txnid} | Récupère un retrait effectué grâce à son identifiant de transaction|   |
| GET | /operations/retraits |Récupère tous les virements effectués ||
| GET | /operations/virements/{txnid} | Récupère un virement effectué grâce à son identifiant de transaction|   |


## Exemples de corps de requêtes JSON valides

##### <a id="creerclient">Créer un client -> http://localhost:9000/clients</a>
```json
{
    "nom":"LIONEL",
    "prenom":"Messi",
    "dateNaissance":"2003-11-18",
    "sexe":"M",
    "adresse":"Buenos Aire",
    "numTel":"99265216",
    "courriel":"olm@gmail.com",
    "nationalite":"Argentin"
}
```

##### <a id="modifierclient">Modifier un client -> http://localhost:9000/clients/1</a>
```json
{
    "nom":"MIRAI",
    "prenom":"Xavier",
    "dateNaissance":"2003-11-18",
    "sexe":"M",
    "adresse":"Lome",
    "courriel":"tx@gmail.com",
    "numTel":"90854562",
    "nationalite":"Togolese"
}
```

##### <a id="creercompte">Créer un compte -> http://localhost:9000/comptes</a>
```json
{
    "typeCompte":"EPARGNE",
    "proprietaire":1,
}
```
Remarque: Impossible de créer un compte avec un solde, le solde par défaut est égale à 0

##### <a id="retrait">Faire un retrait -> PUT http://localhost:9000/comptes/retrait</a>
```json
{
    "numCompte":"E64422023",
    "montant":"100"
}
```

##### <a id="depot">Faire un depot -> PUT http://localhost:9000/comptes/depot</a>
```json
{
    "numCompte":"4OI6X2023",
    "montant":"100"
}
```

##### <a id="virement">Faire un virement -> PUT http://localhost:9000/comptes/virement</a>
```json
{
    "numeroCompteSource":"NZ9HR2023",
    "numeroCompteDest":"QX6Z32023",
    "montant":"1000"
}
```
