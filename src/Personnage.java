import java.lang.Math;

public class Personnage {
    private String nom;
    private int vie;
    private int attaque;

    private TypePersonnage type;
    private int niveau = 1;
    private int experience = 0;
    private int MAX_VIE;

    // VALÈ REFERANS KI PLAFONNEN
    private static final int BASE_MAX_VIE = 100; // Guerrier max HP
    private static final int BASE_ATTAQUE = 25;
    private static final int XP_POUR_LEVEL_UP = 50;
    private static final int BONUS_ATTAQUE_PAR_LEVEL = 5;
    private static final double TAUX_CRITIQUE = 0.20;

    public Personnage(String nom, TypePersonnage type, double multiplicateurStats) {
        this.nom = nom;
        this.type = type;

        int modVie = BASE_MAX_VIE;
        int modAttaque = BASE_ATTAQUE;

        // 1. Defini Stats de baz yo (nan limit 100 HP)
        if (type == TypePersonnage.GUERRIER) {
            // HP: 100 (plafond)
            modAttaque -= 5; // 20 ATK
        } else if (type == TypePersonnage.MAGE) {
            modVie -= 20; // 80 HP
            modAttaque += 5; // 30 ATK
        }

        // 2. APLIKE MULTIPLICATEUR SÈLMAN SOU ATTAQUE.
        // HP a inisializé ak valè de baz yo (pa depase 100)
        this.MAX_VIE = modVie;

        // Attaque a ajiste selon nivo difikilte a
        this.attaque = (int) (modAttaque * multiplicateurStats);

        this.vie = this.MAX_VIE;
    }

    // Le reste des methodes (attaquer, soigner, monterDeNiveau, etc.) reste inchangé.

    public boolean estVivant(){
        return vie > 0;
    }

    public void attaquer(Personnage cible) {
        if (!estVivant()) {
            System.out.println(nom + " est mort et ne peut pas attaquer.");
            return;
        }

        int degats = this.attaque;
        boolean isCritique = Math.random() < TAUX_CRITIQUE;

        if (isCritique) {
            degats *= 2;
            System.out.print("💥 COUP CRITIQUE! ");
        }

        System.out.println("------------------------------------");
        System.out.println(nom + " attaque " + cible.nom + " !");

        cible.vie -= degats;

        if (cible.vie < 0) {
            cible.vie = 0;
        }

        System.out.println("-> " + cible.nom + " perd " + degats + " PV.");

        if (!cible.estVivant()) {
            System.out.println("💥 " + cible.nom + " est KO!");
        }
        System.out.println("------------------------------------");

        gagnerExperience(10);
    }

    public void soigner(int soin) {
        if (!estVivant()) {
            System.out.println(nom + " est KO et ne peut pas etre soigne!");
            return;
        }

        int vieAvant = this.vie;
        this.vie += soin;

        if (this.vie > MAX_VIE) {
            this.vie = MAX_VIE;
        }
        int soinEffectue = this.vie - vieAvant;

        System.out.println("💚 " + this.nom + " recoit " + soinEffectue + " points de soin.");
    }

    protected void gagnerExperience(int xpGagnee) {
        this.experience += xpGagnee;

        while (this.experience >= XP_POUR_LEVEL_UP) {
            monterDeNiveau();
        }
    }

    protected void monterDeNiveau() {
        this.niveau++;
        this.experience -= XP_POUR_LEVEL_UP;
        this.attaque += BONUS_ATTAQUE_PAR_LEVEL;

        System.out.println("🎉 " + this.nom + " monte au Niveau " + this.niveau + " !");
        System.out.println("-> Attaque augmente a " + this.attaque + ".");

        this.vie = MAX_VIE;
    }

    public void afficherEtat(){
        String typeLabel = "[" + this.type.toString() + "] ";

        System.out.print(typeLabel + nom + " - ");
        if (estVivant()) {
            System.out.println(vie + "/" + MAX_VIE + " HP (Niv. " + niveau + " / Atk: " + attaque + " / XP: " + experience + "/" + XP_POUR_LEVEL_UP + ")");
        } else {
            System.out.println("KO");
        }
    }

    public String getNom() {
        return nom;
    }
}