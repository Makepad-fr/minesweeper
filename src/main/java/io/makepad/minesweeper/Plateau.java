package io.makepad.minesweeper;

import java.util.Random;

public class Plateau {
    private final int hauteur;
    private final int largeur;
    private final int nbMin;
    private int nbDrapeaux;
    private final boolean[][] mines;
    private final int[][] etats, adja;

    /**
     * La fonction qui initialise le plateau de jeu
     * @param ha Le nombre des lignes (hauteur de plateau)
     * @param la Le nombre de colonnes (largeur de plateau)
     * @param mi Le nombre de mines
     */
    public Plateau(int ha, int la, int mi) {
        this.hauteur = ha;
        this.largeur = la;
        this.nbMin = mi;
        this.mines = new boolean[ha+2][la+2];
        this.ajouteMinesAlea();
        this.etats= new int[ha+2][la+2];
        this.adja= new int[ha+2][la+2];
        this.calculeAdjacence();
        this.nbDrapeaux = 0;
    }

    /**
     * Exercice 3.3
     * Une méthode qui ajoute NbMines dans le tableau de manière aléatoire.
     */
    private void ajouteMinesAlea() {
        Random r = new Random();
        int added = 0;  // Déclaration de la variable 'added' pour qu'on puisse compter le nombre de la case qu'on a
        // ajouté les mines.
        int i,j;

        // Tant que le nombre de mines ajouté est inférieur au nombre de mines à ajouter.
        while(added <= this.nbMin) {

            i = r.nextInt(this.hauteur) + 1;
            j = r.nextInt(this.largeur) + 1;

            if (mines[i][j]) {
                // Comme dans cette case il y a déjà une mine, nous ne pouvons pas ajouter. Nous allons parcourir une
                // autre case.
                continue;
            }

            // Dans cette case s'il n'y a pas de mines, nous allons ajouter.
            mines[i][j] = true;

            // Chaque fois qu'on ajoute une mine, nous allons incrémenter la valeur de "added".
            added++;
        }
    }

    /**
     * Une méthode qui transformer boolean en entier.
     * @param b
     * @return un entier 1 si le param b est true sinon b
     */
    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    /**
     * Exercice 3.4
     * Une méthode qui remplit  le tableau d’adjacence adja dans son espace utile.
     */
    private void calculeAdjacence() {
        // Comme le jeu contenu dans l’espace délimité par la diagonale de coordonées (1,1) et (ha, la)
        for (int i = 1; i <= this.hauteur; i++) {
            for (int j = 1; j <= this.largeur; j++) {
                // la valeur de adj[i][j] doit être égalé à la somme des valeurs de ses adjacentes.
                adja[i][j] =
                        boolToInt(mines[i-1][j]) +
                        boolToInt(mines[i-1][j-1]) +
                        boolToInt(mines[i-1][j+1]) +
                        boolToInt(mines[i][j-1]) +
                        boolToInt(mines[i][j+1]) +
                        boolToInt(mines[i+1][j]) +
                        boolToInt(mines[i+1][j-1]) +
                        boolToInt(mines[i+1][j+1]);
            }
        }
    }

    /**
     * La fonction qui affiche les statistiques du jeux. Le nombre de mines et le nombre de drapeaux
     * @param width Le largeur de l'affichage du plateau du jeu
     * @return Une chaîne de caractères qui contient le statistiques de la partie courant.
     */
    private String statsString(int width) {
        int w = 20;
        int nbSpace = (width - w) / 2;

        return String.format(
                "%s%s\n%s%s\n%s%s\n%s%s\n",
                " ".repeat(nbSpace),
                "*".repeat(w),
                " ".repeat(nbSpace),
                "* Mines / Drapeaux *",
                " ".repeat(nbSpace),
                "* " +
                " ".repeat(4 - ("" + this.nbMin).length()) +
                this.nbMin + " ".repeat(2) +
                "/" +
                " ".repeat(2) +
                this.nbDrapeaux +
                " ".repeat(7 - (""+this.nbDrapeaux).length()) +
                " *",
                " ".repeat(nbSpace),
                "*".repeat(w));
    }

    @Override
    public String toString() {
        String result = "";
        String line = " ".repeat(4);
        for (int j = 1; j <= this.largeur; j++) {
            line += "" + j;
            if (j == this.largeur) {
                continue;
            }
            line += " ".repeat(2);
        }
        line = line + " ";
        result += statsString(line.length());
        result += line + "\n";
        result += "-".repeat(line.length()) + "\n";
        line = "";
        for (int i = 1; i<= this.hauteur; i++) {
            line += (char)('A' + i - 1) + " |";
            for (int j = 1; j <= this.largeur; j++) {
                line += " ".repeat(("" +j).length()) + (mines[i][j] ? "*" : adja[i][j]) + " ";
            }
            line += "\n";
        }
        result += line;
        return result;
    }

    public void afficheTout() {
        System.out.println(this.toString());
    }

    /**
     * Exercice 3.8
     * Une méthode qui révèle la case dont les coordonnées en entrée, son action consiste à modifier le tableau etats.
     * Si la case contient un drapeau ou était déjà révélée, afficher un message.
     * @param i L'ordonnée de la case à relever
     * @param j L'abscisse de la case à relever
     */
    public void releverCase(int i, int j){
        switch (etats[i][j]) {
            // Si la case est cachée sans drapeau, nous allons relever.
            case 0:
                etats[i][j] = 2;
                if (adja[i][j] == 0) {
                    // S'il n'y a aucune mine dans la case révélée, nous allons relever 8 voisins de cette case.
                    releverCase(i-1, j);
                    releverCase(i-1, j-1);
                    releverCase(i-1, j+1);
                    releverCase(i, j-1);
                    releverCase(i, j+1);
                    releverCase(i+1,j);
                    releverCase(i+1,j-1);
                    releverCase(i+1,j+1);
                }
                break;
            // Si la case est avec un drapeau, nous allons afficher une message.
            case 1:
                System.out.println("La case est cachée avec drapeau");
                break;
            // Si la case est déjà révélée, nous allons afficher une message.
            default:
                System.out.println("La case a été déjà révélée");
                break;
        }
    }

    /**
     * Exercice 3.10
     * Une méthode qui pose/enlève un drapeau sur la case dont on a donné les coordonnées.
     * @param i L'ordonnée de la case que nous allons poser/enlever le drapeau
     * @param j L'abscisse de la case que nous allons poser/enlever le drapeau
     */
    public void drapeauCase(int i, int j){
        switch (etats[i][j]) {
            // Si la case est cachée sans drapeau, nous allons ajouter un drapeau.
            case 0:
                etats[i][j] = 1;
                nbDrapeaux++;
                break;
            case 1:
                etats[i][j] = 0;
                // Comme on a enlevé un drapeau, le nombre de drapeaux est diminué.
                nbDrapeaux--;
                break;
            default:
                System.out.println("La case est déjà révelée.");
                break;
        }
    }

    /**
     * La fonction renvoie la chaîne de caractères associé à l'état
     * @param i L'ordonnée de la case
     * @param j L'abscisse de la case
     * @return Une chaîne de caractères qui represent l'état de la case.
     */
    private String presentEtat(int i, int j) {
        switch (this.etats[i][j]) {
            case 0:
                return ".";
            case 1:
                return "?";
            default:
                if (mines[i][j]) {
                    return "*";
                }
                return adja[i][j] + "";
        }
    }

    /**
     * Fonction qui renvoie une chaîne de caractères qui contient l'état courant de la partie.
     * @return Une chaîne de caractères qui contient l'état courant de la partie.
     */
    public String partieCourantString() {
        String result = "";
        String line = " ".repeat(2);
        for (int j = 1; j <= this.largeur; j++) {
            line += "" + j;
            if (j == this.largeur) {
                continue;
            }
            line += " ".repeat(2);
        }
        line = line + " ";
        result += statsString(line.length());
        result += line + "\n";
        line = "";
        for (int i = 1; i<= this.hauteur; i++) {
            line += (char)('A' + i - 1) + "";
            for (int j = 1; j <= this.largeur; j++) {
                line += " ".repeat(("" +j).length()) + presentEtat(i,j) + " ";
            }
            line += "\n";
        }
        result += line;
        return result;
    }


    /**
     * La fonction qui affiche l'état courant du jeu.
     */
    public void afficheCourant() {
        System.out.println(partieCourantString());
    }

    /**
     * Exercice 3.12
     * Une méthode qui vérifie respectivement si le joueur est mort
     * @return true si le joueur est perdu sinon false
     */
    public boolean jeuPerdu(){
        // Nous allons parcourir le tableau mines,
        for(int i=0; i<mines.length; i++){
            for(int j=0; j<mines[i].length; j++){
                // S'il y a une mine qui est révélée, le joueur est morte.
                if(etats[i][j] == 2 && mines[i][j]) {
                    return true;
                }
            }
        }
        // Si il y a aucun mine qui est révélée, le joueur a gagné.
        return false;
    }

    /**
     * Exercice 3.12
     * Une méthode qui vérifie respectivement si le joueur a gagné.
     * @return true si le joueur a gagné sinon false.
     */
    public boolean jeuGagne(){
        for(int i=0; i<mines.length; i++){
            for(int j=0; j<mines[i].length; j++){
                // Si il y a une case qui n'a pas encore révélée
                if (etats[i][j] == 0 && !mines[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
