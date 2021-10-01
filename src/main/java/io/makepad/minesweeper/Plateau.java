package io.makepad.minesweeper;

import java.util.Random;

public class Plateau {
    private final int hauteur, largeur, nbMin, nbDrapeaux;
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

    private void ajouteMinesAlea() {
        Random r = new Random();
        int added = 0;
        int i,j;
        while(added <= this.nbMin) {
            i = r.nextInt(this.hauteur) + 1;
            j = r.nextInt(this.largeur) + 1;
            if (mines[i][j]) {
                continue;
            }
            mines[i][j] = true;
            added++;
        }
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    private void calculeAdjacence() {
        for (int i = 1; i <= this.hauteur; i++) {
            for (int j = 1; j <= this.largeur; j++) {
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

    private String headerString() {
        int w = 20;
        int max = 5 + (this.hauteur - 1) * 2 + this.hauteur;
        int nbSpace = (max - w) / 2;

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
        String result = headerString();
        String line = " ".repeat(4);
        for (int j = 1; j <= this.largeur; j++) {
            line += "" + j;
            if (j == this.largeur) {
                continue;
            }
            line += " ".repeat(2);
        }
        line = line + " ";
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



}
