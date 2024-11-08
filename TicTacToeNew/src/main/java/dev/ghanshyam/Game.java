package dev.ghanshyam;

import dev.ghanshyam.exceptions.BoardSizeLimitExceededException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Board board;
    List<Player> players;
    List<WinningStrategy> winningStrategies;
    Player winner;

    public Game(Board board, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = board;
        this.players = players;
        this.winningStrategies = winningStrategies;
    }


    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public Player play(){
        int step = 0;
        Scanner sc = new Scanner(System.in);

        while(step<board.boardSize*board.boardSize && winner==null){
            int playerId = step%players.size();
            System.out.println("Turn of player "+players.get(playerId).getName()+". Enter the row and col");
            int row = sc.nextInt();
            int col = sc.nextInt();

            if(row<0 || row>=board.boardSize) try {
                throw new BoardSizeLimitExceededException("Board limit exceeded");
            } catch (BoardSizeLimitExceededException e) {
                e.printStackTrace();
                continue;
            }

            Cell cell = board.cellMatrix.get(row).get(col);
            if(cell.getPlayer()!=null) {
                System.out.println("This cell is already occupied by player "+cell.getPlayer()+" with symbol "+cell.getPlayer().symbol);
                System.out.println("Enter a valid position");
                continue;
            }

            cell.setPlayer(players.get(playerId));

            System.out.println("To undo the last step you can press u or else press any key");
            String command = sc.next();
            if(command.equalsIgnoreCase("u")){
                cell.setPlayer(null);
                System.out.println("step is undone, please make a new move");
                continue;
            }


            for(WinningStrategy strategy : winningStrategies){
                winner = strategy.findWinner(this,cell);
                if(winner!=null) return winner;
            }

            step++;
            printBoard();
        }
        return null;
    }

    public void printBoard(){
        List<List<Cell>> cellMatrix = board.cellMatrix;

        for(int i=0;i<cellMatrix.size();i++){
            for(int j=0;j<cellMatrix.get(i).size();j++){
                Cell cell = cellMatrix.get(i).get(j);
                if(cell.getPlayer()!=null)
                System.out.print(cell.getPlayer().getSymbol() + "  ");
                else
                System.out.print("-" + "  ");
            }
            System.out.println();
        }


    }

}
