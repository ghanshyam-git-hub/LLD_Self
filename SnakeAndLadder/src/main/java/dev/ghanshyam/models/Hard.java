package dev.ghanshyam.models;

import java.util.*;

public class Hard extends Board {
    private List<Snake>snakeList;
    private List<Ladder>ladderList;
    private Random random = new Random();

    public Hard(int boardStart, int boardEnd) {
        this.boardStart = boardStart;
        this.boardEnd = boardEnd;

        snakeList = initializeSnakesList();
        ladderList = initializeLadderList();
    }

    @Override
     List<Snake> initializeSnakesList() {
        int totalSnakes = 20;
        Set<Snake> setOfSnakes = new HashSet<>();
            while(setOfSnakes.size()<totalSnakes) {
                int start = random.nextInt(boardStart, boardEnd);
                int end = random.nextInt(boardStart, start); // snake end should be less than start
                Snake snake = new Snake(start, end);
                setOfSnakes.add(snake);
            }
        return new ArrayList<>(setOfSnakes);
    }

    @Override
    List<Ladder> initializeLadderList() {
        int totalLadder = 5;
        Set<Ladder> setOfLadder = new HashSet<>();
        while(setOfLadder.size()<totalLadder) {
            int start = random.nextInt(boardStart, boardEnd-1); // // ladder should not be from 99 to 99, atleast 98 to 99 is ok
            int end = random.nextInt(start, boardEnd); // snake end should be less than start
            if(isThisASnakePair(start,end)) continue;
            Ladder ladder = new Ladder(start, end);
            setOfLadder.add(ladder);
        }
        return new ArrayList<>(setOfLadder);
    }

    private boolean isThisASnakePair(int start, int end){
        return snakeList.stream().anyMatch(snake-> (snake.start==start) || (snake.start==end) ||
                                            (snake.end==start) || (snake.end==end));
    }
}