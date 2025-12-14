import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Joueur {
    private String nom;
    private ArrayList<Personnage> equipe;
    private static final int TAILLE_EQUIPE = 3;

    // Constructeur
    public Joueur(String nom) {
        this.nom = nom;
        this.equipe = new ArrayList<>();
    }

    // Méthode pour créer l'équipe
    public void creerEquipe(Scanner scanner) {
        // Ajout d'une bordure ici pour la création d'équipe
        System.out.println("---------------------------------------------");
        System.out.println(nom + ", cree ton equipe de " + TAILLE_EQUIPE + " personnages :");
        System.out.println("---------------------------------------------");

        for (int i = 0; i < TAILLE_EQUIPE; i++) {
            System.out.print("Nom du personnage " + (i + 1) + " : ");
            String nomPersonnage = scanner.nextLine();
            equipe.add(new Personnage(nomPersonnage));
        }
    }

    // Méthode pour choisir un attaquant (personnage vivant)
    public Personnage choisirPersonnageVivant(Scanner scanner) {

        while (true) {
            // NOUVELLE BORDURE pour le choix de l'attaquant
            System.out.println("\n*********************************************");
            System.out.println(nom + ", choisis ton personnage attaquant :");
            System.out.println("*********************************************");

            afficherEquipePourChoix(this.equipe);

            try {
                System.out.print("Entre le numero (1-" + this.equipe.size() + ") : ");
                int choix = scanner.nextInt();
                scanner.nextLine();

                if (choix >= 1 && choix <= this.equipe.size()) {
                    Personnage p = equipe.get(choix - 1);
                    if (p.estVivant()) {
                        return p;
                    } else {
                        System.out.println("🚫 Ce personnage est KO. Choisis-en un autre.");
                    }
                } else {
                    System.out.println("Choix invalide!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entree invalide. S'il te plait, entre un numero.");
                scanner.nextLine();
            }
        }
    }

    // Méthode pour choisir une cible chez l'adversaire
    public Personnage choisirCible(Joueur adversaire, Scanner scanner) {

        while (true) {
            // NOUVELLE BORDURE pour le choix de la cible
            System.out.println("\n*********************************************");
            System.out.println(nom + ", choisis la cible chez " + adversaire.getNom() + " :");
            System.out.println("*********************************************");

            adversaire.afficherEquipePourChoix(adversaire.equipe);

            try {
                System.out.print("Entre le numero (1-" + adversaire.equipe.size() + ") : ");
                int choix = scanner.nextInt();
                scanner.nextLine();

                if (choix >= 1 && choix <= adversaire.equipe.size()) {
                    Personnage cible = adversaire.equipe.get(choix - 1);
                    if (cible.estVivant()) {
                        return cible;
                    } else {
                        System.out.println("🚫 Cette cible est KO. Choisis-en une autre.");
                    }
                } else {
                    System.out.println("Choix invalide!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entree invalide. S'il te plait, entre un numero.");
                scanner.nextLine();
            }
        }
    }

    // Vérifie s'il reste au moins un personnage vivant
    public boolean aEncoreDesPersonnagesVivants() {
        for (Personnage p : equipe) {
            if (p.estVivant()) {
                return true;
            }
        }
        return false;
    }

    // Affiche l'équipe (pour info, avec KO ou HP)
    public void afficherEquipe() {
        System.out.println("\n=== Etat de l'equipe de " + nom + " ===");
        for (Personnage p : equipe) {
            p.afficherEtat();
        }
        System.out.println("===============================");
    }

    // Affiche l'équipe avec un numero devant pour le choix
    private void afficherEquipePourChoix(ArrayList<Personnage> liste) {
        for (int i = 0; i < liste.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            liste.get(i).afficherEtat();
        }
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Personnage> getEquipe() {
        return equipe;
    }
}