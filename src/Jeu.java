import java.util.Scanner;
import java.util.InputMismatchException;

public class Jeu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚔️ Bienvenue dans le Jeu de Combat ! ⚔️");

        boolean rejouer;

        do {

            System.out.println("\n===== NOUVELLE PARTIE =====");

            NivoDifikilte niveauChoisi = choisirNiveau(scanner);
            double multiplicateur = niveauChoisi.getMultiplicateurStats();
            System.out.println("Nivo selectionne : " + niveauChoisi + " (Multiplicateur Stats: x" + multiplicateur + ")");
            System.out.println("---------------------------------------------");

            System.out.print("\nJoueur 1, entre ton nom : ");
            String nom1 = scanner.nextLine();
            Joueur joueur1 = new Joueur(nom1);
            joueur1.creerEquipe(scanner, 1.0);

            System.out.print("\nJoueur 2, entre ton nom : ");
            String nom2 = scanner.nextLine();
            Joueur joueur2 = new Joueur(nom2);
            joueur2.creerEquipe(scanner, multiplicateur);

            System.out.println("\n=============================================");
            System.out.println("--- Equipes pretes. Le combat commence! ---");
            System.out.println("=============================================\n");

            int tour = 1;
            while (joueur1.aEncoreDesPersonnagesVivants() && joueur2.aEncoreDesPersonnagesVivants()) {

                System.out.println("\n***************** TOUR " + tour + " *****************");

                if (joueur1.aEncoreDesPersonnagesVivants()) {
                    System.out.println("\n--- Tour de " + joueur1.getNom() + " ---");
                    effectuerTour(joueur1, joueur2, scanner);
                }

                if (!joueur2.aEncoreDesPersonnagesVivants()) {
                    break;
                }

                if (joueur2.aEncoreDesPersonnagesVivants()) {
                    System.out.println("\n--- Tour de " + joueur2.getNom() + " ---");
                    effectuerTour(joueur2, joueur1, scanner);
                }

                tour++;
            }

            declarerVainqueur(joueur1, joueur2);
            rejouer = demanderSiRejouer(scanner);

        } while (rejouer);

        System.out.println("\n👋 Merci d'avoir joue! Au revoir.");
        scanner.close();
    }

    private static NivoDifikilte choisirNiveau(Scanner scanner) {
        while (true) {
            System.out.println("\n--- CHOIX DU NIVEAU DE DIFFICULTE ---");
            System.out.println("[1] Facile (Stats adversaire reduites: x0.8)");
            System.out.println("[2] Normal (Stats adversaire normales: x1.0)");
            System.out.println("[3] Difficile (Stats adversaire augmentees: x1.2)");
            System.out.print("Entrez le numero du niveau : ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        return NivoDifikilte.FASIL;
                    case 2:
                        return NivoDifikilte.NORMAL;
                    case 3:
                        return NivoDifikilte.DIFFICIL;
                    default:
                        System.out.println("Choix invalide. Veuillez choisir 1, 2 ou 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entree invalide. Veuillez entrer un numero.");
                scanner.nextLine();
            }
        }
    }

    private static void effectuerTour(Joueur attaquant, Joueur defenseur, Scanner scanner) {

        int action;
        do {
            System.out.println("\n--- Choix d'action pour " + attaquant.getNom() + " ---");
            System.out.println("[1] Attaquer un adversaire");
            System.out.println("[2] Soigner un personnage de ton equipe (Soins: +20 HP)");
            System.out.print("Choix Action : ");

            try {
                action = scanner.nextInt();
                scanner.nextLine();

                if (action == 1) {

                    Personnage attaquantChoisi = attaquant.choisirPersonnageVivant(scanner);
                    if (attaquantChoisi == null) return;

                    Personnage cibleChoisie = defenseur.choisirCible(defenseur, scanner);
                    if (cibleChoisie == null) return;

                    System.out.println("\n*** RESULTAT DE L'ATTAQUE ***");
                    attaquantChoisi.attaquer(cibleChoisie);
                    System.out.println("***************************");

                    defenseur.afficherEquipe();
                    break;

                } else if (action == 2) {

                    Personnage soigneur = attaquant.choisirPersonnage(scanner, "personnage a soigner", true);
                    if (soigneur == null) return;

                    final int POINTS_DE_SOIN = 20;

                    System.out.println("\n*** RESULTAT DU SOIN ***");
                    soigneur.soigner(POINTS_DE_SOIN);
                    System.out.println("***************************");

                    attaquant.afficherEquipe();
                    break;

                } else {
                    System.out.println("Choix invalide. Veuillez choisir 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entree invalide. Veuillez entrer un numero.");
                scanner.nextLine();
                action = 0;
            }
        } while (true);
    }

    private static boolean demanderSiRejouer(Scanner scanner) {
        while (true) {
            System.out.println("\n--- VOULEZ-VOUS REJOUER? ---");
            System.out.println("[1] Oui, recommencer une partie");
            System.out.println("[2] Non, fermer le programme");
            System.out.print("Votre choix : ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine();

                if (choix == 1) {
                    return true;
                } else if (choix == 2) {
                    return false;
                } else {
                    System.out.println("Choix invalide. Veuillez entrer 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entree invalide. Veuillez entrer un numero (1 ou 2).");
                scanner.nextLine();
            }
        }
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