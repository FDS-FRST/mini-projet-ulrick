import java.util.Scanner;

public class Joueur
{
    private String nom;
    private Personnage[] equipe;

    // Constructeur
    public Joueur(String nom)
    {
        this.nom = nom;
        this.equipe = new Personnage[3];
    }

    // Créer l'équipe du joueur
    public void creerEquipe(Scanner scanner)
    {
        System.out.println("\n" + this.nom + ", crée ton équipe :");

        for (int i = 0; i < 3; i++)
        {
            System.out.print("Nom du personnage " + (i + 1) + " : ");
            String nomPerso = scanner.nextLine();
            this.equipe[i] = new Personnage(nomPerso, 100, 20);
        }
    }

    // Choisir un personnage vivant
    public Personnage choisirPersonnageVivant(Scanner scanner, String action)
    {
        System.out.println("\n" + this.nom + ", " + action);

        // Afficher les personnages disponibles avec leurs numéros
        for (int i = 0; i < 3; i++)
        {
            if (equipe[i].estVivant())
            {
                System.out.println((i + 1) + ". " + equipe[i].getNom() +
                        " - " + equipe[i].getVie() + " HP");
            }
        }

        // Demander le choix
        int choix;
        do
        {
            System.out.print("Choisis un personnage (1-3) : ");
            while (!scanner.hasNextInt())
            {
                System.out.print("Veuillez entrer un nombre (1-3) : ");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Vider le buffer

            if (choix < 1 || choix > 3 || !equipe[choix - 1].estVivant())
            {
                System.out.println("Choix invalide. Choisis un personnage vivant.");
            }
        }
        while (choix < 1 || choix > 3 || !equipe[choix - 1].estVivant());

        return equipe[choix - 1];
    }

    // Vérifier s'il reste des personnages vivants
    public boolean aEncoreDesPersonnagesVivants()
    {
        for (Personnage p : equipe)
        {
            if (p.estVivant())
            {
                return true;
            }
        }
        return false;
    }

    // Afficher l'équipe
    public void afficherEquipe()
    {
        System.out.println("\n=== Équipe de " + this.nom + " ===");
        for (Personnage p : equipe)
        {
            p.afficherEtat();
        }
    }

    // Getter pour l'équipe
    public Personnage[] getEquipe()
    {
        return equipe;
    }

    // Getter pour le nom
    public String getNom()
    {
        return nom;
    }

    // Choisir une cible chez l'adversaire
    public Personnage choisirCible(Scanner scanner, Joueur adversaire, String attaquant)
    {
        System.out.println("\n" + this.nom + ", choisis la cible pour " + attaquant);
        adversaire.afficherEquipe();

        int choix;
        do
        {
            System.out.print("Choisis une cible (1-3), le numéro de la cible correspond à sa position dans son équipe : ");
            while (!scanner.hasNextInt())
            {
                System.out.print("Veuillez entrer un nombre (1-3) : ");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Vider le buffer

            if (choix < 1 || choix > 3 || !adversaire.getEquipe()[choix - 1].estVivant()) {
                System.out.println("Choix invalide. Choisis un personnage vivant.");
            }
        } while (choix < 1 || choix > 3 || !adversaire.getEquipe()[choix - 1].estVivant());

        return adversaire.getEquipe()[choix - 1];
    }
}