package fr.hadrien.mastermind.Mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Classe qui crée et prends en compte les difféerents modes de ju du Mastermind
 * @author hadrien
 * @since 15/10/2018
 */
public class GameModeM {

    Logger LOGGER = LogManager.getLogger();
    Scanner SCANNER = new Scanner(System.in);
    private int codeLength;
    private int maxTry;
    private boolean devMode;
    private int numberUse;

    /**
     * Constructeur
     * @param solutionLength
     * @param maxTry
     * @param dev
     * @param numberUse
     */
    public GameModeM(int solutionLength, int maxTry, boolean dev, int numberUse) {
        this.codeLength = solutionLength;
        this.maxTry = maxTry;
        this.devMode = dev;
        this.numberUse = numberUse;
    }

    /**
     * Methode qui permet de générer une nouvelles combinaison aléatoirement
     * en fonction des resultat de la précendente , gardant seulement les bons chiffres bien placés
     * @param previousCombinaison
     * @param goodMatches
     * @param max
     * @param nbChiffres
     * @return newCombinaison
     */
    String generateIACombinaison(String previousCombinaison, List<Integer> goodMatches, int max, int nbChiffres) {
        Random r = new Random();
        String newCombinaison = "";
        for (int i = 0; i < nbChiffres; i++) {
            if (goodMatches.contains(i)) {
                newCombinaison += previousCombinaison.charAt(i);
            } else {
                newCombinaison += r.nextInt(max + 1);
            }
        }
        return newCombinaison;
    }

    /**
     * Méthode qui lance le mode Challenger dans lequel le joueur doit essayer de trouver un
     * code secret crée en aléatoire
     */
    public void mastermindChallenger() {
        LOGGER.info("Lancement du Mastermind en Challenger");
        String code;
        int[] codeEntre = new int[codeLength];
        int[] solution = new int[codeLength];
        int count = 0;
        int i;
        Random r = new Random();
        for (i = 0; i < codeLength; i++) {
            solution[i] = r.nextInt(numberUse + 1);
        }
        LOGGER.info("Création du code secret en aléatoire : " + Arrays.toString(solution));
        if (devMode == true)
            System.out.println(Arrays.toString(solution));
        do {
            System.out.println("Le code sera compris de chiffres entre 0 et " + numberUse);
            System.out.println("Veuillez rentrez une combinaison de chiffres");
            code = Integer.toString(SCANNER.nextInt());
            LOGGER.info("Le joueur a tapé : " + code);
            List<Integer> goodMatches = new ArrayList<>();
            List<Integer> approximativeMatches = new ArrayList<>();
            List<Integer> wrongNumber = new ArrayList<>();
            for (i = 0; i < codeLength; i++) {

                codeEntre[i] = Integer.parseInt(String.valueOf(code.charAt(i)));
                if (codeEntre[i] == solution[i]) {
                    goodMatches.add(i);
                } else {
                    boolean foundMatch = false;
                    for (int j = 0; j < codeLength; j++) {
                        if (codeEntre[i] == solution[j] && i != j && !goodMatches.contains(j)) {
                            approximativeMatches.add(i);
                            foundMatch = true;
                            break;
                        }
                    }
                    if (!foundMatch) wrongNumber.add(i);
                }
            }
            count++;
            if (goodMatches.size() == codeLength)
                System.out.println("Vous avez tout trouvé");
            else {
                System.out.println("Il y a " + goodMatches.size() + "bons chiffres bien placés et "
                        + approximativeMatches.size() + "bon chiffre mal placés et " + wrongNumber.size()
                        + " mauvais chiffre");
            }
            LOGGER.info("Le joueur a essayé : "+code +"avec pour résultats "+goodMatches.size()
                    + " chiffres bons et biens placés ; "+approximativeMatches.size()+" chiffres bons" +
                    "mais mal placés et "+wrongNumber.size()+" mauvais chiffres");
        } while (!Arrays.toString(codeEntre).equals(Arrays.toString(solution)) && count <= maxTry);

        if (Arrays.toString(codeEntre).equals(Arrays.toString(solution))) {
            System.out.println("vous avez trouvé le code en seulement " + count + " essais");
            LOGGER.info("Le joueur a trouvé le code secret");
        } else {
            System.out.println("vous n'avez  pas trouvé le code");
            LOGGER.info("Le joueur a perdu");
        }
        LOGGER.info("Fin du Mastermind en Challenger");
    }

    /**
     * Méthode qui lance le mode Defense dans lequel l' IA doit essayer de trouver un
     * code secret crée par le joueur
     */
    public void mastermindDefense() {
        LOGGER.info("Lancement du Mastermind en Defensif");
        String code = "";
        int max = numberUse;
        int[] codeEntre = new int[codeLength];
        int[] solution = new int[codeLength];
        int count = 0;
        int i;

        System.out.println("Veuillez rentrez une combinaison de " + codeLength + " chiffres que L'IA doit deviner en" +
                " utilisant des chiffres entre 0 et " + numberUse + "  uniquement ");
        String myCombinaison = SCANNER.next();

        for (i = 0; i < codeLength; i++) {
            solution[i] = myCombinaison.charAt(i);
        }
        LOGGER.info("Saisie du code secret par le joueur pour l'IA");
        if (devMode == true)
            System.out.println(myCombinaison);
        List<Integer> goodMatches = new ArrayList<>();
        List<Integer> approximativeMatches = new ArrayList<>();
        List<Integer> wrongNumber = new ArrayList<>();

        do {
            System.out.println("L'IA va jouer");
            SCANNER.nextLine();
            code = generateIACombinaison(code, goodMatches, max, codeLength);
            System.out.println("Combinaison de l'IA " + code);
            LOGGER.info("l'IA a tapé : "+code );
            goodMatches.clear();
            approximativeMatches.clear();
            wrongNumber.clear();
            for (i = 0; i < codeLength; i++) {

                if (code.charAt(i) == solution[i]) {
                    goodMatches.add(i);
                } else {
                    boolean foundMatch = false;
                    for (int j = 0; j < codeLength; j++) {
                        if (codeEntre[i] == solution[j] && i != j && !goodMatches.contains(j)) {
                            approximativeMatches.add(i);
                            foundMatch = true;
                            break;
                        }
                    }
                    if (!foundMatch) wrongNumber.add(i);
                }
            }
            count++;
            if (goodMatches.size() == codeLength)
                System.out.println("Vous avez tout trouvé");
            else {
                System.out.println("Il y a " + goodMatches.size() + " bons chiffres bien placés et "
                        + approximativeMatches.size() + " bon chiffre mal placés et " + wrongNumber.size()
                        + " mauvais chiffre");
            }
            LOGGER.info("L'IA a essayé : "+code +"avec pour résultats "+goodMatches.size()
                    + " chiffres bons et biens placés ; "+approximativeMatches.size()+" chiffres bons" +
                    "mais mal placés et "+wrongNumber.size()+" mauvais chiffres");
        } while (!Arrays.toString(codeEntre).equals(Arrays.toString(solution)) && count <= maxTry);

        if (Arrays.toString(codeEntre).equals(Arrays.toString(solution))) {
            LOGGER.info("L'IA a perdu");
            System.out.println("vous n'avez  pas trouvé le code");
        } else {
            System.out.println("vous avez trouvé le code en seulement " + count + " essais");
            LOGGER.info("L'IA a gagné");

        }
        LOGGER.info("Fin du Mastermind en Defensif");
    }

    /**
     * Méthode qui lance le mode Duel dans lequel le joueur doit essayer de trouver un
     * code secret crée en aléatoire et l'IA un code crée par le joueur
     */
    public void mastermindDuel() {
        /**
         * variable pour le joueur
         */
        String code;

        int[] codeEntre = new int[codeLength];
        int[] solution = new int[codeLength];
        int count = 0;
        int i;
        Random r = new Random();
        for (i = 0; i < codeLength; i++) {
            solution[i] = r.nextInt(numberUse + 1);
        }
        LOGGER.info("Création en aléatoire du code secret pour le joueur :"
                +Arrays.toString(solution));
        if (devMode == true)
            System.out.println(Arrays.toString(solution));
        List<Integer> goodMatches = new ArrayList<>();
        List<Integer> approximativeMatches = new ArrayList<>();
        List<Integer> wrongNumber = new ArrayList<>();
        boolean playerWin = false;

        /**
         * variables pour l'ia
         */
        String codeIA = "";
        int maxIA = numberUse;
        int[] codeEntreIA = new int[codeLength];
        int[] solutionIA = new int[codeLength];
        int countIA = 0;
        boolean IAWin = false;
        System.out.println("Veuillez rentrez une combinaison de " + codeLength + " chiffres que L'IA doit deviner ");
        System.out.println("Le code sera compris de chiffres entre 0 et " + numberUse);
        String myCombinaisonIA = SCANNER.next();
        for (i = 0; i < codeLength; i++) {
            solutionIA[i] = myCombinaisonIA.charAt(i);
        }
        LOGGER.info("Creation du code secret rentré par le joueur pour l'IA : "
                +Arrays.toString(solutionIA));
        List<Integer> goodMatchesIA = new ArrayList<>();
        List<Integer> approximativeMatchesIA = new ArrayList<>();
        List<Integer> wrongNumberIA = new ArrayList<>();
        do {
            System.out.println("Veuillez rentrez une combinaison de chiffres");
            code = Integer.toString(SCANNER.nextInt());
            LOGGER.info("Le joueur a tapé : "+code);
            for (i = 0; i < codeLength; i++) {

                codeEntre[i] = Integer.parseInt(String.valueOf(code.charAt(i)));
                if (codeEntre[i] == solution[i]) {
                    goodMatches.add(i);
                } else {
                    boolean foundMatch = false;
                    for (int j = 0; j < codeLength; j++) {
                        if (codeEntre[i] == solution[j] && i != j && !goodMatches.contains(j)) {
                            approximativeMatches.add(i);
                            foundMatch = true;
                            break;
                        }
                    }
                    if (!foundMatch) wrongNumber.add(i);
                }
            }
            count++;
            if (goodMatches.size() == codeLength) {
                System.out.println("Vous avez tout trouvé");
                playerWin = true;
            } else {
                System.out.println("Il y a " + goodMatches.size() + "bons chiffres bien placés et "
                        + approximativeMatches.size() + "bon chiffre mal placés et " + wrongNumber.size()
                        + " mauvais chiffre");
                LOGGER.info("Le joueur a essayé : "+code +"avec pour résultats "+goodMatches.size()
                        + " chiffres bons et biens placés ; "+approximativeMatches.size()+" chiffres bons" +
                        "mais mal placés et "+wrongNumber.size()+" mauvais chiffres");
            }
            goodMatches.clear();
            approximativeMatches.clear();
            wrongNumber.clear();
            System.out.println("L'IA va jouer");
            SCANNER.nextLine();
            codeIA = generateIACombinaison(codeIA, goodMatchesIA, maxIA, codeLength);
            System.out.println("Combinaison de l'IA " + codeIA);
            LOGGER.info("L'IA a tapé : "+codeIA);
            goodMatchesIA.clear();
            approximativeMatchesIA.clear();
            wrongNumberIA.clear();
            if (devMode == true)
                System.out.println(myCombinaisonIA);
            for (i = 0; i < codeLength; i++) {

                if (codeIA.charAt(i) == solutionIA[i]) {
                    goodMatchesIA.add(i);
                } else {
                    boolean foundMatchIA = false;
                    for (int j = 0; j < codeLength; j++) {
                        if (codeEntreIA[i] == solutionIA[j] && i != j && !goodMatchesIA.contains(j)) {
                            approximativeMatchesIA.add(i);
                            foundMatchIA = true;
                            break;
                        }
                    }
                    if (!foundMatchIA) wrongNumberIA.add(i);
                }
            }
            countIA++;
            if (goodMatchesIA.size() == codeLength)
                System.out.println("Vous avez tout trouvé");
            else {
                System.out.println("Il y a " + goodMatchesIA.size() + "bons chiffres bien placés et "
                        + approximativeMatchesIA.size() + "bon chiffre mal placés et " + wrongNumberIA.size()
                        + " mauvais chiffre");
                LOGGER.info("L'IA a essayé : "+codeIA +"avec pour résultats "+goodMatchesIA.size()
                        + " chiffres bons et biens placés ; "+approximativeMatchesIA.size()+" chiffres bons" +
                        "mais mal placés et "+wrongNumberIA.size()+" mauvais chiffres");
            }
        } while (playerWin == false && count <= maxTry && IAWin == false && countIA <= maxTry);
        if (!Arrays.toString(codeEntreIA).equals(Arrays.toString(solutionIA))) {
            System.out.println("vous n'avez  pas trouvé le code");
            LOGGER.info("L'IA a gagne");

        }
        if (!Arrays.toString(codeEntre).equals(Arrays.toString(solution))) {
            System.out.println("l'IA n'a  pas trouvé le code");
            LOGGER.info("Le joueur a gangé");
        }
        LOGGER.info("Fin du Mastermin en Duel");
    }
}







