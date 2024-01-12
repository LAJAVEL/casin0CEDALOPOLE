package pojos;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameStatistics {
    private int coins;
    private int gamesPlayed;
    private int gamesWon;
    private int coinsSpent;

    public GameStatistics() {
        loadStats();
    }

    private void loadStats() {
        Gson gson = new Gson();
        try {
            GameStatistics stats = gson.fromJson(new FileReader("src/main/resources/stats.json"), GameStatistics.class);
            if (stats != null) {
                this.coins = stats.coins;
                this.gamesPlayed = stats.gamesPlayed;
                this.gamesWon = stats.gamesWon;
                this.coinsSpent = stats.coinsSpent;
            }
        } catch (IOException e) {

            coins = 50;
            gamesPlayed = 0;
            gamesWon = 0;
            coinsSpent = 0;
        }
    }

    public void saveStats() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("src/main/resources/stats.json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  mettre à jour les statistiques
    public void updateGamesPlayed() {
        gamesPlayed++;
    }

    public void updateGamesWon() {
        gamesWon++;
    }

    public void updateCoinsSpent(int bet) {
        coinsSpent += bet;
    }

    public void updateCoins(int amount) {
        coins += amount;
    }


}
