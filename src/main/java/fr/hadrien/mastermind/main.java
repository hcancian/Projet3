package fr.hadrien.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe principale qui lance le programme
 * @author hadrien
 * @since 10/10/18
 */
public class Main {

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger();
        logger.info("Lancement de l'application");
        Interface lancement = new Interface();
        lancement.menu();
        logger.info("Lancement du menu fin de jeu avec differentes options");
        lancement.menuEnd();
        logger.info("Le menu est fermé");
        logger.info("fin de l'application");
    }
}
