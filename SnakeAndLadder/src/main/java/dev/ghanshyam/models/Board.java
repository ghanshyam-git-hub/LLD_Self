package dev.ghanshyam.models;

import java.util.List;

public abstract class Board {
    protected int size;
    protected int boardStart;
    protected int boardEnd;
    protected List<Snake>snakeList;
    protected List<Ladder>ladderList;

    abstract List<Snake> initializeSnakesList();
    abstract List<Ladder> initializeLadderList();

    public int getSize() {
        return size;
    }

    public int getStart() {
        return boardStart;
    }

    public int getEnd() {
        return boardEnd;
    }

    public List<Snake> getSnakeList() {
        return snakeList;
    }

    public List<Ladder> getLadderList() {
        return ladderList;
    }

}
