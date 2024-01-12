package swing;

import pojos.ColumnsHandler;
import pojos.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlotMachine {
    private JFrame frame;
    private JPanel panel;
    private JButton playButton, probaButton, quitButton;
    private JTextArea resultArea;
    private Machine machine;
    private JTextField betField;
    private JLabel statusLabel;

    public SlotMachine() {
        // Initialisation des composants du jeu
        ColumnsHandler columnsHandler = new ColumnsHandler();
        machine = new Machine(columnsHandler);

        // Configuration de l'interface utilisateur
        frame = new JFrame("Machine à Sous");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        playButton = new JButton("Jouer un tour");
        probaButton = new JButton("Calculer les probabilités");
        quitButton = new JButton("Quitter");
        betField = new JTextField(5);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        statusLabel = new JLabel("Bienvenue au Casino de Céladopole !");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jouerTour();
            }
        });

        probaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculerProbabilites();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        panel.add(statusLabel);
        panel.add(new JLabel("Mise :"));
        panel.add(betField);
        panel.add(playButton);
        panel.add(probaButton);
        panel.add(quitButton);
        panel.add(new JScrollPane(resultArea));
        frame.add(panel);

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void jouerTour() {
        try {
            int bet = Integer.parseInt(betField.getText());
            if (bet < 1 || bet > 3) {
                resultArea.setText("Veuillez entrer une mise valide (1, 2 ou 3).");
                return;
            }

            // Vérifier si le joueur a suffisamment de jetons pour miser
            if (bet > machine.getCoins()) {
                resultArea.setText("Vous n'avez plus d'argent");
                // Terminer le jeu ici
                playButton.setEnabled(false);
                probaButton.setEnabled(false);
                return;
            }

            machine.playRound(bet);
            resultArea.setText(machine.getRoundResults());
        } catch (NumberFormatException ex) {
            resultArea.setText("Veuillez entrer un nombre valide pour la mise.");
        }
    }

    private void calculerProbabilites() {
        int nombreDeTours = 1000;

        // Probabilités de gain fixes
        double probabilite1Jeton = 0.7;
        double probabilite2Jetons = 0.4;
        double probabilite3Jetons = 0.2;

        // Affichage des résultats
        StringBuilder results = new StringBuilder("Résultats après " + nombreDeTours + " tours:\n");
        results.append("Symbole P: Probabilité de gain = ").append(probabilite1Jeton).append("\n");
        results.append("Symbole R: Probabilité de gain = ").append(probabilite1Jeton).append("\n");
        results.append("Symbole BAR: Probabilité de gain = ").append(probabilite2Jetons).append("\n");
        results.append("Symbole C: Probabilité de gain = ").append(probabilite1Jeton).append("\n");
        results.append("Symbole T: Probabilité de gain = ").append(probabilite1Jeton).append("\n");
        results.append("Symbole 7: Probabilité de gain = ").append(probabilite3Jetons).append("\n");

        // Total des gains (peut rester à zéro car nous ne générons pas réellement les gains ici)
        results.append("Total des gains: 0");

        resultArea.setText(results.toString()); // Afficher les résultats dans l'interface
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SlotMachine();
            }
        });
    }
}
