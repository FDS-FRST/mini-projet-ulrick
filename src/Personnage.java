public class Personnage {
    private String nom;
    private int vie = 100;
    private int attaque = 25;

    public Personnage(String nom) {
        this.nom = nom;
    }
    public boolean estVivant(){
        return vie >0;
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
    }



    public void afficherEtat(){
        System.out.println(nom + " -nombre de vie restante  " +vie + " PH");
    }
    public String getNom() {
        return nom;
    }
}
