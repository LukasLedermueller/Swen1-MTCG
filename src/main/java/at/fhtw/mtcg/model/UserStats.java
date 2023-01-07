package at.fhtw.mtcg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserStats {
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Elo"})
    private int elo;
    @JsonAlias({"Wins"})
    private int wins;
    @JsonAlias({"Losses"})
    private int losses;

    public UserStats(){}

    public UserStats(String name, int elo, int wins, int losses) {
        this.name = name;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void addWin() {
        this.wins++;
        this.elo += 3;
    }

    public void addLoss() {
        this.losses++;
        this.elo -= 5;
    }
}
