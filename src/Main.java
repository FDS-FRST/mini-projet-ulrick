import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("   BIENVENUE DANS LE JEU DE COMBAT   ");
        System.out.println("======================================");

        // Phase 1 : Création des joueurs
        System.out.print("\nNom du Joueur 1 : ");
        String nomJoueur1 = scanner.nextLine();
        Joueur joueur1 = new Joueur(nomJoueur1);
        joueur1.creerEquipe(scanner);

        System.out.print("\nNom du Joueur 2 : ");
        String nomJ2 = scanner.nextLine();
        Joueur joueur2 = new Joueur(nomJ2);
        joueur2.creerEquipe(scanner);

        // Phase 2 : Boucle de combat
        int tour = 1;

        while (joueur1.aEncoreDesPersonnagesVivants() &&
                joueur2.aEncoreDesPersonnagesVivants())
        {
            System.out.println("\n\n--- TOUR " + tour + " ---");

            // Tour du joueur 1
            System.out.println("\n>>> C'est au tour de " + joueur1.getNom() + " <<<");

            if (joueur1.aEncoreDesPersonnagesVivants())
            {
                Personnage attaquant1 = joueur1.choisirPersonnageVivant(scanner,
                        "choisis ton personnage attaquant :");
                Personnage cible1 = joueur1.choisirCible(scanner, joueur2,
                        attaquant1.getNom());
                attaquant1.attaquer(cible1);
                joueur2.afficherEquipe();
            }

            // Vérifier si le jeu continue
            if (!joueur2.aEncoreDesPersonnagesVivants())
            {
                break;
            }

            // Tour du joueur 2
            System.out.println("\n>>> C'est au tour de " + joueur2.getNom() + " <<<");

            if (joueur2.aEncoreDesPersonnagesVivants())
            {
                Personnage attaquant2 = joueur2.choisirPersonnageVivant(scanner,
                        "choisis ton personnage attaquant :");
                Personnage cible2 = joueur2.choisirCible(scanner, joueur1,
                        attaquant2.getNom());
                attaquant2.attaquer(cible2);
                joueur1.afficherEquipe();
            }

            tour++;

            // Pause pour laisser le temps de lire
            if (joueur1.aEncoreDesPersonnagesVivants() &&
                    joueur2.aEncoreDesPersonnagesVivants())
            {
                System.out.println("\nAppuyez sur Enter pour continuer...");
                scanner.nextLine();
            }
        }

        // Fin du jeu
        System.out.println("\n\n======================================");
        System.out.println("           FIN DU COMBAT             ");
        System.out.println("======================================");

        if (joueur1.aEncoreDesPersonnagesVivants())
        {
            System.out.println("\n VICTOIRE DE " + joueur1.getNom().toUpperCase() + " !");
            System.out.println("Tous les personnages de " + joueur2.getNom() + " sont hors combat !");
        }
        else
        {
            System.out.println("\n VICTOIRE DE " + joueur2.getNom().toUpperCase() + " !");
            System.out.println("Tous les personnages de " + joueur1.getNom() + " sont hors combat !");
        }

        System.out.println("\nRécapitulatif final :");
        joueur1.afficherEquipe();
        joueur2.afficherEquipe();

        scanner.close();
    }
}