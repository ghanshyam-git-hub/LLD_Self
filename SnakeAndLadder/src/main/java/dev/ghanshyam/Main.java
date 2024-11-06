package dev.ghanshyam;

import dev.ghanshyam.exceptions.WrongDifficultyLevel;
import dev.ghanshyam.models.*;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the size of the board");
        int boardSize = sc.nextInt();
        System.out.println("Enter board start point");
        int boardStart = sc.nextInt();
        int boardEnd = boardStart+boardSize-1;

        Board board;
        while(true){
            System.out.println("enter the difficulty Level - 0:Easy, 1:Medium, 2:Hard,3:Medium Hard");
            int difficultyLevel = sc.nextInt();
            switch(difficultyLevel){
                case 0 : board = new Easy(boardStart,boardEnd);break;
                case 1 : board = new Medium(boardStart,boardEnd);break;
                case 2 : board = new Hard(boardStart,boardEnd);break;
                case 3 : board = new MediumHard(boardStart,boardEnd);break;
                default:
                    try {
                        throw new WrongDifficultyLevel("choose appropriate difficulty level");
                    } catch (WrongDifficultyLevel e) {
                        e.printStackTrace();
                        continue;
                    }
            }
            break;
        }

        System.out.println("Enter no player");
        int playerCount = sc.nextInt();

        Queue<Player> playerQueue = new ArrayDeque<>();
        while(playerCount>0){
            System.out.println("Enter player name");
            playerQueue.add(new Player(sc.next()));
            playerCount--;
        }

        System.out.println("Enter dice start");
        int diceStart = sc.nextInt();
        System.out.println("Enter dice end");
        int diceEnd = sc.nextInt();

        Dice dice = new Dice(diceStart,diceEnd);

        Game game = new Game(board,playerQueue,dice);

        List<Snake>snakeList = board.getSnakeList();
        List<Ladder>ladderList = board.getLadderList();

        System.out.println(snakeList);
        System.out.println(ladderList);

        game.play();
    }
}