package dev.ghanshyam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
         Board board;
    List<Player> players;
    List<WinningStrategy> winningStrategies;
         */
        System.out.println("Enter the size of the Board");
        Scanner sc = new Scanner(System.in);

        int size = sc.nextInt();
        Board board = new Board(size);

        List<Player>playerList = new ArrayList<>();
       for(int i=0;i<size-1;i++){
           System.out.println("enter the player name and symbol character Ex- Hari X");
           String name = sc.next();
           String symbol = sc.next();
           if(symbol.length()>1) throw new IllegalArgumentException("Symbol cannot be of length greater than 1");
           char symbolChar = symbol.charAt(0);

           Player player = new Player(i,name,symbolChar);
           playerList.add(player);
       }

       List<WinningStrategy> winningStrategyList = new ArrayList<>();
        winningStrategyList.add(new RowWinningStrategy(size));
        winningStrategyList.add(new ColWinningStrategy(size));
        winningStrategyList.add(new DiagnolWinningStrategy());

        Game game = new Game(board,playerList,winningStrategyList);
        Player winner = game.play();
        if(winner==null) System.out.println("Game draw");
        else System.out.println("Winner is "+winner.getName());
    }
}