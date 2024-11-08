package dev.ghanshyam;

public interface WinningStrategy {
    Player findWinner(Game game,Cell lastMoveCell);
}
