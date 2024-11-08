package dev.ghanshyam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{
    List<Map<Player,Integer>> mapList;

    public ColWinningStrategy(int col) {
        mapList = new ArrayList<>();
        for(int i=0;i<col;i++){
            mapList.add(new HashMap<>());
        }
    }
    @Override
    public Player findWinner(Game game,Cell lastMoveCell) {
        Map<Player,Integer> colMap = mapList.get(lastMoveCell.getCol());
        Player player = lastMoveCell.getPlayer();
        colMap.put(player,colMap.getOrDefault(player,0)+1);
        if(colMap.get(player)==mapList.size()) return player;
        else return null;
    }
}
