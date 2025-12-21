import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        afficherTitre();

        // Phase 1 : Création des joueurs
        Joueur joueur1 = creerJoueur(scanner, 1);
        Joueur joueur2 = creerJoueur(scanner, 2);

        // Phase 2 : Boucle de combat
        int tour = 1;
        boolean jeuEnCours = true;

        while (jeuEnCours)
        {
            afficherSeparateurTour(tour);

            // Tour du joueur 1
            if (joueur1.aEncoreDesPersonnagesVivants())
            {
                joueur1.jouerTour(scanner, joueur2);
                if (!joueur2.aEncoreDesPersonnagesVivants())
                {
                    finDuJeu(joueur1, joueur2);
                    jeuEnCours = false;
                    continue;
                }
            }

            // Tour du joueur 2
            if (joueur2.aEncoreDesPersonnagesVivants())
            {
                joueur2.jouerTour(scanner, joueur1);
                if (!joueur1.aEncoreDesPersonnagesVivants())
                {
                    finDuJeu(joueur2, joueur1);
                    jeuEnCours = false;
                    continue;
                }
            }

            // Affichage des statistiques intermédiaires
            afficherStatistiques(joueur1, joueur2);

            // Pause entre les tours
            if (jeuEnCours)
            {
                System.out.println("\nAppuyez sur la touche Enter pour continuer...");
                scanner.nextLine();
            }
            tour++;
        }
        scanner.close();
    }

    // Méthodes auxiliaires
    private static void afficherTitre()
    {
        System.out.println("================================================");
        System.out.println("     JEU DE COMBAT AVANCÉ - EXTENSIONS FACULTATIVES     ");
        System.out.println("================================================");
        System.out.println("Extensions facultatives activées :");
        System.out.println("  - Système de types (Guerrier/Mage/Archer/etc.)");
        System.out.println("  - Coups critiques (20% de chance)");
        System.out.println("  - Soins et soins de zone");
        System.out.println("  - Système de niveaux et d'expérience");
        System.out.println("  - Équilibrage selon les types");
        System.out.println("================================================\n");
    }

    private static Joueur creerJoueur(Scanner scanner, int numero)
    {
        System.out.print("Nom du Joueur " + numero + " : ");
        String nom = scanner.nextLine();
        Joueur joueur = new Joueur(nom);
        joueur.creerEquipe(scanner);
        return joueur;
    }

    private static void afficherSeparateurTour(int tour)
    {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("                    TOUR " + tour + "                    ");
        System.out.println("═".repeat(50));
    }

    private static void afficherStatistiques(Joueur j1, Joueur j2)
    {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("STATISTIQUES APRÈS LE TOUR :");
        System.out.println("─".repeat(50));
        j1.afficherEquipe();
        j2.afficherEquipe();
    }

    private static void finDuJeu(Joueur vainqueur, Joueur perdant)
    {
        System.out.println("\n" + "⭐".repeat(50));
        System.out.println("              FIN DU COMBAT - VICTOIRE !             ");
        System.out.println("⭐".repeat(50));

        System.out.println("\nFÉLICITATIONS " + vainqueur.getNom().toUpperCase() + " ! ");
        System.out.println("Tu as vaincu " + perdant.getNom() + " !");

        // Statistiques finales
        System.out.println("\nSTATISTIQUES FINALES :");
        System.out.println("=".repeat(40));
        System.out.println("Équipe victorieuse (" + vainqueur.getNom() + ") :");
        vainqueur.afficherEquipe();

        System.out.println("\nÉquipe vaincue (" + perdant.getNom() + ") :");
        perdant.afficherEquipe();

        // Personnage homme du combat (plus haut niveau)
        Personnage hommeDuCombat = trouverHommeDuCombat(vainqueur);
        if (hommeDuCombat != null)
        {
            System.out.println("\nHOMME DU COMBAT : " + hommeDuCombat.getNom());
            System.out.println("   Niveau " + hommeDuCombat.getNiveau() + " | " + hommeDuCombat.getType());
        }
    }

    private static Personnage trouverHommeDuCombat(Joueur joueur)
    {
        Personnage hommeDuCombat = null;
        for (Personnage p : joueur.getEquipe())
        {
            if (p.estVivant())
            {
                if (hommeDuCombat == null || p.getNiveau() > hommeDuCombat.getNiveau())
                {
                    hommeDuCombat = p;
                }
            }
        }
        return hommeDuCombat;
    }
}