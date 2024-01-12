package org.example;

import pojos.ColumnsHandler;
import pojos.Machine;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // creation instances
        ColumnsHandler columnsHandler = new ColumnsHandler();
        Machine machine = new Machine(columnsHandler);

        // lire l'entrée de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        int jetons;

        while (true) {
            System.out.println("\nBienvenue au Casino de Céladopole !");
            System.out.println("Choisissez une option :");
            System.out.println("1. Jouer un tour");
            System.out.println("2. Calculer les probabilités ");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();

            // choix de l'utilisateur
            switch (choix) {
                case 1: // Jouer un tour
                    System.out.print("Combien de jetons ? (1, 2 ou 3) : ");
                    jetons = scanner.nextInt();

                    // Vérifier que l'entrée est valide
                    if (jetons < 1 || jetons > 3) {
                        System.out.println("Veuillez entrer un nombre valide de jetons (1, 2, ou 3).");
                    } else {
                        // Lancer un tour de jeu
                        machine.playRound(jetons);
                        // Affichage des résultats du tour
                        System.out.println(machine.getRoundResults());
                    }
                    break;

                case 2: // Calculer les probabilités
                    System.out.print("Entrez le nombre de tours pour le calcul : ");
                    int nombreDeTours = scanner.nextInt();
                    machine.calculerProbabilites(nombreDeTours);
                    break;

                case 3: // Quitter
                    System.out.println("Merci d'avoir joué au Casino de Céladopole !");
                    scanner.close();
                    return;

                default:
                    System.out.println("Option non valide. Veuillez réessayer.");
                    break;
            }
        }
    }
}
