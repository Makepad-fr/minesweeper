package io.makepad.minesweeper;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Joueur {
    private String nom;
    private final Scanner scanReponse = new Scanner(System.in);

    public Joueur(){
        this.nom = "Anonyme";
    }

    public Joueur(String nom) {
        this.nom = nom;
    }


    public void setNom(String nom){
        this.nom = nom;
    }

    public void finish(){
        scanReponse.close();
    }


    /**
     * fonction qui permet de poser une question à l'utilisateur dont la réponse est un caractère
     * @param question La question à poser à l'utilisateur.
     * @return le caractère que l'utilisateur a saisi
     */
    private char demanderChar(String question) {
        System.out.println(question);
        return scanReponse.next().charAt(0);
    }

    /**
     * fonction qui permet de poser une question à l'utilisateur dont la réponse est un entier
     * @param question La question à poser à l'utilisateur
     * @return l'entier saisie par l'utilisateur.
     */
    private int demanderInt(String question) {
        System.out.println(question);
        return this.scanReponse.nextInt();
    }

    /**
     * fonction qui permet de poser une question à l'utilisateur dont la réponse est une chaîne de caractères
     * @param question La question à poser à l'utilisateur
     * @return la chaîne de caractères saisie par l'utilisateur
     */
    private String demanderString(String question) {
        System.out.println(question);
        return this.scanReponse.next();
    }

    /**
     * Fonction qui renvoie si le joueur veut jouer une mache ou pas
     * @return True si le joueur veut jouer une manche, false sinon.
     */
    public boolean veutJouer(){
        return this.demanderString("Voulez-vous jouer (Oui/Non) ?").trim().toLowerCase().equals("oui");
    }

    /**
     * Fonction qui demande à l'utilisateur son nom
     * @return Renvoie une chaîne de caractères qui dit Bonjour à l'utilisateur.
     */
    public String demanderNom(){
        setNom(this.demanderString("Comment vous vous appelez ?"));
        return "Hi "+this.nom+" !";
    }


    /**
     * Fonction qui demande à l'utilisateur la dimensions du plateau qu'il veut jouer avec
     * @return Un tableau à 2 éléments, dans le premier élément est le nombre de lignes et le second est le nombre de colonnes.
     */
    public int[] demanderDimensions(){
        System.out.println("Veuillez insérer les dimensions du plateau que vous voulez jouer");
        return new int[]{
                this.demanderInt("Le nombre de lignes (l'hauteur du plateau) ?"),
                this.demanderInt("Le nombre de colonnes (le largeur du plateau) ?")
        };
    }


    /**
     * Fonction qui demande à l'utilisateur de nombre de mines qu'il veut avoir dans son partie.
     * @return Le nombre de mines que l'utilisateur veut avoir dans la partie.
     */
    public int demanderNbMines(){
        return this.demanderInt("Combien de mines vous voulez avoir ?");
    }


    /**
     * La fonction demande à l'utilisateur l'action qui veut réaliser.
     * @return 'r' si l'utilisateur veut reveler une case, 'd' s'il veut poser un drapeau.
     */
    public char demanderAction(){
        return this.demanderChar("Voulez-vous reveler une case (r) ou placer un drapeau (d) ?");
    }


    /**
     * la fonction demande à l'utilisateur les coordonnées qu'il veut réaliser son action.
     * @return Un tableau à 2 colonnes, dont la première est le nombre de ligne est le deuxième est le nombre de colonnes.
     */
    public int[] demanderCoordonnees(){
        String resp = this.demanderString("Veuillez insérer les coordonnés de la case que vous voulez jouer");
        Pattern strPattern = Pattern.compile("(?![1-9][0-9]*)+"), nbPattern = Pattern.compile("([1-9][0-9]*)");
        Matcher lineMatcher = strPattern.matcher(resp), columnMatcher = nbPattern.matcher(resp);

        return new int[]{
                this.demanderChar("Quelle ligne vous voulez jouer ?") - 'A',
                this.demanderInt("Quelle colon que vous voulez jouer ?")
        };
    }


}
