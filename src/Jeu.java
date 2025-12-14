import java.util.Scanner;

public class Jeu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚔️ Bienvenue dans le Jeu de Combat ! ⚔️");

        // --- PHASE 1 : Creation des joueurs et des equipes ---

        System.out.print("\nJoueur 1, entre ton nom : ");
        String nom1 = scanner.nextLine();
        Joueur joueur1 = new Joueur(nom1);
        joueur1.creerEquipe(scanner);

        System.out.print("\nJoueur 2, entre ton nom : ");
        String nom2 = scanner.nextLine();
        Joueur joueur2 = new Joueur(nom2);
        joueur2.creerEquipe(scanner);

        System.out.println("\n--- Equipes pretes. Le combat commence! ---\n");

        // --- PHASE 2 : Boucle de combat ---
        int tour = 1;
        while (joueur1.aEncoreDesPersonnagesVivants() && joueur2.aEncoreDesPersonnagesVivants()) {
            System.out.println("\n================ TOUR " + tour + " ================");

            // Tour Joueur 1
            if (joueur1.aEncoreDesPersonnagesVivants()) {
                System.out.println("\nC'est le tour de " + joueur1.getNom() + " :");
                effectuerAttaque(joueur1, joueur2, scanner);
            }

            if (!joueur2.aEncoreDesPersonnagesVivants()) {
                break;
            }

            // Tour Joueur 2
            if (joueur2.aEncoreDesPersonnagesVivants()) {
                System.out.println("\nC'est le tour de " + joueur2.getNom() + " :");
                effectuerAttaque(joueur2, joueur1, scanner);
            }

            tour++;
        }

        // --- PHASE 3 : Fin du jeu ---
        declarerVainqueur(joueur1, joueur2);
        scanner.close();
    }

    /**
     * Gere une attaque: choix attaquant, choix cible, et execution.
     */
    private static void effectuerAttaque(Joueur attaquant, Joueur defenseur, Scanner scanner) {
        // 1. Choisir l'attaquant (doit etre vivant)
        Personnage attaquantChoisi = attaquant.choisirPersonnageVivant(scanner);

        if (attaquantChoisi == null) {
            return;
        }

        // 2. Choisir la cible (doit etre vivante)
        Personnage cibleChoisie = defenseur.choisirCible(defenseur, scanner);

        if (cibleChoisie == null) {
            return;
        }

        // 3. Execution de l'attaque
        attaquantChoisi.attaquer(cibleChoisie);

        // 4. Affichage de l'etat de l'equipe adverse
        defenseur.afficherEquipe();
    }

    /**
     * Declare le vainqueur.
     */
    private static void declarerVainqueur(Joueur j1, Joueur j2) {
        System.out.println("\n**************** FIN DU JEU ****************");
        if (j1.aEncoreDesPersonnagesVivants()) {
            System.out.println("🏆 Victoire de " + j1.getNom() + " ! Tous les personnages de " + j2.getNom() + " sont elimines.");
        } else if (j2.aEncoreDesPersonnagesVivants()) {
            System.out.println("🏆 Victoire de " + j2.getNom() + " ! Tous les personnages de " + j1.getNom() + " sont elimines.");
        } else {
            System.out.println("Match nul !");
        }
    }
}