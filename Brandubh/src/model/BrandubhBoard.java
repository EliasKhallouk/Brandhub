package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class BrandubhBoard extends GridElement {
    public BrandubhBoard(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 3x3 grid, named "Brandubhboard", and in x,y in space
        super("Brandubhboard", x, y, 7 , 7, gameStageModel);
        resetReachableCells(false);

    }



    public void setValidCells(int number) {
        resetReachableCells(false);
        List<Point> valid = computeValidCells(number ,this);
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.x][p.y] = true;
            }
        }
        lookChanged = true;
    }
    public List<Point> computeValidCells(int number, GridElement grid) {
            List<Point> lst = new ArrayList<>();
            int row;
            int col;
            int[] coord = new int[2];
            BrandubhBoard board = (BrandubhBoard) grid;
            coord = board.getCooPawn(number);
            row = coord[0];
            col = coord[1];
            System.out.println("row : "+row+" col : "+col);
            for(int i=row+1;i<7;i++){
                if(grid.isEmptyAt(i,col) || (grid.getElement(row,col) instanceof KingPawn && grid.getElement(i,col) instanceof Escape)){
                    grid.setCellReachable(i,col,true);
                    lst.add(new Point(i,col));
                }
                else{
                    //System.out.println("erreur row2");
                    break;                    }
            }
            for(int i=row-1;i>=0;i--){
                if(grid.isEmptyAt(i,col) || (grid.getElement(row,col) instanceof KingPawn && grid.getElement(i,col) instanceof Escape)){
                    grid.setCellReachable(i,col,true);
                    lst.add(new Point(i,col));
                }
                else{
                    //System.out.println("erreur row1");
                    break;
                }
            }

            for(int i=col+1;i<7;i++){
                if (grid.isEmptyAt(row, i) || (grid.getElement(row, col) instanceof KingPawn && grid.getElement(row, i) instanceof Escape)) {
                    grid.setCellReachable(row, i, true);
                    lst.add(new Point(row,i));
                }
                else{
                    //System.out.println("erreur col2");
                    break;
                }
            }
            for(int i=col-1;i>=0;i--) {
                if (grid.isEmptyAt(row, i) || (grid.getElement(row, col) instanceof KingPawn && grid.getElement(row, i) instanceof Escape)) {
                    grid.setCellReachable(row, i, true);
                    lst.add(new Point(row,i));
                } else {
                    //System.out.println("erreur col1 + i: " + i);
                    break;
                }
            }
            return lst;
        }


    BrandubhStageModel stageModel = (BrandubhStageModel)gameStageModel;

    public int[] getCooPawn(int number){
        int[] coo = new int[2];
        for (int i = 0 ; i<7;i++){
            for (int j = 0 ; j<7;j++){

                if (stageModel.getBoard().getElement(i,j) != null){

                    if (stageModel.getBoard().getElement(i,j) instanceof Pawn){
                        Pawn pawn = (Pawn)stageModel.getBoard().getElement(i,j);
                        if ( pawn.getNumber() == number){
                            coo[0] = i;
                            coo[1] = j;
                            return coo;
                        }
                    }
                }
            }

        }
        return coo;
    }
}
