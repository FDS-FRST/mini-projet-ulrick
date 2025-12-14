public class Personnage {
    private String nom;
    private int vie = 100;
    private int attaque = 25;

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

        System.out.println("------------------------------------");
        System.out.println(nom + " attaque " + cible.nom + " !");

        cible.vie -= this.attaque;

        if (cible.vie < 0) {
            cible.vie = 0;
        }

        System.out.println("→ " + cible.nom + " perd " + attaque + " PV");

        if (!cible.estVivant()) {
            System.out.println("💥 " + cible.nom + " est KO!");
        }
        System.out.println("------------------------------------");
    }


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