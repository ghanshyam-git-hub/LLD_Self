package dev.ghanshyam.models;

public class Player {
    private String name;
    private boolean winner;
    private int currentPosition;

    public Player(String name) {
        this.name = name;
        this.winner = false;
        this.currentPosition = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isWinner() {
        return winner;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
