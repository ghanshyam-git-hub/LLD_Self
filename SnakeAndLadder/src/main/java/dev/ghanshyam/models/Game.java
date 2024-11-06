package dev.ghanshyam.models;

import java.util.List;
import java.util.Queue;

public class Game {
    private Board board;
    private Queue<Player> players;
    private Dice dice;

    public Game(Board board, Queue<Player> players, Dice dice) {
        this.board = board;
        this.players = players;
        this.dice = dice;
    }

    public void play(){
        while(true){
            Player player = players.remove();
            System.out.println("Turn of player "+player.getName()+". currentPosition = "+player.getCurrentPosition());
            int diceroll = dice.rollDice();

            int newPosition = player.getCurrentPosition() + diceroll;
            System.out.println("dice rolled by player "+player.getName()+" ="+diceroll);
            System.out.println("new position ="+ newPosition);
            if(newPosition>board.getEnd()){ // nothing happens, the player remains at the same position and its turn goes away
                System.out.println("O O Sorry you cant go beyond the board, pls wait until next turn");
                players.add(player);
            }else if(newPosition==board.getEnd()){
                System.out.println("Player "+player.getName()+" rolled dice = "+diceroll);
                System.out.println("Player "+player.getName()+" moves from = "+player.getCurrentPosition()+" to "+newPosition);

                player.setWinner(true);
                System.out.println("Player "+ player.getName()+" has won");
                break;
            }else{
                int tailposition = checkSnakeBite(newPosition);
                int ladderTopPosition = checkLadder(newPosition);

                if(tailposition>0){
                    System.out.println("Oh! it's a snake Bite");
                    player.setCurrentPosition(tailposition);
                    System.out.println("Player "+player.getName()+" moves to = "+player.getCurrentPosition());
                }else if(ladderTopPosition>0){
                    System.out.println("Wow! it's a Ladder");
                    player.setCurrentPosition(ladderTopPosition);
                    System.out.println("Player "+player.getName()+" moves to = "+player.getCurrentPosition());
                }else{
                    player.setCurrentPosition(newPosition);
                }

                players.add(player); // maintaining queue
            }
        }
    }

    private int checkSnakeBite(int newPosition){
        List<Snake>snakeList = board.getSnakeList();
        int snakeTail = -1;
        for(Snake snake : snakeList){
            int start = snake.getStart();
            int end = snake.getEnd();

            if(newPosition==start)  {
                snakeTail=end;
                break;
            }
        }
        return snakeTail;
    }

    private int checkLadder(int newPosition){
        List<Ladder>ladderList = board.getLadderList();
        int ladderTop = -1;
        for(Ladder ladder : ladderList){
            int start = ladder.getStart();
            int end = ladder.getEnd();

            if(newPosition==start)  {
                ladderTop=end;
                break;
            }
        }
        return ladderTop;
    }
}
