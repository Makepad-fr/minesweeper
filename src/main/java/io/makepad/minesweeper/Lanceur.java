package io.makepad.minesweeper;

public class Lanceur {
    public static void main(String[] args) {
        Plateau p = new Plateau(20, 20, 10);
        Joueur j = new Joueur();
        Jeu jeu = new Jeu(j, p);
        jeu.jouer();
    }
}
