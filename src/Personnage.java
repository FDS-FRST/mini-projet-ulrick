import java.util.Random;

public class Personnage
{
    private String nom;
    private int vie;
    private int vieMax;
    private int attaque;
    private String type;
    private int niveau;
    private int experience;
    private static final Random random = new Random();

    // Constructeur avec type
    public Personnage(String nom, int vie, int attaque, String type)
    {
        this.nom = nom;
        this.vie = vie;
        this.vieMax = vie;
        this.attaque = attaque;
        this.type = type;
        this.niveau = 1;
        this.experience = 0;
    }

    // Constructeur simple (pour compatibilité)
    public Personnage(String nom, int vie, int attaque)
    {
        this(nom, vie, attaque, "Normal");
    }

    // Méthode d'attaque avec système de critique
    public void attaquer(Personnage cible)
    {
        if (!this.estVivant()) return;

        int degats = this.attaque;
        boolean critique = false;

        // 20% de chance de coup critique
        if (random.nextInt(100) < 20) {
            degats *= 2;
            critique = true;
        }

        // Bonus selon les types
        degats = calculerDegatsAvecBonus(degats, cible.type);

        System.out.print(this.nom + " attaque " + cible.nom);
        if (critique) {
            System.out.print(" - COUP CRITIQUE! ");
            degats += 5; // Bonus supplémentaire pour critique
        }
        System.out.println();

        cible.recevoirDegats(degats);

        // Gagner de l'expérience
        this.gagnerExperience(degats / 2);
    }

    // Calcul des dégâts avec bonus/malus selon les types
    private int calculerDegatsAvecBonus(int degats, String typeCible)
    {
        // Exemple de système de types simple
        switch (this.type) {
            case "Guerrier":
                if (typeCible.equals("Archer")) degats += 5;
                break;
            case "Mage":
                if (typeCible.equals("Guerrier")) degats += 5;
                break;
            case "Archer":
                if (typeCible.equals("Mage")) degats += 5;
                break;
        }
        return degats;
    }

    // Méthode pour soigner
    public void soigner(int points)
    {
        if (!this.estVivant())
        {
            System.out.println(this.nom + " ne peut pas être soigné car il est mort.");
            return;
        }

        this.vie += points;
        if (this.vie > this.vieMax)
        {
            this.vie = this.vieMax;
        }
        System.out.println(this.nom + " a été soigné de " + points + " points. Vie: " + this.vie + "/" + this.vieMax);
    }

    // Soin automatique (pour certains types)
    public void soinAutomatique()
    {
        if (this.type.equals("Mage") && this.estVivant()) {
            int soin = 10 + random.nextInt(10);
            soigner(soin);
        }
    }

    // Gagner de l'expérience
    private void gagnerExperience(int exp)
    {
        this.experience += exp;
        System.out.println(this.nom + " gagne " + exp + " points d'expérience. Total: " + this.experience);

        // Monter de niveau tous les 100 points d'expérience
        if (this.experience >= 100) {
            this.niveau++;
            this.experience -= 100;
            this.vieMax += 10;
            this.vie = this.vieMax; // Soin complet au niveau supérieur
            this.attaque += 2;
            System.out.println("✨ " + this.nom + " monte au niveau " + this.niveau + "!");
            System.out.println("   Vie max: " + this.vieMax + ", Attaque: " + this.attaque);
        }
    }

    public void recevoirDegats(int degats)
    {
        this.vie -= degats;
        if (this.vie < 0)
        {
            this.vie = 0;
        }
        System.out.println(this.nom + " perd " + degats + " points de vie. Vie restante: " + this.vie + "/" + this.vieMax);
    }

    public boolean estVivant()
    {
        return this.vie > 0;
    }

    public void afficherEtat()
    {
        String etat = this.estVivant() ? "VIVANT" : "MORT";
        String etoilesNiveau = "★".repeat(Math.min(this.niveau, 5));
        System.out.printf("%-15s %-20s Niv %d %s %3d/%3d HP (Exp: %3d)%n",
                this.nom,
                "[" + this.type + "]",
                this.niveau,
                etoilesNiveau,
                this.vie,
                this.vieMax,
                this.experience);
    }

    // Getters
    public String getNom()
    {
        return nom;
    }

    public int getVie()
    {
        return vie;
    }

    public int getVieMax()
    {
        return vieMax;
    }

    public int getAttaque()
    {
        return attaque;
    }

    public String getType()
    {
        return type;
    }

    public int getNiveau()
    {
        return niveau;
    }

    public int getExperience()
    {
        return experience;
    }

    // Setter pour le type (pourrait être utile)
    public void setType(String type)
    {
        this.type = type;
    }
}