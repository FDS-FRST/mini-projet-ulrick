import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Joueur {
    private String nom;
    private ArrayList<Personnage> equipe;
    private static final int TAILLE_EQUIPE = 3;

    public Joueur(String nom) {
        this.nom = nom;
        this.equipe = new ArrayList<>();
    }

    public void creerEquipe(Scanner scanner, double multiplicateurStats) {
        System.out.println("---------------------------------------------");
        System.out.println(nom + ", cree ton equipe de " + TAILLE_EQUIPE + " personnages :");
        System.out.println("---------------------------------------------");

        for (int i = 0; i < TAILLE_EQUIPE; i++) {
            System.out.print("Nom du personnage " + (i + 1) + " : ");
            String nomPersonnage = scanner.nextLine();

            TypePersonnage typeChoisi = null;

            while (typeChoisi == null) {
                System.out.println("  Choix du type pour " + nomPersonnage + ":");
                System.out.println("  [1] Guerrier (HP 100 max, Attaque moderee)");
                System.out.println("  [2] Mage (HP 80 max, Gwo Attaque)");
                System.out.print("  Entrez le numero du type : ");

                try {
                    int typeChoix = scanner.nextInt();
                    scanner.nextLine();

                    if (typeChoix == 1) {
                        typeChoisi = TypePersonnage.GUERRIER;
                    } else if (typeChoix == 2) {
                        typeChoisi = TypePersonnage.MAGE;
                    } else {
                        System.out.println("Choix de type invalide. Veuillez choisir 1 ou 2.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entree invalide. Veuillez entrer un numero.");
                    scanner.nextLine();
                }
            }
            equipe.add(new Personnage(nomPersonnage, typeChoisi, multiplicateurStats));
        }
    }

    public Personnage choisirPersonnage(Scanner scanner, String role, boolean mustBeVivant) {

        while (true) {
            System.out.println("\n*********************************************");
            System.out.println(nom + ", choisis ton " + role + " :");
            System.out.println("*********************************************");

            afficherEquipePourChoix(this.equipe);

            try {
                System.out.print("Entre le numero (1-" + this.equipe.size() + ") : ");
                int choix = scanner.nextInt();
                scanner.nextLine();

                if (choix >= 1 && choix <= this.equipe.size()) {
                    Personnage p = equipe.get(choix - 1);
                    if (!mustBeVivant || p.estVivant()) {
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

    public Personnage choisirPersonnageVivant(Scanner scanner) {
        return choisirPersonnage(scanner, "personnage attaquant", true);
    }

    public Personnage choisirCible(Joueur adversaire, Scanner scanner) {

        while (true) {
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

    public boolean aEncoreDesPersonnagesVivants() {
        for (Personnage p : equipe) {
            if (p.estVivant()) {
                return true;
            }
        }
        return false;
    }

    public void afficherEquipe() {
        System.out.println("\n=== Etat de l'equipe de " + nom + " ===");
        for (Personnage p : equipe) {
            p.afficherEtat();
        }
        System.out.println("===============================");
    }

    private void afficherEquipePourChoix(ArrayList<Personnage> liste) {
        for (int i = 0; i < liste.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            liste.get(i).afficherEtat();
        }
    }

    public String getNom() {
        return nom;
    }
}