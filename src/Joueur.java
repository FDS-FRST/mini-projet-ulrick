import java.util.Scanner;

public class Joueur {
    private String nom;
    private Personnage[] equipe;

    // Constructeur
    public Joueur(String nom) {
        this.nom = nom;
        this.equipe = new Personnage[3];
    }

    // Méthode pour créer l'équipe
    public void creerEquipe(Scanner scanner) {
        System.out.println("\n" + nom + ", crée ton équipe :");

        for (int i = 0; i < 3; i++) {
            System.out.print("Nom du personnage " + (i + 1) + " : ");
            String nomPersonnage = scanner.nextLine();

            // Création du personnage
            equipe[i] = new Personnage(nomPersonnage);
        }
    }

    // Choisir un personnage vivant pour attaquer
    public Personnage choisirPersonnageVivant(Scanner scanner) {
        System.out.println("\n" + nom + ", choisis ton personnage attaquant :");

        // Afficher seulement les personnages vivants
        int compteur = 1;
        int[] indicesVivants = new int[3];

        for (int i = 0; i < equipe.length; i++) {
            if (equipe[i].estVivant()) {
                System.out.print(compteur + ". ");
                equipe[i].afficherEtat(); // Utiliser afficherEtat() au lieu de getVie()
                indicesVivants[compteur - 1] = i;
                compteur++;
            }
        }

        if (compteur == 1) {
            System.out.println("Aucun personnage vivant !");
            return null;
        }

        int choix;
        do {
            System.out.print("Entrez le numéro (1-" + (compteur - 1) + ") : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide !");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            if (choix < 1 || choix >= compteur) {
                System.out.println("Choix invalide !");
            }
        } while (choix < 1 || choix >= compteur);

        return equipe[indicesVivants[choix - 1]];
    }

    // Choisir une cible parmi l'adversaire
    public Personnage choisirCible(Joueur adversaire, Scanner scanner) {
        System.out.println("\n" + nom + ", choisis la cible chez " + adversaire.getNom() + " :");

        Personnage[] equipeAdversaire = adversaire.getEquipe();
        int compteur = 1;
        int[] indicesVivants = new int[3];

        for (int i = 0; i < equipeAdversaire.length; i++) {
            if (equipeAdversaire[i].estVivant()) {
                System.out.print(compteur + ". ");
                equipeAdversaire[i].afficherEtat(); // Utiliser afficherEtat() au lieu de getVie()
                indicesVivants[compteur - 1] = i;
                compteur++;
            }
        }

        int choix;
        do {
            System.out.print("Entrez le numéro (1-" + (compteur - 1) + ") : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide !");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            if (choix < 1 || choix >= compteur) {
                System.out.println("Choix invalide !");
            }
        } while (choix < 1 || choix >= compteur);

        return equipeAdversaire[indicesVivants[choix - 1]];
    }

    // Vérifier si le joueur a encore des personnages vivants
    public boolean aEncoreDesPersonnagesVivants() {
        for (Personnage p : equipe) {
            if (p.estVivant()) {
                return true;
            }
        }
        return false;
    }

    // Afficher l'équipe
    public void afficherEquipe() {
        System.out.println("\n=== Équipe de " + nom + " ===");
        for (Personnage p : equipe) {
            p.afficherEtat();
        }
    }

    // Afficher l'équipe pour le choix de cible
    public void afficherEquipePourCible() {
        System.out.println("État de l'équipe de " + nom + " :");
        for (Personnage p : equipe) {
            p.afficherEtat();
        }
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public Personnage[] getEquipe() {
        return equipe;
    }
}