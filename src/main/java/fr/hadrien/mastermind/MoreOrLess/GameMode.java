package fr.hadrien.mastermind.MoreOrLess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameMode {
    Scanner SCANNER = new Scanner(System.in);
    int CODE_LENGTH;
    int MAX_TRY;
    boolean DEV_MODE;
    Logger LOGGER = LogManager.getLogger();

    public GameMode(int solutionLength, int maxTry, boolean dev) {
        this.CODE_LENGTH = solutionLength;
        this.MAX_TRY = maxTry;
        this.DEV_MODE = dev;
    }

    /**
     * Méthode qui lance le mode Challenger dans lequel le joueur doit essayer de trouver un
     * code secret crée en aléatoire
     */
    public void moreOrLessChallenger() {
        LOGGER.info("Lancement du mode Challenger du jeu MoreOrLess");
        String code;
        String number = "0123456789";
        String verif = "";
        int length = number.length();
        Random rand = new Random();

        char[] codeEntre = new char[CODE_LENGTH];
        char[] secretEntre = new char[CODE_LENGTH];
        int i;
        LOGGER.info("Création du code secret a trouvé en aléatoire");
        for (i = 0; i < CODE_LENGTH; i++) {
            int k = rand.nextInt(length);
            secretEntre[i] = number.charAt(k);
        }
        int compteur = 0;

        if (DEV_MODE == true) {
            System.out.println(Arrays.toString(secretEntre));
        }
        do {
            System.out.println("Veuillez rentrez une combinaison de "+CODE_LENGTH+" chiffres");
            code = SCANNER.next();
            LOGGER.info("Le code saisie par le joueur est : " + code);
            try {
                Integer.parseInt(code);
                if (code.length() == CODE_LENGTH) {
                    for (i = 0; i < CODE_LENGTH; i++) {
                        codeEntre[i] = code.charAt(i);
                        if (codeEntre[i] == secretEntre[i])
                            verif += "=";
                        else if (codeEntre[i] < secretEntre[i])
                            verif += "+";
                        else if (codeEntre[i] > secretEntre[i])
                            verif += "-";
                    }
                    LOGGER.info("Comparaison du code entré : "+Arrays.toString(codeEntre)+
                            " et du code secret : "+Arrays.toString(secretEntre)+
                            " avec une vérification : "+verif);
                    System.out.println("Vous avez tapé : " + code + " Etat : " + verif);
                    verif = "";
                    compteur++;
                } else {
                    System.out.println("pas assez de chiffre ou trop de chiffre");
                    LOGGER.info("Arrete le programme si le nombre de chiffe du code entre" +
                            "est différent de celui attendu");
                }
            } catch (NumberFormatException e) {
                System.out.println("La chaine de caractere n'est pas composé de chiffre");
                LOGGER.info("Arrete le programme si le code entré n'est pas composé de chiffres");
            }
        } while (!Arrays.toString(codeEntre).equals(Arrays.toString(secretEntre)) && compteur <= MAX_TRY);
        if (Arrays.toString(codeEntre).equals(Arrays.toString(secretEntre))) {
            System.out.println("vous avez trouvé le code");
            LOGGER.info("Le Joueur a gagné");
        } else {
            System.out.println("vous n'avez  pas trouvé le code");
            LOGGER.info("Le Joueur a perdu");
        }
        LOGGER.info("Fin du MoreOrLess en Challenger");
    }

    /**
     * Méthode qui lance le mode Defenseur dans lequel l'IA doit essayer de trouver un
     * code secret crée en aléatoire
     */
    public void moreOrLessDefenseur() {
        LOGGER.info("Lancement du mode Defensif du jeu MoreOrLess");
        Random r = new Random();
        String verif = "";

        int[] codeEntre = new int[CODE_LENGTH];
        int[] secretEntre = new int[CODE_LENGTH];
        int i;
        int compteur = 0;
        LOGGER.info("Création d'un code secret et code entré en random");
        for (i = 0; i < CODE_LENGTH; i++) {
            codeEntre[i] = r.nextInt(9 + 1);
            secretEntre[i] = r.nextInt(9 + 1);
        }

        do {
            if (DEV_MODE == true)
            System.out.println(Arrays.toString(secretEntre));

            for (i = 0; i < CODE_LENGTH; i++) {
                if (codeEntre[i] == secretEntre[i])
                    verif += "=";
                else if (codeEntre[i] < secretEntre[i]) {
                    verif += "+";
                    codeEntre[i] = codeEntre[i] + 1;
                } else if (codeEntre[i] > secretEntre[i]) {
                    verif += "-";
                    codeEntre[i] = codeEntre[i] - 1;
                }
            }
            LOGGER.info("Comparaison du code entré : "+Arrays.toString(codeEntre)+
                    " et du code secret : "+Arrays.toString(secretEntre)+
                    " avec une vérification : "+verif);
            System.out.println("Vous avez tapé : " + Arrays.toString(codeEntre) + " Etat : " + verif);
            verif = "";
            compteur++;
        } while ((!Arrays.toString(codeEntre).equals(Arrays.toString(secretEntre))) && compteur <= MAX_TRY);
        if (Arrays.toString(codeEntre).equals(Arrays.toString(secretEntre))) {
            System.out.println("vous avez trouvé le code");
            LOGGER.info("L'IA a gagné");
        } else {
            System.out.println("vous n'avez  pas trouvé le code");
            LOGGER.info("L'IA a perdu");
        }
        LOGGER.info("Fin du MoreOrLess en Defensif");
    }
    /**
     * Méthode qui lance le mode Duel dans lequel le joueur et l'IA s'affronte pour essayer de trouver
     * chacun leur code secret crée en aléatoire
     */
    public void moreOrLessDuel() {
        LOGGER.info("Lancement du mode Duel du jeu MoreOrLess");
        //Pour l'ia
        Random r = new Random();
        String verifIA = "";
        int[] codeEntreIA = new int[7];
        int[] secretEntreIA = new int[7];

        int i;
        int compteurIA = 0;
        LOGGER.info("Création du code secret et code entré en aléatoire pour l'IA");
        for (i = 0; i < 6; i++) {
            codeEntreIA[i] = r.nextInt(9 + 1);
            secretEntreIA[i] = r.nextInt(9 + 1);
        }

        boolean iaWin = false;
        //Pour le Joueur
        String code;
        String number = "0123456789";
        String verif = "";
        int length = number.length();
        Random rand = new Random();
        char[] codeEntre = new char[CODE_LENGTH];
        char[] secretEntre = new char[CODE_LENGTH];
        for (i = 0; i < CODE_LENGTH; i++) {
            int k = rand.nextInt(length);
            secretEntre[i] = number.charAt(k);
        }
        LOGGER.info("Creation du code secret en aleatoire pour le Joueur "
                + Arrays.toString(secretEntre));
        int compteur = 0;
        if (DEV_MODE == true) {
            System.out.println(Arrays.toString(secretEntre));
        }
        boolean playerWin = false;
        //Pour les 2
        String winner = "";
        do {
            code = SCANNER.next();
            LOGGER.info("Code saisie par le Joueur : "+code);

            try {
                Integer.parseInt(code);
                if (code.length() == CODE_LENGTH) {
                    for (i = 0; i < CODE_LENGTH; i++) {
                        codeEntre[i] = code.charAt(i);

                        if (codeEntre[i] == secretEntre[i])
                            verif += "=";
                        else if (codeEntre[i] < secretEntre[i])
                            verif += "+";
                        else if (codeEntre[i] > secretEntre[i])
                            verif += "-";
                    }
                }
                LOGGER.info("Comparaison du code entré : "+Arrays.toString(codeEntre)+
                        " et du code secret : "+Arrays.toString(secretEntre)+
                        " avec une vérification : "+verif);
                System.out.println("Vous avez tapé : " + code + " Etat : " + verif);
                verif = "";
                if (Arrays.toString(codeEntre).equals(Arrays.toString(secretEntre))) {
                    System.out.println("vous avez trouvé le code");
                    LOGGER.info("Victoire du joueur");
                    winner = "le Joueur";
                    playerWin = true;
                }
                compteur++;
            } catch (NumberFormatException e) {
                System.out.println("La chaine de caractere n'est pas composé de chiffre");
                LOGGER.info("Arrete le programme si le nombre de chiffre est " +
                        "différent de celui attendu");
            }

            for (i = 0; i < 7; i++) {
                if (codeEntreIA[i] == secretEntreIA[i])
                    verifIA += "=";
                else if (codeEntreIA[i] < secretEntreIA[i]) {
                    verifIA += "+";
                    codeEntreIA[i] = codeEntreIA[i] + 1;
                } else if (codeEntreIA[i] > secretEntreIA[i]) {
                    verifIA += "-";
                    codeEntreIA[i] = codeEntreIA[i] - 1;
                }

            }
            LOGGER.info("Comparaison du code entré : "+Arrays.toString(codeEntreIA)+
                    " et du code secret : "+Arrays.toString(secretEntreIA)+
                    " avec une vérification : "+verifIA);
            System.out.println("L'IA a tapé : " + Arrays.toString(codeEntreIA) + " Etat : " + verifIA);
            verifIA = "";
            if (Arrays.toString(codeEntreIA).equals(Arrays.toString(secretEntreIA))) {
                System.out.println("L'IA a trouvé le code");
                LOGGER.info("Victoire de l'IA");
                winner = "l'IA";
                iaWin = true;
                compteurIA++;
            }
        } while (playerWin == false && compteur <= MAX_TRY && iaWin == false && compteurIA <= MAX_TRY);
        if (!Arrays.toString(codeEntreIA).equals(Arrays.toString(secretEntreIA))) {
            System.out.println("l'IA n'a  pas trouvé le code");
        }
        if (!Arrays.toString(codeEntre).equals(Arrays.toString(secretEntre))) {
            System.out.println("vous n'avez  pas trouvé le code");
        }
        LOGGER.info("Fin du mode de jeu Duel pour le MoreOrLess avec comme gagnant " + winner);
    }
}
