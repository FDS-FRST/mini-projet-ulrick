import java.util.Scanner;

public class Jeu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚔️ Bienvenue dans le Jeu de Combat ! ⚔️");

        System.out.print("\nJoueur 1, entre ton nom : ");
        String nom1 = scanner.nextLine();
        Joueur joueur1 = new Joueur(nom1);
        joueur1.creerEquipe(scanner);

        System.out.print("\nJoueur 2, entre ton nom : ");
        String nom2 = scanner.nextLine();
        Joueur joueur2 = new Joueur(nom2);
        joueur2.creerEquipe(scanner);

        System.out.println("\n=============================================");
        System.out.println("--- Équipes prêtes. Le combat commence! ---");
        System.out.println("=============================================\n");

        int tour = 1;
        while (joueur1.aEncoreDesPersonnagesVivants() && joueur2.aEncoreDesPersonnagesVivants()) {

            System.out.println("\n***************** TOUR " + tour + " *****************");

            if (joueur1.aEncoreDesPersonnagesVivants()) {
                System.out.println("\n--- Phase d'Attaque de " + joueur1.getNom() + " ---");
                effectuerAttaque(joueur1, joueur2, scanner);
            }

            if (!joueur2.aEncoreDesPersonnagesVivants()) {
                break;
            }

            if (joueur2.aEncoreDesPersonnagesVivants()) {
                System.out.println("\n--- Phase d'Attaque de " + joueur2.getNom() + " ---");
                effectuerAttaque(joueur2, joueur1, scanner);
            }

            tour++;
        }

        declarerVainqueur(joueur1, joueur2);
        scanner.close();
    }

    private static void effectuerAttaque(Joueur attaquant, Joueur defenseur, Scanner scanner) {

        Personnage attaquantChoisi = attaquant.choisirPersonnageVivant(scanner);

        if (attaquantChoisi == null) {
            return;
        }

        Personnage cibleChoisie = defenseur.choisirCible(defenseur, scanner);

        if (cibleChoisie == null) {
            return;
        }

        System.out.println("\n*** RÉSULTAT DE L'ACTION ***");
        attaquantChoisi.attaquer(cibleChoisie);
        System.out.println("***************************");

        defenseur.afficherEquipe();
    }

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