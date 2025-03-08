# Système de Réservation de Vols

## Description
Ce projet est un système de gestion des réservations de vols en Java. Il permet d'ajouter des vols, de réserver des sièges, d'annuler des réservations et d'afficher les informations des vols.

## Fonctionnalités
- Ajouter un vol (code, destination, date, nombre de sièges).
- Afficher la liste des vols disponibles.
- Réserver un siège sur un vol.
- Annuler une réservation.
- Sauvegarde des données dans un fichier texte ou binaire.
- Gestion des utilisateurs avec authentification (Admin / Passager).

## Architecture du Projet
```
flight-reservation-system/
│── src/
│   ├── models/             # Classes représentant les entités (Vol, Réservation, Utilisateur, etc.)
│   │   ├── Vol.java        # Représente un vol
│   │   ├── Reservation.java # Gère une réservation
│   │   ├── Utilisateur.java # Classe parent pour les utilisateurs
│   │   ├── Admin.java       # Classe pour l'administrateur
│   │   ├── Passager.java    # Classe pour les passagers
│   ├── services/           # Logique métier (ajout, réservation, annulation, gestion utilisateur, etc.)
│   │   ├── VolService.java  # Gestion des vols
│   │   ├── ReservationService.java # Gestion des réservations
│   │   ├── UtilisateurService.java # Gestion des utilisateurs et authentification
│   ├── utils/              # Gestion des fichiers et validation
│   │   ├── FileManager.java # Sauvegarde et chargement des données
│   │   ├── Validator.java   # Vérification des données
│   ├── main/               # Classe principale
│   │   ├── Main.java        # Point d'entrée de l'application
│   ├── console/            # Interface en mode console
│   │   ├── ConsoleUI.java   # Interface utilisateur textuelle
│   ├── gui/                # Interface graphique (Swing, JavaFX)
│   │   ├── LoginUI.java     # Interface de connexion
│   │   ├── AdminUI.java     # Interface admin
│   │   ├── PassagerUI.java  # Interface passager
│── data/                   # Stockage des données (fichiers texte ou binaires)
│── README.md
│── .gitignore
│── pom.xml (si utilisation de Maven)
```

## Installation et Exécution
### Prérequis
- Java 8 ou supérieur
- Un IDE comme IntelliJ IDEA, Eclipse ou VS Code

### Exécution en mode console
1. Compiler le projet :
   ```sh
   javac -d bin src/**/*.java
   ```
2. Exécuter l'application :
   ```sh
   java -cp bin console.ConsoleUI
   ```

## Améliorations Prévues
- Ajout d'une interface graphique avec JavaFX ou Swing.
- Intégration d'une base de données (SQLite, MySQL).
- Recherche avancée de vols par critères (date, destination).

## Auteur
**[Ton Nom]**

