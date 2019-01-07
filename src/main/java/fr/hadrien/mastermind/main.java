package fr.hadrien.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class main {

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger();
        logger.info("Lancement de l'application");
        Interface lancement = new Interface();
        lancement.Menu();
        logger.info("Lancement du menu fin de jeu avec differentes options");
        lancement.menuEnd();
        logger.info("Le menu est fermé");
        logger.info("fin de l'application");
    }
}
