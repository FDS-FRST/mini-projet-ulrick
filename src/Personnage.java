public class Personnage {
    private String nom;
    private int vie = 100;
    private int attaque = 25; // Atak de baz 25

    public Personnage(String nom) {
        this.nom = nom;
    }

    public boolean estVivant(){
        return vie > 0;
    }

    public void attaquer(Personnage cible) {
        if (!estVivant()) {
            System.out.println(nom + " est mort et ne peut pas attaquer.");
            return;
        }

        System.out.println(nom + " attaque " + cible.nom + " !");
        cible.vie -= this.attaque;

        if (cible.vie < 0) {
            cible.vie = 0;
        }

        System.out.println("→ " + cible.nom + " perd " + attaque + " PV");

        // --- AJOUT 1: Anonse lè sib la KO ---
        if (!cible.estVivant()) {
            System.out.println("💥 " + cible.nom + " est KO!");
        }
    }


    // --- AJOUT 2: Modifikasyon afficherEtat() pou jere KO ---
    public void afficherEtat(){
        System.out.print(nom + " - ");
        if (estVivant()) {
            System.out.println(vie + " HP");
        } else {
            System.out.println("KO");
        }
    }

    public String getNom() {
        return nom;
    }
}