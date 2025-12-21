import java.util.Scanner;

public class Joueur
{
    private String nom;
    private Personnage[] equipe;

    public Joueur(String nom)
    {
        this.nom = nom;
        this.equipe = new Personnage[3];
    }

    // Création d'équipe avec types
    public void creerEquipe(Scanner scanner)
    {
        System.out.println("\n" + this.nom + ", crée ton équipe :");

        String[] typesDisponibles = {"Guerrier", "Mage", "Archer", "Soigneur", "Tank"};

        for (int i = 0; i < 3; i++)
        {
            System.out.print("\nPersonnage " + (i + 1) + " - ");
            System.out.print("Nom : ");
            String nomPerso = scanner.nextLine();

            // Choix du type
            System.out.println("Types disponibles :");
            for (int j = 0; j < typesDisponibles.length; j++)
            {
                System.out.println("  " + (j + 1) + ". " + typesDisponibles[j]);
            }

            int choixType;
            do {
                System.out.print("Choisis un type (1-" + typesDisponibles.length + ") : ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Veuillez entrer un nombre : ");
                    scanner.next();
                }
                choixType = scanner.nextInt();
                scanner.nextLine();
            }
            while (choixType < 1 || choixType > typesDisponibles.length);

            String typeChoisi = typesDisponibles[choixType - 1];

            // Stats selon le type
            int vie, attaque;
            switch (typeChoisi)
            {
                case "Guerrier":
                    vie = 120; attaque = 25;
                    break;
                case "Mage":
                    vie = 80; attaque = 30;
                    break;
                case "Archer":
                    vie = 90; attaque = 22;
                    break;
                case "Soigneur":
                    vie = 100; attaque = 15;
                    break;
                case "Tank":
                    vie = 150; attaque = 18;
                    break;
                default:
                    vie = 100; attaque = 20;
            }

            this.equipe[i] = new Personnage(nomPerso, vie, attaque, typeChoisi);
            System.out.println(nomPerso + " le " + typeChoisi + " créé !");
        }
    }

    // Choix d'action : attaquer ou soigner
    public void jouerTour(Scanner scanner, Joueur adversaire) {
        System.out.println("\n>>> Tour de " + this.nom + " <<<");

        // Afficher l'équipe
        this.afficherEquipe();

        // Choix du personnage actif
        Personnage actif = choisirPersonnageVivant(scanner, "Choisis le personnage qui va agir :");

        // Choix de l'action
        System.out.println("\nActions disponibles pour " + actif.getNom() + " :");
        System.out.println("1. Attaquer un ennemi");
        System.out.println("2. Soigner un allié");
        if (actif.getType().equals("Mage") || actif.getType().equals("Soigneur")) {
            System.out.println("3. Soin de zone (soigne toute l'équipe légèrement)");
        }

        int choixAction;
        do {
            System.out.print("Choisis une action : ");
            while (!scanner.hasNextInt())
            {
                scanner.next();
            }
            choixAction = scanner.nextInt();
            scanner.nextLine();
        }
        while (choixAction < 1 || choixAction > 3);

        // Exécuter l'action
        switch (choixAction)
        {
            case 1: // Attaquer
                Personnage cible = choisirCible(scanner, adversaire, actif.getNom());
                actif.attaquer(cible);
                break;

            case 2: // Soigner un allié
                System.out.println("\nChoisis un allié à soigner :");
                for (int i = 0; i < 3; i++)
                {
                    if (equipe[i].estVivant())
                    {
                        System.out.println((i + 1) + ". " + equipe[i].getNom() +
                                " - " + equipe[i].getVie() + "/" + equipe[i].getVieMax() + " HP");
                    }
                }

                int choixSoin;
                do
                {
                    System.out.print("Choisis un allié (1-3) : ");
                    while (!scanner.hasNextInt()) {
                        scanner.next();
                    }
                    choixSoin = scanner.nextInt();
                    scanner.nextLine();
                }
                while (choixSoin < 1 || choixSoin > 3 || !equipe[choixSoin - 1].estVivant());

                int pointsSoin = 15 + (actif.getNiveau() * 2);
                equipe[choixSoin - 1].soigner(pointsSoin);
                break;

            case 3: // Soin de zone (pour mages et soigneurs)
                if (actif.getType().equals("Mage") || actif.getType().equals("Soigneur")) {
                    System.out.println("\n" + actif.getNom() + " lance un soin de zone !");
                    for (Personnage p : equipe)
                    {
                        if (p.estVivant())
                        {
                            p.soigner(10 + actif.getNiveau());
                        }
                    }
                }
                break;
        }

        // Soin automatique pour les mages
        if (actif.getType().equals("Mage") && actif.estVivant())
        {
            actif.soinAutomatique();
        }
    }

    // Les autres méthodes restent similaires mais adaptées
    public Personnage choisirPersonnageVivant(Scanner scanner, String message) {
        System.out.println("\n" + message);

        for (int i = 0; i < 3; i++)
        {
            if (equipe[i].estVivant())
            {
                System.out.print((i + 1) + ". ");
                equipe[i].afficherEtat();
            }
        }

        int choix;
        do
        {
            System.out.print("Choisis un personnage (1-3) : ");
            while (!scanner.hasNextInt())
            {
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine();
        }
        while (choix < 1 || choix > 3 || !equipe[choix - 1].estVivant());

        return equipe[choix - 1];
    }

    public Personnage choisirCible(Scanner scanner, Joueur adversaire, String attaquant) {
        System.out.println("\nChoisis une cible pour " + attaquant + " :");
        adversaire.afficherEquipe();

        int choix;
        do
        {
            System.out.print("Choisis une cible (1-3) : ");
            while (!scanner.hasNextInt())
            {
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine();
        }
        while (choix < 1 || choix > 3 || !adversaire.getEquipe()[choix - 1].estVivant());

        return adversaire.getEquipe()[choix - 1];
    }

    public boolean aEncoreDesPersonnagesVivants()
    {
        for (Personnage p : equipe)
        {
            if (p.estVivant()) return true;
        }
        return false;
    }

    public void afficherEquipe()
    {
        System.out.println("\n=== Équipe de " + this.nom + " ===");
        for (Personnage p : equipe)
        {
            p.afficherEtat();
        }
    }

    // Getters
    public String getNom()
    {
        return nom;
    }

    public Personnage[] getEquipe()
    {
        return equipe;
    }
}