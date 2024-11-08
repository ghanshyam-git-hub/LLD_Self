package dev.ghanshyam;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int boardSize;
    List<List<Cell>> cellMatrix;

    public Board(int boardSize) {
        this.boardSize = boardSize;

        cellMatrix = new ArrayList<List<Cell>>();

        for(int i=0;i<boardSize;i++){
            List<Cell>cellList = new ArrayList<>();
            for(int j=0;j<boardSize;j++){

                Cell cell = new Cell(i,j);
                cellList.add(cell);
            }
            cellMatrix.add(cellList);
        }
    }

}
