public class Personnage
{
    private String nom;
    private int vie;
    private int attaque;

    // Constructeur
    public Personnage(String nom, int vie, int attaque)
    {
        this.nom = nom;
        this.vie = vie;
        this.attaque = attaque;
    }

    // Méthode pour attaquer un autre personnage
    public void attaquer(Personnage cible)
    {
        if (this.estVivant())
        {
            System.out.println(this.nom + " attaque " + cible.nom);
            cible.recevoirDegats(this.attaque);
        }
    }

    // Méthode pour recevoir des dégâts
    public void recevoirDegats(int degats)
    {
        this.vie -= degats;
        if (this.vie < 0)
        {
            this.vie = 0;
        }
        System.out.println(this.nom + " perd " + degats + " points de vie");
    }

    // Vérifier si le personnage est vivant
    public boolean estVivant()
    {
        return this.vie > 0;
    }

    // Afficher l'état du personnage
    public void afficherEtat()
    {
        String etat = this.estVivant() ? "VIVANT" : "MORT";
        System.out.println(this.nom + " - " + this.vie + " HP (" + etat + ")");
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

    public int getAttaque()
    {
        return attaque;
    }
}