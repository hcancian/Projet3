package fr.hadrien.mastermind;

import fr.hadrien.mastermind.Mastermind.GameModeM;
import fr.hadrien.mastermind.MoreOrLess.GameMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Interface {

    Scanner SCANNER = new Scanner(System.in);
    Properties PROPERTIES = new Properties();
    Logger LOGGER = LogManager.getLogger();

    GameMode gameMode = new GameMode(Integer.parseInt(getPropValues(1)),
            Integer.parseInt(getPropValues(2)),Boolean.parseBoolean(getPropValues(3)));
    GameModeM gameModeM = new GameModeM(Integer.parseInt(getPropValues(1)),
            Integer.parseInt(getPropValues(2)),Boolean.parseBoolean(getPropValues(3))
            ,Integer.parseInt(getPropValues(4)));


    /**
     * Methode permettant de charger la liste des propriétés contenu dans le fichier spécifié
     * et qui renvoi un String specifique en fonction de nb
     * @param nb
     * @return valeur[nb]
     */
    public  String getPropValues(int nb) {
        String configPath = "src/main/resources/config.properties";
        try {
            FileInputStream in = new FileInputStream(configPath);
            PROPERTIES.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Unable to load config file.");
        }
        String solutionLenght = PROPERTIES.getProperty("solutionLenght");
        String maxTry = PROPERTIES.getProperty("maxTry");
        String dev = PROPERTIES.getProperty("dev");
        String numberUse = PROPERTIES.getProperty("numberUse");

        String[] valeurs = {solutionLenght, maxTry, dev,numberUse};
        if (nb == 1)
            return valeurs[0];
        else if (nb == 2)
            return valeurs[1];
        else if(nb == 3)
            return valeurs[2];
        else
            return valeurs[3];
    }

    /**
     *Menu endgame qui permet de quitter l'application ou de lancer directement un autre jeu
     * ou le meme
     */

    public void menuEnd() {
        System.out.println("Option 1 : quitter le jeu ");
        System.out.println("Option 2 : lancer le MoreOrLess en mode challenger");
        System.out.println("Option 3 :lancer le MoreOrLess en  le mode defenseur");
        System.out.println("Option 4 : lancer le MoreOrLess en  le mode duel");
        System.out.println("Option 5 : lancer le Mastermind en challenger");
        System.out.println("Option 6 : lancer le Mastermind en defenseur");
        System.out.println("Option 7 : lancer le Mastermind en duel");
        int options = SCANNER.nextInt();
        switch (options){
            case 1:
                break;
            case 2 :
                gameMode.moreOrLessChallenger();
                break;
            case 3:
                gameMode.moreOrLessDefenseur();
                break;
            case 4:
                gameMode.moreOrLessDuel();
                break;
            case 5:
                gameModeM.mastermindChallenger();
                break;
            case 6:
                gameModeM.mastermindDefense();
                break;
            case 7:
                gameModeM.mastermindDuel();
                break;
        }
    }

    /**
     * Methode qui ne prends pas de paremetre en compte et qui lance le menu permettant
     * d'acceder aux différents jeux et leurs modes
     */
    public  void menu() {
        LOGGER.info("Ouverture du menu ");
        System.out.println("Veuillez entrez dans le jeu de votre choix : \n");
        System.out.println("1 : MoreOrLess \n");
        System.out.println("2 : MasterMind \n");
        LOGGER.info("Choix du jeu");
        try {
            int Game = SCANNER.nextInt();
            switch (Game) {
                case 1:
                    LOGGER.info("Le MoreOrLess a été choisi");
                    System.out.println("- Vous avez choisis le MoreOrLess");
                    System.out.println("Veuillez choisir le mode auquel vous souhaitez jouer :");
                    System.out.println("1 : Mode Challenger");
                    System.out.println("2 : Mode Defenseur ");
                    System.out.println("3 : Mode Duel");
                    LOGGER.info("Choix du mode de jeu");
                    int Mode = SCANNER.nextInt();
                    switch (Mode) {
                        case 1:
                            System.out.println("Vous avex choisi le mode challenger");
                            try {
                                gameMode.moreOrLessChallenger();
                            } catch (InputMismatchException e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 2:
                            System.out.println("Vous avex choisi le mode defenseur");
                            try {
                                gameMode.moreOrLessDefenseur();
                            } catch (InputMismatchException e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.println("Vous avex choisi le mode Duel");
                            try {
                                gameMode.moreOrLessDuel();
                            } catch (InputMismatchException e) {
                                System.out.print(e.getMessage());
                            }
                    }
                    break;
                case 2:
                    LOGGER.info("Le Mastermind a été choisi");
                    System.out.println("- Vous avez choisis le MasterMind");
                    System.out.println("Veuillez choisir le mode auquel vous souhaitez jouer :  ");
                    System.out.println("1 : Mode Challenger");
                    System.out.println("2 : Mode Defenseur");
                    System.out.println("3 : Mode Duel");
                    LOGGER.info("Choix du mode de jeu");
                    Mode = SCANNER.nextInt();
                    switch (Mode) {
                        case 1:
                            System.out.println("Vous avex choisi le mode challenger");
                            try {
                                gameModeM.mastermindChallenger();
                            } catch (InputMismatchException e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 2:
                            System.out.println("Vous avex choisi le mode défenseur");
                            try {
                                gameModeM.mastermindDefense();
                            } catch (InputMismatchException e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.println("Vous avex choisi le mode Duel");
                            try {
                                gameModeM.mastermindDuel();
                            } catch (InputMismatchException e) {
                                System.out.print(e.getMessage());
                            }
                    }
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

