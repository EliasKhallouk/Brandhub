package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import boardifier.model.GridElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.stage.Stage;
import model.*;
import view.BrandubhRootPane;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BrandubhDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    public BrandubhDecider(Model model, Controller control) {
        super(model, control);
    }



    @Override
    public ActionList decide() {
        // do a cast get a variable of the real type to get access to the attributes of BrandubhStageModel
        BrandubhStageModel stage = (BrandubhStageModel)model.getGameStage();
        BrandubhBoard board = stage.getBoard(); // get the board
        GridElement grid = stage.getBoard();

        //View view = new View(model,stage1,rootPane);
        //BrandubhController cont = new BrandubhController(model,view);
        BrandubhController cont = (BrandubhController) control;
        int rowDep = 0;
        int colDep = 0;
        GameElement pawn = board.getElement(rowDep,colDep); // the pawn that is moved
        int rowDest = 0; // the dest. row in board
        int colDest = 0; // the dest. col in board



        if (model.getIdPlayer() == Pawn.PAWN_White) {


            // --------------------------------------------------  WHITE AI  -------------------------------------------------------------------------

            String posKing = cont.posKing(grid);  // King position
            int rowPions2, colPions2;
            boolean pawnCanEat = false;
            String controlcanEat;
            int rowControlCanEat;//row to control
            int colControlCanEat;//col to control
            int rowKing = posKing.charAt(0) - 48;     //ASCII TABLE CONVERSION
            int colKing = posKing.charAt(1) - 48;     //ASCII TABLE CONVERSION
            ArrayList<String> startPawn;

            grid.resetReachableCells(false); //RESET THE REACHABLE BOARD TO FALSE TO KNOW PLAYABLE POSSIBILITIES FOR NEXT MOVE
            cont.deplacementDispo(colKing, rowKing, grid); //gives the possibilities of the king
            boolean[][] reachableCellsCopy = new boolean[7][7]; //MAKE A CLONE OF REACHABLE CELLS FOR CURL ON IT
            for (int w = 0; w < 7; w++) {
                for (int q = 0; q < 7; q++) {
                    reachableCellsCopy[w][q] = grid.canReachCell(w, q);
                }
            }

            ArrayList<String> possiblePos1Moves = new ArrayList<>(); //list of position that makes the king win in 1 move
            ArrayList<String> possiblePos2Moves = new ArrayList<>(); //list of position that makes the king win in 2 moves
            cont.winIn1or2Move(possiblePos1Moves, possiblePos2Moves, grid, reachableCellsCopy, stage); //function that fills the list

            if (possiblePos1Moves.size() > 0) {
                System.out.println("--1 COUP--");
                pawn = board.getElement(rowKing, colKing); // the pawn that is moved
                rowDep = rowKing;
                colDep = colKing;
                rowDest = possiblePos1Moves.get(0).charAt(0) - 48;
                colDest = possiblePos1Moves.get(0).charAt(1) - 48;
                grid.removeElement(grid.getElement(rowDest, colDest));
            } else {
                cont.checkTable2Move(possiblePos2Moves, grid);
                startPawn = cont.storeWhitePawns(posKing, grid); // Store all white pawns in a list

                //CHECK IF A PIECE CAN EAT
                for (int i = 0; i < startPawn.size(); i++) {
                    rowPions2 = startPawn.get(i).charAt(0) - 48;//retrieve the coordinates of the pawns
                    colPions2 = startPawn.get(i).charAt(1) - 48;//retrieve the coordinates of the pawns
                    grid.resetReachableCells(false);
                    cont.deplacementDispo(colPions2, rowPions2, grid);

                    controlcanEat = cont.canEat(rowPions2, colPions2,grid); //see if the pawn can eat
                    rowControlCanEat = controlcanEat.charAt(0) - 48;
                    colControlCanEat = controlcanEat.charAt(1) - 48;

                    if (rowControlCanEat != 9 && colControlCanEat != 9) {   //if a white pawn can eat
                        System.out.println("--PEUT MANGER--");
                        pawn = board.getElement(rowPions2, colPions2); // the pawn that is moved
                        rowDep = rowPions2;
                        colDep = colPions2;
                        rowDest = rowControlCanEat;
                        colDest = colControlCanEat;
                        pawnCanEat = true;
                        break;
                    }
                }

                //if no white pawn can eat
                if (!pawnCanEat) {
                    System.out.println("--PEUT PAS MANGER--");
                    grid.resetReachableCells(false);
                    boolean isAlreadyPassed = false; //avoid infinite loops
                    boolean kingGoingEaten = false;
                    do {
                        int rowPions;
                        int colPions;
                        boolean pawnWillBeEaten = false;
                        for (int i = 0; i < startPawn.size(); i++) {
                            rowPions = startPawn.get(i).charAt(0) - 48;
                            colPions = startPawn.get(i).charAt(1) - 48;
                            if (!isAlreadyPassed) { //avoids the case where a pawn will be eaten if it does not move and if all these possibilities are false
                                if (cont.willBeEaten(rowPions, colPions, false, 'W', grid)) {
                                    //System.out.println("--VA ETRE MANGER--");
                                    pawn = board.getElement(rowPions, colPions); // the pawn that is moved
                                    rowDep = rowPions;
                                    colDep = colPions;
                                    pawnWillBeEaten = true;
                                    if (rowDep == rowKing && colDep == colKing) {
                                        kingGoingEaten = true;
                                    }
                                }
                            }
                        }
                        if (!pawnWillBeEaten) {     //if no pawn will be eaten
                            System.out.println("--VA PAS ETRE MANGER--");
                            if (possiblePos2Moves.size() > 0) {     //check if he can win in 2 moves
                                System.out.println("--2 COUP--");
                                pawn = board.getElement(rowKing, colKing); // the pawn that is moved
                                rowDep = rowKing;
                                colDep = colKing;
                                rowDest = possiblePos2Moves.get(0).charAt(0) - 48;
                                colDest = possiblePos2Moves.get(0).charAt(1) - 48;
                            } else {
                                if (startPawn.size() == 1) {    //if there is only one pawn (the king)
                                    //System.out.println("--PLUS DE PIONS--");
                                    pawn = board.getElement(rowKing, colKing); // the pawn that is moved
                                    rowDep = rowKing;
                                    colDep = colKing;
                                } else {
                                    do {
                                        //System.out.println("--DEPART ALÉATOIRE--");
                                        Collections.shuffle(startPawn);
                                        rowPions = startPawn.get(0).charAt(0) - 48;//retrieve the coordinates of the first pawn
                                        colPions = startPawn.get(0).charAt(1) - 48;//retrieve the coordinates of the first pawn
                                        pawn = board.getElement(rowPions, colPions); // the pawn that is moved
                                        rowDep = rowPions;
                                        colDep = colPions;
                                    } while (rowDep == rowKing && colDep == colKing);  //so as not to move the king randomly
                                }
                            }
                        }

                        // GO OVER THE TABLE OF ALL THE POSSIBILITIES OF THE STARTING PAWN AND CHECK IF HE CAN MAKE IT EAT
                        // AT THE OPPONENT'S NEXT MOVE IF YES HE REMOVES IT FROM THE TABLE (FALSE)
                        grid.resetReachableCells(false);
                        cont.deplacementDispo(colDep, rowDep, grid);
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 7; j++) {
                                if (grid.canReachCell(i, j)) {
                                    cont.willBeEaten(i, j, true, 'W', grid);
                                }
                            }
                        }
                        isAlreadyPassed = true;
                    } while (cont.nbTrueReachableCells(grid) == 0 && rowDep != rowKing && colDep != colKing); //redo as long as a pawn that will be eaten has more possibilities unless it is the king

                    if (rowDep == rowKing && colDep == colKing && kingGoingEaten == true && cont.nbTrueReachableCells(grid) == 0) { //if the king is going to be eaten and he has no possibility of play
                        //System.out.println("--ROI VA ETRE MANGER ET PLUS DE POSSIBILITER--");
                        String control = cont.cellAround(rowKing, colKing, 'W',grid);
                        int rowControl = control.charAt(0) - 48;
                        int colControl = control.charAt(1) - 48;
                        int rowInitial = 0; //starting pawn
                        int colInitial = 0;//starting pawn
                        boolean canSave = false;
                        for (int i = 0; i < startPawn.size(); i++) { //look for each pawn if it can go where a black must go to eat the king to save him
                            rowInitial = startPawn.get(i).charAt(0) - 48;
                            colInitial = startPawn.get(i).charAt(1) - 48;
                            if (cont.caseDispo(rowControl, colControl, rowInitial, colInitial, false, 'B', grid) == "manger") {
                                canSave = true;
                                break;
                            }
                        }

                        if (canSave) { //a pawn can save the king
                            //System.out.println("--PEUT SAUVER LE ROI--");
                            rowDep = rowInitial;
                            colDep = colInitial;
                        } else {
                            //the king is dead in any case so he is committing suicide
                            //System.out.println("--PEUT PAS SAUVER LE ROI --");
                            pawn = board.getElement(rowKing, colKing); // the pawn that is moved
                            rowDep = rowKing;
                            colDep = colKing;
                        }
                        rowDest = rowControl;
                        colDest = colControl;
                    } else if (rowDep == rowKing && colDep == colKing && possiblePos2Moves.size() > 0) {
                        //System.out.println("--2 COUP--");
                        rowDest = possiblePos2Moves.get(0).charAt(0) - 48;
                        colDest = possiblePos2Moves.get(0).charAt(1) - 48;
                    } else {
                        //System.out.println("--DESTINATION ALÉATOIRE--");
                        // Choose a random destination
                        int[] tRandomDests = cont.randomDests(grid);
                        colDest = tRandomDests[0];
                        rowDest = tRandomDests[1];
                    }
                }
            }

        }
        else {
            // --------------------------------------------------  BLACK AI  -------------------------------------------------------------------------
            String move = cont.chooseBestMove(cont.getAllPossibleMoves(grid),grid);
            //System.out.println("move : " + move);
            rowDep = move.charAt(0) - 48;
            colDep = move.charAt(1)- 48;
            rowDest = move.charAt(3)- 48;
            colDest = move.charAt(4)- 48;
            pawn = board.getElement(rowDep, colDep); // the pawn that is moved

        }


        // TO DO
        /*  1. win in 1 move OK
            2. eat an ennemy piece OK
            3. move an unsafe piece OK
            4. move the king in order to win in 2 moves OK
            5. take a random pawn and choose a safe random destination OK

        */



        // TO DO
        /*  1. search the closest corner for branan to win and move pawn to block it
            2. if not, focus on limiting the branan moves by approching the pawn close to it
        */


        // create action list. After the last action, it is next player's turn.
        ActionList actions = new ActionList(true);
        // get the dest. cell center in space.
        GridLook look = (GridLook) control.getElementLook(board);
        Coord2D center = look.getRootPaneLocationForCellCenter(rowDest, colDest);
        // create the move action
        GameAction move = new MoveAction(model, pawn, "Brandubhboard", rowDest, colDest, AnimationTypes.MOVE_LINEARPROP, center.getX(), center.getY(), 10);
        actions.addSingleAction(move);

        grid.moveElement(pawn, rowDest, colDest);
        if(pawn instanceof KingPawn && grid.getElement(rowDest, colDest) instanceof Escape){
            grid.removeElement(grid.getElement(rowDest, colDest));

        }
        ((BrandubhController)control).checkfinishwin(grid);

        grid.moveElement(pawn, rowDep, colDep);
        grid.resetReachableCells(false);


        return actions;
    }
}
