package dev.ghanshyam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{
    List<Map<Player,Integer>> mapList;

    public RowWinningStrategy(int rows) {
        mapList = new ArrayList<>();
        for(int i=0;i<rows;i++){
            mapList.add(new HashMap<>());
        }
    }

    @Override
    public Player findWinner(Game game,Cell lastMoveCell) {
// instead of checking the entire board we can check the current row,col,diag,reverse diagnol in which the current cell is present
        Map<Player,Integer> rowMap = mapList.get(lastMoveCell.getRow());
        Player player = lastMoveCell.getPlayer();
        rowMap.put(player,rowMap.getOrDefault(player,0)+1);
        if(rowMap.get(player)==mapList.size()) return player;
        else return null;
    }
}
