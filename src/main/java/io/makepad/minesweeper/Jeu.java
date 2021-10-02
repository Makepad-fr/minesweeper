package io.makepad.minesweeper;

public class Jeu {
    private final Joueur joueur;
    private Plateau plateau;

    public Jeu(Joueur j, Plateau p){
        this.joueur = j;
        this.plateau = p;
    }

    private char demanderAction(){
        char c = this.joueur.demanderAction();
        if(c=='r' || c=='d'){
            return c;
        }
        return demanderAction();
    }

    private int[] demanderCoordonnees(){
        int[] tabCor = this.joueur.demanderCoordonnees();
        if(this.plateau.isCaseExists(tabCor[0],tabCor[1])){
            return tabCor;
        }
        return demanderCoordonnees();
    }

    private int demanderMines() {
        int nm = this.joueur.demanderNbMines();
        if (nm > this.plateau.nbCases()) {
            return demanderMines();
        }
        return nm;
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void jouer() {
        // Demande le nom au joueur
        this.joueur.demanderNom();
        // Demander les dimensions du plateau
        int[] dims = this.joueur.demanderDimensions();
        // Demander le nombre des mines
        int nbMines = this.demanderMines();
        // Réinitialise le plateau
        this.plateau = new Plateau(dims[0], dims[1], nbMines);
        boolean perdu = false;
        // Tant que le joueur n'a pas perdu et n'a pas gagné
        while (!plateau.jeuGagne() && !perdu) {
            this.clearScreen();
            //Affiche l'état courant du plateau
            this.plateau.afficheCourant();
            // Demander l'action au joueur
            char a = this.demanderAction();
            // Demander la case qu'il veut jouer
            int[] coords = this.demanderCoordonnees();
            // Jouer l'action à la case demandé
            if (a == 'r') {
                if (this.plateau.estMine(coords[0], coords[1])) {
                    perdu = true;
                    continue;
                }
                this.plateau.releverCase(coords[0], coords[1]);
                continue;
            }
            this.plateau.drapeauCase(coords[0], coords[1]);
        }
        if (perdu) {
            System.out.println("Vous avez perdu le jeu!");
        } else {
            System.out.println("Bravo! Vous avez gagné");
        }
        // Demander s'il veut rejouer
        if (this.joueur.veutJouer()) {
            jouer();
            return;
        }
        this.joueur.finish();
    }

}
