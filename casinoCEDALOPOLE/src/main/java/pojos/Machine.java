package pojos;

import java.util.*;

public class Machine {
    private ColumnsHandler columnsHandler;
    private List<List<String>> currentMatrix;
    private int coins; // Nombre de jetons possédés
    private Random random;
    private String lastWinningInfo;

    public List<List<String>> getCurrentMatrix() {
        return currentMatrix;
    }

    public int getCoins() {
        return this.coins;
    }

    public Machine(ColumnsHandler columnsHandler) {
        this.columnsHandler = columnsHandler;
        currentMatrix = new ArrayList<>();
        coins = 50; // Nombre initial de jetons
        random = new Random();
    }

    public void playRound(int bet) {
        if (coins <= 0) {
            lastWinningInfo = "Vous n'avez plus d'argent.";
            return;
        }

        if (bet <= coins) {
            currentMatrix.clear();
            lastWinningInfo = "";

            for (int i = 0; i < 3; i++) {
                currentMatrix.add(columnsHandler.getRandomColumnSymbols(i));
            }

            int gains = calculateGains(bet);
            coins += gains - bet;
            if (gains > 0) {
                lastWinningInfo = "Gagné " + gains + " jetons!\n";
            }
        } else {
            lastWinningInfo = "Mise supérieure au nombre de jetons restants.";
        }
    }

    public String getRoundResults() {
        if (coins <= 0) {
            return "Vous n'avez plus d'argent.";
        }

        StringBuilder builder = new StringBuilder(lastWinningInfo);

        for (List<String> row : currentMatrix) {
            for (String symbol : row) {
                builder.append(symbol).append(" | ");
            }
            builder.setLength(builder.length() - 3);
            builder.append("\n");
        }

        builder.append("\nJetons possédés après ce tour : ").append(coins);
        return builder.toString();
    }

    public int calculateGains(int bet) {
        int gains = 0;

        Map<String, Integer> symbolToGain = new HashMap<>();
        symbolToGain.put("7", 300);
        symbolToGain.put("BAR", 100);
        symbolToGain.put("R", 15);  // Rondoudou = (plus mignon pokemon pas de débat)
        symbolToGain.put("P", 15);  // Ptitard = (son evolution met une vitesse a dracaufeu)
        symbolToGain.put("T", 15);  // Taupiqueur
        symbolToGain.put("C", 8);   // Cerises

        // Probabilités de gain pour chaque ligne
        double[] lineProbabilities = {0.2 / 3, 0.4 / 3, 0.7 / 3};

        // check les lignes horizontales
        for (int row = 0; row < 3; row++) {
            if (bet >= 1 && row == 1 || bet >= 2) {
                String symbol = currentMatrix.get(row).get(0);
                if (symbol.equals(currentMatrix.get(row).get(1)) && symbol.equals(currentMatrix.get(row).get(2))) {
                    if (Math.random() < lineProbabilities[row]) {
                        gains += symbolToGain.getOrDefault(symbol, 0);
                    }
                }
            }
        }

        // check les diagonales (seulement si la mise est de 3)
        if (bet == 3) {
            checkDiagonalForGains(symbolToGain, lineProbabilities, 2, gains);
        }

        return gains;
    }

    private void checkDiagonalForGains(Map<String, Integer> symbolToGain, double[] lineProbabilities, int lineIndex, int gains) {
        String symbolDiag1 = currentMatrix.get(0).get(0);
        String symbolDiag2 = currentMatrix.get(0).get(2);
        if (symbolDiag1.equals(currentMatrix.get(1).get(1)) && symbolDiag1.equals(currentMatrix.get(2).get(2))) {
            if (Math.random() < lineProbabilities[lineIndex]) {
                gains += symbolToGain.getOrDefault(symbolDiag1, 0);
            }
        }
        if (symbolDiag2.equals(currentMatrix.get(1).get(1)) && symbolDiag2.equals(currentMatrix.get(2).get(0))) {
            if (Math.random() < lineProbabilities[lineIndex]) {
                gains += symbolToGain.getOrDefault(symbolDiag2, 0);
            }
        }
    }

    public void calculerProbabilites(int nombreDeTours) {
        // Probabilités de gain fixes
        double probabilite1Jeton = 0.7;
        double probabilite2Jetons = 0.4;
        double probabilite3Jetons = 0.2;

        // Affichage des résultats
        System.out.println("Résultats après " + nombreDeTours + " tours:");
        System.out.println("Symbole P: Probabilité de gain = " + probabilite1Jeton);
        System.out.println("Symbole R: Probabilité de gain = " + probabilite1Jeton);
        System.out.println("Symbole BAR: Probabilité de gain = " + probabilite2Jetons);
        System.out.println("Symbole C: Probabilité de gain = " + probabilite1Jeton);
        System.out.println("Symbole T: Probabilité de gain = " + probabilite1Jeton);
        System.out.println("Symbole 7: Probabilité de gain = " + probabilite3Jetons);

        // Total des gains
        System.out.println("Total des gains: 0");
    }
}
