package dev.ghanshyam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnolWinningStrategy implements WinningStrategy {
    Map<Player,Integer> map1; //diagnol
    Map<Player,Integer> map2; // reverse diagnol
    public DiagnolWinningStrategy() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
    }

    public Player findWinner(Game game,Cell lastMoveCell) {
        int row = lastMoveCell.getRow();
        int col = lastMoveCell.getCol();

        if(row==col){
            Player player = lastMoveCell.getPlayer();
            map1.put(player,map1.getOrDefault(player,0)+1);
            if(map1.get(player)==game.getBoard().boardSize) return player;
        }

        if(row+col==game.getBoard().boardSize-1){
            Player player = lastMoveCell.getPlayer();
            map2.put(player,map2.getOrDefault(player,0)+1);
            if(map2.get(player)==game.getBoard().boardSize) return player;
        }
        return null;
    }
}
