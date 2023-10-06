package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GameStageView;
import boardifier.view.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import model.*;
import view.BrandubhRootPane;
import view.BrandubhView;
import view.ThroneLook;

import java.awt.*;
import java.util.Optional;
import java.util.Scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrandubhController extends Controller {

    final static Scanner input = new Scanner(System.in);

    public int mode;

    Model model;

    BrandubhView view2;


    public void setMode(int mode) {
        this.mode = mode;
    }




    public BrandubhController(Model model, View view,int mode) {
        super(model, view);
        setControlKey(new BrandubhControllerKey(model, view, this));
        setControlMouse(new BrandubhControllerMouse(model, view, this));
        setControlAction (new BrandubhControllerAction(model, view, this));
        this.mode = mode;
        this.model = model;
    }



    public void trysetmode() {
        try {
            model.getPlayers().clear();
            if(mode <= 3 && mode >= 0){
                if (mode == 0) {
                    model.addHumanPlayer("player1");
                    model.addHumanPlayer("player2");
                } else if (mode == 1) {
                    model.addHumanPlayer("player");
                    model.addComputerPlayer("computer");
                } else if (mode == 2) {
                    model.addComputerPlayer("computer");
                    model.addHumanPlayer("player1");

                } else if (mode == 3) {
                    model.addComputerPlayer("computer1");
                    model.addComputerPlayer("computer2");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void nextPlayer() {
        verifThrone(model.getGrid("Brandubhboard"),(BrandubhStageModel) model.getGameStage());

        // use the default method to compute next player
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        BrandubhStageModel stageModel = (BrandubhStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            BrandubhDecider decider = new BrandubhDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
        }
    }

    /**
     * Check coordinates of the king
     * @param grid
     * @return coo : coordinates of the king
     */
    public int[] cooOfKing(GridElement grid){
        int[] coo = new int[2];
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                if(grid.getElement(i,j) instanceof KingPawn){
                    coo[0]=i;
                    coo[1]=j;
                }
            }
        }
        return coo;
    }


    /**
     * Ask the mode of the game to the user
     * 0 : Human vs Human
     * 1 : Human vs Computer
     * 2 : Computer vs Human
     * 3 : Computer vs Computer
     * @return mode
     *
     */
    public static int askMode(){
        System.out.println("Choose the mode of the game : ");
        System.out.println("0 : Human vs Human");
        System.out.println("1 : Human vs Computer");
        System.out.println("2 : Computer vs Human");
        System.out.println("3 : Computer vs Computer");
        int mode = input.nextInt();
        return mode;

    }


    /** Check if the pawn have a neighbour of the opposite color
     * @param row
     * @param col
     * @param grid
     * @return res : String with the direction of the neighbour
     */
    public String verifDuel(int row, int col, GridElement grid){
        String res= ""; /*sud nord ouest est*/
        if(grid.getElement(row,col) instanceof WhithPawn) {
            if (row+1>=0 && row+1<=6 && grid.getElement(row + 1, col) instanceof BlackSoldier) {
                res = res + 'Y';
            } else {
                res = res + 'N';
            }
            if (row-1>=0 && row-1<=6 && grid.getElement(row - 1, col) instanceof BlackSoldier) {
                res = res + 'Y';
            } else {
                res = res + 'N';
            }
            if (col+1>=0 && col+1<=6 && grid.getElement(row, col + 1) instanceof BlackSoldier) {
                res = res + 'Y';
            } else {
                res = res + 'N';
            }
            if (col-1>=0 && col-1<=6 && grid.getElement(row, col - 1) instanceof BlackSoldier) {
                res = res + 'Y';
            } else {
                res = res + 'N';
            }

        }
        else if(grid.getElement(row,col) instanceof BlackSoldier) {
            if (row+1>=0 && row+1<=6 && grid.getElement(row + 1, col) instanceof WhithPawn) {
                if (grid.getElement(row+1,col) instanceof KingPawn && ((row+1 == 3 && col == 3) || (row+1 == 2 && col == 3) || (row+1 == 3 && col == 2) || (row+1 == 4 && col == 3) || (row+1 == 3 && col == 4))){
                    res = res + 'R';
                }
                else {
                    res = res + 'Y';
                }
            } else {
                res = res + 'N';
            }
            if (row-1>=0 && row-1<=6 && grid.getElement(row - 1, col) instanceof WhithPawn) {
                if (grid.getElement(row-1,col) instanceof KingPawn && ((row-1 == 3 && col == 3) || (row-1 == 2 && col == 3) || (row-1 == 3 && col == 2) || (row-1 == 4 && col == 3) || (row-1 == 3 && col == 4))){
                    res = res + 'R';
                }
                else {
                    res = res + 'Y';
                }
            } else {
                res = res + 'N';
            }
            if (col+1>=0 && col+1<=6 && grid.getElement(row, col + 1) instanceof WhithPawn) {
                if (grid.getElement(row,col+1) instanceof KingPawn && ((row == 3 && col+1 == 3) || (row == 2 && col+1 == 3) || (row == 3 && col+1 == 2) || (row == 4 && col+1 == 3) || (row == 3 && col+1 == 4))){
                    res = res + 'R';
                }
                else {
                    res = res + 'Y';
                }
            } else {
                res = res + 'N';
            }
            if (col-1>=0 && col-1<=6 && grid.getElement(row, col - 1) instanceof WhithPawn) {
                if (grid.getElement(row,col-1) instanceof KingPawn && ((row == 3 && col-1 == 3) || (row == 2 && col-1 == 3) || (row == 3 && col-1 == 2) || (row == 4 && col-1 == 3) || (row == 3 && col-1 == 4 ))){
                    res = res + 'R';
                }
                else {
                    res = res + 'Y';
                }
            } else {
                res = res + 'N';
            }
        }
        else {res = "NNNN";}
        return res;
    }

    /**
     * Check if the king is on the throne
     * @param grid
     * @param s StageModel
     */
    // TODO: 31/05/2023

    /**
     * Check if the king is on an escape point
     * @param grid
     * @return true if the king is on an escape point
     */
    public int verifKingWin(GridElement grid){
        if (grid.getElement(0, 0) instanceof KingPawn || grid.getElement(0, 6) instanceof KingPawn
             || grid.getElement(6, 0) instanceof KingPawn || grid.getElement(6, 6) instanceof KingPawn) {
        //if((row==0 && col==0) || (row==0 && col==6) || (row==6 && col==0) || (row==6 && col==6)){

            System.out.println("Victory of the king");
            model.setIdWinner(1);
            return 1;
        }
        else
            return 0;
    }

    /**
     * Returns the position of the king pawn on the game board
     * @param grid the game board on which to find the king pawn
     * @return a string representing the position of the king pawn in the format "row column"
     */
    public  String posKing(GridElement grid){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(grid.getElement(i,j) instanceof KingPawn) {
                    return i+""+j;
                }
            }
        }
        return "";
    }
    // --------------------------------------------------  Function  AI Black   -------------------------------------------------------------------------
    public int[][] boardValues = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 1},
            {1, 2, 3, 5, 3, 2, 1},
            {1, 2, 5, 6, 5, 2, 1},
            {1, 2, 3, 5, 3, 2, 1},
            {1, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1}
    };


    /**
     * Evaluates the score of the game board based on the current state of the grid.
     *
     * @param grid      The game grid.
     * @param coldest   The column index of the destination position.
     * @param rowdest   The row index of the destination position.
     * @param colsource The column index of the source position.
     * @param rowsource The row index of the source position.
     * @return The score of the game board.
     */
    public int evaluateBoard(GridElement grid,int coldest,int rowdest,int colsource,int rowsource) {
        updateBoardValues(grid);
        int score = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(i == rowsource && j == colsource){
                    score+=boardValues[rowdest][coldest];

                }
                else{
                    if ((grid.getElement(i,j) instanceof BlackSoldier)) {
                        score += boardValues[i][j];
                    }
                }
            }
        }
        return score;
    }

    /**
     * Updates the board values based on the current state of the game grid.
     *
     * @param grid The game grid.
     */
    public void updateBoardValues(GridElement grid) {
        // Reset boardValues to a neutral state
        boardValues = new int[7][7];

        // Get the king's position
        String kingPosition = posKing(grid);
        int kingposition_i = kingPosition.charAt(0) - 48;
        int kingposition_j = kingPosition.charAt(1) - 48;

        // Update the values around the king, the closer to the king, the higher the value
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                int distance = Math.abs(kingposition_i - i) + Math.abs(kingposition_j - j);

                if (i == kingposition_i - 1 && j == kingposition_j) { // position just above the king
                    if (kingposition_i + 1 >= 0 && kingposition_i + 1 < 7 && grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier) {
                        boardValues[i][j] = 6; // Higher value if there is a pawn below the king
                    } else {
                        boardValues[i][j] = 5;
                    }
                } else if (i == kingposition_i + 1 && j == kingposition_j) { // position just below the king
                    if (kingposition_i - 1 >= 0 && kingposition_i - 1 < 7 && grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier) {
                        boardValues[i][j] = 6; // Higher value if there is a pawn above the king
                    } else {
                        boardValues[i][j] = 5;
                    }
                } else if (i == kingposition_i && j == kingposition_j - 1) { // position to the left of the king
                    if (kingposition_j + 1 >= 0 && kingposition_j + 1 < 7 && grid.getElement(kingposition_i, kingposition_j + 1) instanceof BlackSoldier) {
                        boardValues[i][j] = 6; // Higher value if there is a pawn to the right of the king
                    } else {
                        boardValues[i][j] = 5;
                    }
                } else if (i == kingposition_i && j == kingposition_j + 1) { // position to the right of the king
                    if (kingposition_j - 1 >= 0 && kingposition_j - 1 < 7 && grid.getElement(kingposition_i, kingposition_j - 1) instanceof BlackSoldier) {
                        boardValues[i][j] = 6; // Higher value if there is a pawn to the left of the king
                    } else {
                        boardValues[i][j] = 5;
                    }
                } else {
                    boardValues[i][j] = Math.max(0, 5 - distance);
                }
            }
        }
    }


    /**
     * Retrieves all positions where a black soldier can make a capture on the game grid.
     *
     * @param grid The game grid.
     * @return A list of positions where a black soldier can make a capture.
     */
    public List<String> getallPosBlackCanKill(GridElement grid) {
        List<String> allCaptureMoves = new ArrayList<>();
        List<String> allPossibleMoves = getAllPossibleMoves(grid);
        for (String move : allPossibleMoves) {
            if (isCaptureMove(grid, move)) {
                allCaptureMoves.add(move);
                return allCaptureMoves;
            }
        }
        return allCaptureMoves;
    }



    public boolean isCaptureMove(GridElement grid, String move) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        // The destination position of the move
        int rowsource = move.charAt(0) - 48;
        int colsource = move.charAt(1) - 48;
        int rowdest = move.charAt(3) - 48;
        int coldest = move.charAt(4) - 48;

        for (int[] direction : directions) {
            int newRow = rowdest + direction[0];
            int newCol = coldest + direction[1];
            if (newRow >= 0 && newRow < 7 && newCol >= 0 && newCol < 7) {
                if (grid.getElement(newRow, newCol) instanceof WhitSoldier) {
                    int captureRow = newRow + direction[0];
                    int captureCol = newCol + direction[1];

                    if (captureRow >= 0 && captureRow < 7 && captureCol >= 0 && captureCol < 7) {
                        if (grid.getElement(captureRow, captureCol) instanceof BlackSoldier ||
                                grid.getElement(captureRow, captureCol) instanceof CaseSpecial) {
                            grid.removeElement(grid.getElement(newRow, newCol));
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    /**

     Retrieves all possible moves for the current player on the game grid.
     @param grid The game grid.
     @return A list of all possible moves.
     */
    public List<String> getAllPossibleMoves(GridElement grid) {
        List<String> allPossibleMoves = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                grid.resetReachableCells(false);
                if(grid.getElement(i,j) instanceof BlackSoldier){
                    deplacementDispo(j, i, grid);
                    for(int test_i = 0;test_i < 7; test_i++){
                        for(int test_j = 0; test_j < 7; test_j++) {
                            if (grid.canReachCell(test_i, test_j)) {
                                if (grid.getElement(test_i, test_j) == null) {
                                    allPossibleMoves.add(i+""+j+"|"+test_i + "" + test_j); // Add position to list of positions where player can win in one move
                                }
                            }
                        }
                    }
                }
            }
        }
        return allPossibleMoves;
    }

    public String KingCanWinOneMove(GridElement grid,int kingposition_i, int kingposition_j ){
        List<String> allPossibleMoves = getAllPossibleMoves(grid);
        String Move = null;
        String EscapetoWin = null;

        grid.resetReachableCells(false);
        deplacementDispo(kingposition_i,kingposition_j,grid);

        for(int test_i = 0;test_i < 7; test_i++){
            for(int test_j = 0; test_j < 7; test_j++) {
                if (grid.canReachCell(test_i, test_j)) {
                    if (grid.getElement(test_i, test_j) instanceof Escape) {
                        EscapetoWin = test_i + "" + test_j; // Add position to list of positions where player can win in one move
                    }
                }
            }
        }

        for(String possiblemove : allPossibleMoves  ){
            if(EscapetoWin == "06"){
                if(kingposition_i == 0 ){
                    if (possiblemove == "05") {
                        Move = possiblemove;
                    }
                } else if ( kingposition_j == 6) {
                    if(possiblemove == "16"){
                        Move = possiblemove;
                    }
                }
            } else if (EscapetoWin == "00") {
                if(kingposition_i == 0 ){
                    if (possiblemove == "01") {
                        Move = possiblemove;
                    }
                } else if ( kingposition_j == 0) {
                    if(possiblemove == "10"){
                        Move = possiblemove;
                    }
                }
            } else if (EscapetoWin == "66") {
                if(kingposition_i == 6 ){
                    if (possiblemove == "65") {
                        Move = possiblemove;
                    }
                } else if ( kingposition_j == 6) {
                    if(possiblemove == "56"){
                        Move = possiblemove;
                    }
                }
            } else if (EscapetoWin == "60") {
                if(kingposition_i == 6 ){
                    if (possiblemove == "61") {
                        Move = possiblemove;
                    }
                } else if ( kingposition_j == 0) {
                    if(possiblemove == "50"){
                        Move = possiblemove;
                    }
                }
            }
        }

        return Move;

    }

    public String KingCanWinTwoMove(GridElement grid){
        String Move = null;
        List<String> allPossibleMoves = getAllPossibleMoves(grid);
        String moveneed = null;
        String EscapetoWin = null;
        // Get the king's position
        String kingPosition = posKing(grid);
        int kingposition_i = kingPosition.charAt(0) - 48;
        int kingposition_j = kingPosition.charAt(1) - 48;

        grid.resetReachableCells(false);
        deplacementDispo(kingposition_i,kingposition_j,grid);

        for(int test_i = 0;test_i < 7; test_i++){
            for(int test_j = 0; test_j < 7; test_j++) {
                if (grid.canReachCell(test_i, test_j)) {
                    KingPawn kingVirtual = new KingPawn(model.getGameStage(),12); // Create king copy
                    grid.putElement(kingVirtual, test_i, test_j);     // Put it on the board
                    if(KingCanWinOneMove(grid,test_i,test_j) != null){
                        moveneed = test_i + "" + test_j;
                        EscapetoWin = KingCanWinOneMove(grid,test_i,test_j);

                    }
                    grid.removeElement(kingVirtual);    // Delete king copy
                }
            }
            if(moveneed != null){
                break;
            }
        }
        if( moveneed != null) {
            int kingdestRow = moveneed.charAt(0) - 48;
            int kingdestCol = moveneed.charAt(1) - 48;

            int escapeRow = EscapetoWin.charAt(0) - 48;
            int escpaeCol = EscapetoWin.charAt(1) - 48;

            for (String move : allPossibleMoves) {

                int PawnDestRow = move.charAt(0) - 48;
                int PawnDestCol = move.charAt(1) - 48;

                if ((PawnDestRow >= Math.min(kingdestRow, escapeRow) && PawnDestRow <= Math.max(kingdestRow, escapeRow)) &&
                        (PawnDestCol >= Math.min(kingdestCol, escpaeCol) && PawnDestCol <= Math.max(kingdestCol, escpaeCol))) {
                    return move;
                }
            }
        }

        return Move;
    }

    public String canKillKing(GridElement grid) {
        String kingPosition = posKing(grid);
        int kingPosition_i = kingPosition.charAt(0) - '0';
        int kingPosition_j = kingPosition.charAt(1) - '0';

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] direction : directions) {
            int adjacentRow = kingPosition_i + direction[0];
            int adjacentCol = kingPosition_j + direction[1];
            int oppositeRow = kingPosition_i - direction[0];
            int oppositeCol = kingPosition_j - direction[1];

            if (adjacentRow >= 0 && adjacentRow < 7 && adjacentCol >= 0 && adjacentCol < 7 &&
                    oppositeRow >= 0 && oppositeRow < 7 && oppositeCol >= 0 && oppositeCol < 7) {

                GameElement adjacentElement = grid.getElement(adjacentRow, adjacentCol);
                GameElement oppositeElement = grid.getElement(oppositeRow, oppositeCol);

                if ((adjacentElement instanceof BlackSoldier || adjacentElement instanceof CaseSpecial  ) && oppositeElement == null) {
                    List<String> allPossibleMoves = getAllPossibleMoves(grid);

                    for (String move : allPossibleMoves) {
                        int pawnDestRow = move.charAt(3) - 48;
                        int pawnDestCol = move.charAt(4) - 48;

                        if (pawnDestCol == oppositeCol && pawnDestRow == oppositeRow) {
                            return move;
                        }
                    }

                }
            }
        }

        return null;
    }


    /**
     * Chooses the best move from a list of possible moves.
     *
     * @param possibleMoves The list of possible moves.
     * @param grid The game grid.
     * @return The best move to make.
     */
    public String chooseBestMove(List<String> possibleMoves,GridElement grid) {
        String bestMove = null;
        int bestScore = 0;
        List<String> cankill = getallPosBlackCanKill(grid);
        String kingPosition = posKing(grid);
        if(canKillKing(grid) != null){
            return canKillKing(grid);
        }
        else if (KingCanWinOneMove(grid, (kingPosition.charAt(0) - 48), kingPosition.charAt(1) - 48) != null) {
            return KingCanWinOneMove(grid, (kingPosition.charAt(0) - 48), kingPosition.charAt(1) - 48);
        }
        // If the king can win in two moves, return the corresponding move
        else if (KingCanWinTwoMove(grid) != null) {
            return KingCanWinTwoMove(grid);
        }
        else if( cankill.size() != 0){
            bestMove = cankill.get(0);
            return bestMove;
        }
        else{
            for (String move : possibleMoves) {
                int rowsource = move.charAt(0) - 48;
                int colsource = move.charAt(1)- 48;
                int rowdest = move.charAt(3)- 48;
                int coldest = move.charAt(4)- 48;
                int score = evaluateBoard(grid,coldest,rowdest,colsource,rowsource);
                if (!willBeEaten(rowdest, coldest, false, 'B', grid)) {
                    if (score > bestScore) {
                        bestMove = move;
                        bestScore = score;
                    }
                }
            }
        }

        return bestMove;
    }





    // --------------------------------------------------  Function  AI Black end  -------------------------------------------------------------------------


    public  void getAllReachableCells(GridElement grid) {
        boolean[][] reachableCells = grid.getReachableCells();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(reachableCells[i][j] + "\t");
            }
            System.out.println("");
        }
    }


    /**
     * Calculates the number of reachable cells in the current state of the grid.
     * @param grid The grid containing the current state of the game.
     * @return The number of reachable cells.
     */
    public  int nbTrueReachableCells(GridElement grid) {
        boolean[][] reachableCells = grid.getReachableCells();
        int cpt=0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(reachableCells[i][j]==true){
                    cpt+=1;
                }
            }
        }
        return cpt;
    }


    /**
     * Checks if there is a possibility of capturing an opponent's piece from a given position on the game board.
     *
     * @param rowControl   The row index of the position to check.
     * @param colControl   The column index of the position to check.
     * @param rowInitial   The row index of the initial position.
     * @param colInitial   The column index of the initial position.
     * @param reel         A boolean flag indicating whether the check is part of a real move or not.
     * @param color        The color of the piece ('W' for white, 'B' for black).
     * @param grid         The GridElement representing the game board.
     * @return A string indicating whether a capture move is possible ('manger') or not ('pas manger').
     */
    public  String caseDispo(int rowControl, int colControl, int rowInitial, int colInitial, boolean reel, char color, GridElement grid){
        boolean nord = false;
        boolean sud = false;
        boolean est = false;
        boolean ouest = false;
        boolean manger = false;
        if(rowControl>rowInitial){
            sud = true;
            est = true;
            ouest = true;
        } else if (rowControl<rowInitial) {
            nord = true;
            est = true;
            ouest = true;
        } else if (colControl>colInitial) {
            est = true;
            sud = true;
            nord = true;
        } else if (colControl<colInitial) {
            ouest = true;
            sud = true;
            nord = true;
        }

        if(color=='W') {
            if (grid.isEmptyAt(rowControl, colControl)) {
                if (sud == true) {  //verifie que dans la direction sud pour pas retomber sur la case qui deja a coter de lui
                    for (int i = rowControl; i < 6; i++) {
                        if (grid.getElement(i + 1, colControl) instanceof BlackSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(i, colControl) == false) {
                            break;
                        }
                    }
                }
                if (nord == true) {
                    for (int i = rowControl; i > 0; i--) {
                        if (grid.getElement(i - 1, colControl) instanceof BlackSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(i, colControl) == false) {
                            break;
                        }
                    }
                }
                if (est == true) {
                    for (int j = colControl; j < 6; j++) {
                        if (grid.getElement(rowControl, j + 1) instanceof BlackSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(rowControl, j) == false) {
                            break;
                        }
                    }
                }
                if (ouest == true) {
                    for (int j = colControl; j > 0; j--) {
                        if (grid.getElement(rowControl, j - 1) instanceof BlackSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(rowControl, j) == false) {
                            break;
                        }
                    }
                }
                if (manger == true) {
                    return "manger";
                }
            }
            return "pas manger";
        }
        else {
            if(grid.isEmptyAt(rowControl,colControl)) {
                if (sud == true) {  //verifie que dans la direction sud pour pas retomber sur la case qui deja a coter de lui
                    for (int i = rowControl; i < 6; i++) {
                        if (grid.getElement(i + 1, colControl) instanceof WhitSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(i, colControl) == false) {
                            break;
                        }
                    }
                }
                if (nord == true) {
                    for (int i = rowControl; i > 0; i--) {
                        if (grid.getElement(i - 1, colControl) instanceof WhitSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(i, colControl) == false) {
                            break;
                        }
                    }
                }
                if (est == true) {
                    for (int j = colControl; j < 6; j++) {
                        if (grid.getElement(rowControl, j + 1) instanceof WhitSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(rowControl, j) == false) {
                            break;
                        }
                    }
                }
                if (ouest == true) {
                    for (int j = colControl; j > 0; j--) {
                        if (grid.getElement(rowControl, j - 1) instanceof BlackSoldier) {
                            if (reel == true) {
                                grid.setCellReachable(rowInitial, colInitial, false);
                            }
                            manger = true;
                            break;
                        } else if (grid.isEmptyAt(rowControl, j) == false) {
                            break;
                        }
                    }
                }
                if (manger == true) {
                    return "manger";
                }
            }
            return "pas manger";
        }
    }

    /**
     * This method checks the adjacent cells around a given cell to find a neighboring cell that is BlackSoldier or Escape or Throne.
     * @param row the row of the cell to check
     * @param col the column of the cell to check
     * @param grid the game board
     * @return If found, the method returns the coordinates of the opposite cell with respect to the adjacent cell. If not found, the method returns "99"
     */
    public  String cellAround(int row, int col, char color, GridElement grid){
        if(color=='W') {
            //Condition series to look at the 4 boxes around him
            if (row < 6) {        //    so as not to count the edges
                if ((grid.getElement(row + 1, col) instanceof BlackSoldier || grid.getElement(row + 1, col) instanceof Escape || grid.getElement(row + 1, col) instanceof Throne) && (row-1>=0)) {
                    row -= 1;
                    return row + "" + col;         //It is necessary to check the box opposing to the adjacent box
                }
            }
            if (col < 6) {
                if ((grid.getElement(row, col + 1) instanceof BlackSoldier || grid.getElement(row, col + 1) instanceof Escape || grid.getElement(row, col + 1) instanceof Throne) && (col-1>=0)) {
                    col -= 1;
                    return row + "" + col;
                }
            }
            if (row > 0) {
                if ((grid.getElement(row - 1, col) instanceof BlackSoldier || grid.getElement(row - 1, col) instanceof Escape || grid.getElement(row - 1, col) instanceof Throne) && (row+1<7)) {
                    row += 1;
                    return row + "" + col;
                }
            }
            if (col > 0) {
                if ((grid.getElement(row, col - 1) instanceof BlackSoldier || grid.getElement(row, col - 1) instanceof Escape || grid.getElement(row, col - 1) instanceof Throne) && (col+1<7)){
                    col += 1;
                    return row + "" + col;
                }
            }
        }else{
            if (row < 6) {
                if ((grid.getElement(row + 1, col) instanceof WhithPawn || grid.getElement(row + 1, col) instanceof Escape || grid.getElement(row + 1, col) instanceof Throne) && (row-1>=0)){
                    row -= 1;
                    return row + "" + col;
                }
            }
            if (col < 6) {
                if ((grid.getElement(row, col + 1) instanceof WhithPawn || grid.getElement(row, col + 1) instanceof Escape || grid.getElement(row, col + 1) instanceof Throne) && (col-1>0)) {
                    col -= 1;
                    return row + "" + col;
                }
            }
            if (row > 0) {
                if ((grid.getElement(row - 1, col) instanceof WhithPawn || grid.getElement(row - 1, col) instanceof Escape || grid.getElement(row - 1, col) instanceof Throne ) && (row+1<7)) {
                    row += 1;
                    return row + "" + col;
                }
            }
            if (col > 0) {
                if ((grid.getElement(row, col - 1) instanceof BlackSoldier || grid.getElement(row, col - 1) instanceof Escape || grid.getElement(row, col - 1) instanceof Throne)&& (col+1<7)) {
                    col += 1;
                    return row + "" + col;
                }
            }
        }
        row=9;
        col=9;
        return row+""+col;
    }

    /**
     * Determines if a given pawn at a certain position is going to be eaten by an opponent's pawn in the next move.
     * @param row the row position of the pawn
     * @param col the column position of the pawn
     * @param reel indicates whether or not to modify the table of possibilities
     * @param color the color of the pawn being checked
     * @param grid the game board
     * @return true if the pawn is going to be eaten in the next move, false otherwise
     */
    public boolean willBeEaten(int row, int col, boolean reel,char color, GridElement grid){
        String control;
        int rowControl;//case a controler
        int colControl;//case a controler

        control = cellAround(row, col, color, grid);
        rowControl = control.charAt(0) - 48;
        colControl = control.charAt(1) - 48;

        if (rowControl != 9 && colControl != 9) {  // won't check if he can get eaten since there is no square next to him
            if(caseDispo(rowControl, colControl, row, col, reel, color, grid)=="manger"){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * Determines if a white pawn can eat a black pawn.
     * @param row The row of the white pawn
     * @param col The column of the white pawn
     * @param grid The game board
     * @return The position of the white pawn if it can eat a black pawn, or 99 if it can't
     */
    public String canEat(int row, int col , GridElement grid){
        String control;
        int rowControl;
        int colControl;

        int i;
        int j;
        for(i=0;i<7;i++){
            for(j=0;j<7;j++){
                if(grid.canReachCell(i,j)) {
                    control = cellAround(i, j,'W',grid);
                    rowControl = control.charAt(0) - 48;
                    colControl = control.charAt(1) - 48;
                    //System.out.println("control"+control);
                    if (rowControl == 9 && colControl == 9) {
                        continue;
                    } else {
                        if (i <= 4) {
                            if (rowControl < i) {
                                if ((grid.getElement(i + 2, j) instanceof WhithPawn || grid.getElement(i + 2, j) instanceof Escape || grid.getElement(i + 2, j) instanceof Throne) && grid.getElement(i + 1, j) instanceof BlackSoldier){
                                    grid.removeElement(grid.getElement(i + 1, j));
                                    return i + "" + j;
                                }
                            }
                        }
                        if (i >= 2) {
                            if (rowControl > i) {
                                if ((grid.getElement(i - 2, j) instanceof WhithPawn || grid.getElement(i - 2, j) instanceof Escape || grid.getElement(i - 2, j) instanceof Throne) && grid.getElement(i - 1, j) instanceof BlackSoldier) {
                                    grid.removeElement(grid.getElement(i - 1, j));
                                    return i + "" + j;
                                }
                            }
                        }
                        if (j <= 4) {
                            if (colControl < j) {
                                if ((grid.getElement(i, j + 2) instanceof WhithPawn || grid.getElement(i, j + 2) instanceof Escape || grid.getElement(i, j + 2) instanceof Throne) && grid.getElement(i, j + 1) instanceof BlackSoldier) {
                                    grid.removeElement(grid.getElement(i, j + 1));
                                    return i + "" + j;
                                }
                            }
                        }
                        if (j >= 2) {
                            if (colControl > j) {
                                if ((grid.getElement(i, j - 2) instanceof WhithPawn || grid.getElement(i, j - 2) instanceof Escape || grid.getElement(i, j - 2) instanceof Throne) && grid.getElement(i, j - 1) instanceof BlackSoldier) {
                                    grid.removeElement(grid.getElement(i, j - 1));
                                    return i + "" + j;
                                }
                            }
                        }
                    }
                }
            }
        }
        i=9;
        j=9;
        return i+""+j;
    }

    /*public boolean canEat(int row, int col, int rowControl, int colControl, GridElement grid){
        if(grid.getElement(row,col) instanceof BlackSoldier){
            if(direction == 1){
                if(row+2<7 && (grid.getElement(row+2,col) instanceof BlackSoldier || grid.getElement(row+2,col) instanceof CaseSpecial)){
                    return true;
                }
            }
            else if(direction == 2){
                if(row-2>=0 && (grid.getElement(row-2,col) instanceof BlackSoldier || grid.getElement(row-2,col) instanceof CaseSpecial)){
                    return true;
                }
            }
            else if(direction == 3){
                if(col+2<7 &&  (grid.getElement(row,col+2) instanceof BlackSoldier || grid.getElement(row,col+2) instanceof CaseSpecial)){
                    return true;
                }
            }
            else if(direction == 4){
                if(col-2>=0 && (grid.getElement(row,col-2) instanceof BlackSoldier || grid.getElement(row,col-2) instanceof CaseSpecial)){
                    return true;
                }
            }
        }
        if(grid.getElement(row,col) instanceof WhitSoldier){
            //System.out.println("test");
            if(direction == 1){
                if(row+2<7 && (grid.getElement(row+2,col) instanceof WhitSoldier || grid.getElement(row+2,col) instanceof CaseSpecial)) {
                    pawn = (Pawn) grid.getElement(row+1,col);

                }
            }
            else if(direction == 2){
                if(row-2>=0 && (grid.getElement(row-2,col) instanceof WhitSoldier || grid.getElement(row-2,col) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row-1,col);
                }
            }
            else if(direction == 3){
                if(col+2<7 && (grid.getElement(row,col+2) instanceof WhitSoldier || grid.getElement(row,col+2) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row,col+1);
                }
            }
            else if(direction == 4){
                if(col-2>=0 && (grid.getElement(row,col-2) instanceof WhitSoldier || grid.getElement(row,col-2) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row,col-1);
                }
            }
        }
    }*/




    /**
     * Determines the available moves for a piece located at a given row and column on the game board.
     *
     * @param col   The column index of the piece's position.
     * @param row   The row index of the piece's position.
     * @param grid  The GridElement representing the game board.
     */
    public void deplacementDispo(int col, int row, GridElement grid){

        for(int i=row+1;i<7;i++){
            if(grid.isEmptyAt(i,col) || (grid.getElement(row,col) instanceof KingPawn && grid.getElement(i,col) instanceof Escape)){
                grid.setCellReachable(i,col,true);
            }
            else{
                //System.out.println("erreur row2");
                break;                    }
        }
        for(int i=row-1;i>=0;i--){
            if(grid.isEmptyAt(i,col) || (grid.getElement(row,col) instanceof KingPawn && grid.getElement(i,col) instanceof Escape)){
                grid.setCellReachable(i,col,true);
            }
            else{
                //System.out.println("erreur row1");
                break;
            }
        }

        for(int i=col+1;i<7;i++){
            if (grid.isEmptyAt(row, i) || (grid.getElement(row, col) instanceof KingPawn && grid.getElement(row, i) instanceof Escape)) {
                grid.setCellReachable(row, i, true);
            }
            else{
                //System.out.println("erreur col2");
                break;
            }
        }
        for(int i=col-1;i>=0;i--) {
            if (grid.isEmptyAt(row, i) || (grid.getElement(row, col) instanceof KingPawn && grid.getElement(row, i) instanceof Escape)) {
                grid.setCellReachable(row, i, true);
            } else {
                //System.out.println("erreur col1 + i: " + i);
                break;
            }
        }

    }





    //il faut vérifier que les pions peuvent se déplacer veritcalement ou horizontalements
    /**
     * Verifies if a move from one position to another is valid on the game board.
     *
     * @param col   The column index of the starting position.
     * @param row   The row index of the starting position.
     * @param col2  The column index of the destination position.
     * @param row2  The row index of the destination position.
     * @param grid  The GridElement representing the game board.
     * @return      True if the move is valid, False otherwise.
     */
    public boolean verfiDepla(int col, int row, int col2, int row2,GridElement grid){
        grid.resetReachableCells(false);
        //setCellsReachable pour mettre les cases dispos
        //canReachable a la fin IL FAUT LE FAIRE A TOUT PRIX ET AVOIR TOUTES LES CASES

        if(col==col2 && row==row2){
            return false;
        }
        else if(col!=col2 && row!=row2){
            System.out.println("test erreur");
            return false;
        }
        deplacementDispo(col,row,grid);
        /*boolean [][] o = grid.getReachableCells();

                for(int i=0; i<7; i++){
                    for(int y=0; y<7;y++){
                        System.out.print("\t"+o[i][y]);
                    }
                    System.out.println("");
                }
        */
        return grid.canReachCell(row2,col2);
    }

    /**
     * check if there is a comrade (soldier of same color or specials cases) in the direction
     * to eat an enemy pawn
     * @param direction 1: bottom, 2: top, 3: right, 4: left
     * @param row
     * @param col
     * @param grid
     */
    public void checkComrade(int direction, int row, int col, GridElement grid){
        Pawn pawn = null;
        if(grid.getElement(row,col) instanceof BlackSoldier){
            if(direction == 1){
                if(row+2<7 && (grid.getElement(row+2,col) instanceof BlackSoldier || grid.getElement(row+2,col) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row+1,col);
                }
            }
            else if(direction == 2){
                if(row-2>=0 && (grid.getElement(row-2,col) instanceof BlackSoldier || grid.getElement(row-2,col) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row-1,col);
                }
            }
            else if(direction == 3){
                if(col+2<7 &&  (grid.getElement(row,col+2) instanceof BlackSoldier || grid.getElement(row,col+2) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row,col+1);
                }
            }
            else if(direction == 4){
                if(col-2>=0 && (grid.getElement(row,col-2) instanceof BlackSoldier || grid.getElement(row,col-2) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row,col-1);
                }
            }
        }
        if(grid.getElement(row,col) instanceof WhithPawn){
            //System.out.println("test");
            if(direction == 1){
                if(row+2<7 && (grid.getElement(row+2,col) instanceof WhithPawn || grid.getElement(row+2,col) instanceof CaseSpecial)) {
                    pawn = (Pawn) grid.getElement(row+1,col);

                }
            }
            else if(direction == 2){
                if(row-2>=0 && (grid.getElement(row-2,col) instanceof WhithPawn || grid.getElement(row-2,col) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row-1,col);
                }
            }
            else if(direction == 3){
                if(col+2<7 && (grid.getElement(row,col+2) instanceof WhithPawn || grid.getElement(row,col+2) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row,col+1);
                }
            }
            else if(direction == 4){
                if(col-2>=0 && (grid.getElement(row,col-2) instanceof WhithPawn || grid.getElement(row,col-2) instanceof CaseSpecial)){
                    pawn = (Pawn) grid.getElement(row,col-1);
                }
            }
        }
        if (pawn != null){
            System.out.println("test");
            System.out.println(pawn);
            grid.removeElement(pawn);
        }

    }

    /**
     * check if the box is a neighbor of the throne
     * @param row
     * @param col
     *
     */
    public boolean neighborWithThrone(int row,int col){
        if(row == 3 && col == 2){
            return true;
        }
        else if(row == 3 && col == 4){
            return true;
        }
        else if(row == 2 && col == 3){
            return true;
        }
        else if(row == 4 && col == 3){
            return true;
        }
        return false;
    }


    /**
     * Counts the number of neighboring BlackSoldier objects in a grid, based on the provided row and column coordinates.
     *
     * @param row The row coordinate of the element in the grid.
     * @param col The column coordinate of the element in the grid.
     * @param grid The grid containing the elements.
     * @return The count of neighboring BlackSoldier objects.
     */
    public int countNeighborBlack(int row,int col, GridElement grid){
        int nbneighbor = 0;
        if(row+1<7 && grid.getElement(row+1,col) instanceof BlackSoldier){
            nbneighbor++;
        }
        if(row-1>=0 && grid.getElement(row-1,col) instanceof BlackSoldier){
            nbneighbor++;
        }
        if(col+1<7 && grid.getElement(row,col+1) instanceof BlackSoldier){
            nbneighbor++;
        }
        if(col-1>=0 && grid.getElement(row,col-1) instanceof BlackSoldier){
            nbneighbor++;
        }
        return nbneighbor;
    }

    /**
     * check if the king is surrounded by 4 black pawns or 3 black pawns and the throne
     * and remove the king if it is true
     * @param direction 1: bottom, 2: top, 3: right, 4: left
     * @param row
     * @param col
     * @param grid
     */
    public void checkKing(int direction, int row, int col, GridElement grid){
        if(direction == 1){
            if(row+1 == 3 && col == 3 && countNeighborBlack(row+1,col,grid) == 4){
                grid.removeElement(grid.getElement(row+1,col));
            }
            if (neighborWithThrone(row+1,col) && countNeighborBlack(row+1,col,grid) == 3){
                grid.removeElement(grid.getElement(row+1,col));
            }

        }
        if (direction == 2 ){
            if(row-1 == 3 && col == 3 && countNeighborBlack(row-1,col,grid)== 4){
                grid.removeElement(grid.getElement(row-1,col));
            }
            if (neighborWithThrone(row-1,col) && countNeighborBlack(row-1,col,grid) == 3){
                grid.removeElement(grid.getElement(row-1,col));
            }
        }
        if (direction == 3){
            if(row == 3 && col+1 == 3 && countNeighborBlack(row,col+1,grid)== 4){
                grid.removeElement(grid.getElement(row,col+1));
            }
            if (neighborWithThrone(row+1,col) && countNeighborBlack(row,col+1,grid) == 3){
                grid.removeElement(grid.getElement(row,col+1));
            }
        }
        if (direction == 4){
            if(row == 3 && col-1 == 3 && countNeighborBlack(row,col-1,grid)== 4){
                grid.removeElement(grid.getElement(row,col-1));
            }
            if (neighborWithThrone(row,col-1) && countNeighborBlack(row,col-1,grid) == 3){
                grid.removeElement(grid.getElement(row,col-1));
            }
        }
    }

/*
    private boolean verifPlacement(int col, int row, int col2, int row2,GridElement grid){
        boolean etat=false;
        int tmp=0;
        int [] possiblRow = new int[6];
        int [] possiblCol = new int[6];


        //rajouter les methode resetReachableCells et get et set

        if((col==col2 && row==row2) ||(col != col2 && row!=row2)) {
            System.out.println("erreur");
            return false;
        }
        else if(row==row2){
            int i=col+1;
            System.out.println("row egale");
            while(grid.isEmptyAt(i,row) && i<6){
                System.out.println(i);
                if(i==col){
                    continue;
                }
                possiblCol[tmp]= i;
                tmp++;
                i++;
            }
            i=col-1;
            while (grid.isEmptyAt(i,row) && i>0){
                if(i==col){
                    continue;
                }
                possiblCol[tmp]= i;
                tmp++;
                i--;
            }
            for(int y=0;y<possiblCol.length;y++){
                if(possiblCol[y]==col2){
                    return true;
                }
            }
        }
        else if(col==col2){
            int i=row+1;
            System.out.println("col egale");
            while (grid.isEmptyAt(col,i)==true && i<6){
            if(i==row){
                continue;
            }
                possiblRow[tmp]= i;
                tmp++;
                i++;
        }
            i=row-1;
        while (grid.isEmptyAt(col,i)==true && i>0){
            if(i==row){
                continue;
            }
                System.out.println(i);
                possiblRow[tmp]= i;
                tmp++;
                i--;
        }

            for(int x=0; i<possiblRow.length;x++){
                if(possiblRow[x]==row2){
                    return true;
                }
            }
        }
        return etat;
    }

*/

    /**
     * Checks if the KingPawn is on the grid.
     *
     * @param grid The grid containing the elements.
     * @return True if the KingPawn is on the grid, false otherwise.
     */
    public boolean KingOnGrid(GridElement grid){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(grid.getElement(i,j) instanceof KingPawn) {
                    return true;
                }
            }
        }
        return false;

    }

    public void checkblackplayer(GridElement grid){
        if(!KingOnGrid(grid)){
            System.out.println("black player win");
            model.setIdWinner(0);
        }
        return;
    }

    /**
     * Checks if black player have win
     * @param grid The grid containing the elements.
     * @return True if the black player win, false otherwise.
     */
    public int blackWinCheck(GridElement grid){


        int[] kingPosition = cooOfKing(grid);
        if(kingPosition != null){
            int kingposition_i = kingPosition[0];
            int kingposition_j = kingPosition[1];

            if(kingposition_i == 3 && kingposition_j == 3){
                if (grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j - 1) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j + 1 ) instanceof BlackSoldier ) {
                    grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                    System.out.println("Black win");
                    model.setIdWinner(0);
                    return 2;
                }
            }
            else if(kingposition_i == 2 && kingposition_j == 3){
                if (grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j + 1) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j - 1) instanceof BlackSoldier  && grid.getElement(kingposition_i + 1, kingposition_j) instanceof Throne ) {
                    grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                    System.out.println("Black win");
                    model.setIdWinner(0);
                    return 2;
                }
            }
            else if(kingposition_i == 3 && kingposition_j == 4){
                if (grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j + 1) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j -1) instanceof Throne) {
                    grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                    System.out.println("Black win");
                    model.setIdWinner(0);
                    return 2;
                }
            }
            else if(kingposition_i == 3 && kingposition_j == 2){
                if (grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j -1) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j + 1 ) instanceof Throne) {
                    grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                    System.out.println("Black win");
                    model.setIdWinner(0);
                    return 2;
                }
            }
            else if(kingposition_i == 4 && kingposition_j == 3){
                if (grid.getElement(kingposition_i, kingposition_j-1) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i, kingposition_j + 1) instanceof BlackSoldier &&
                        grid.getElement(kingposition_i - 1, kingposition_j) instanceof Throne) {
                    grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                    System.out.println("Black win");
                    model.setIdWinner(0);
                    return 2;
                }
            } else if (kingposition_j != 3 && kingposition_i !=3) {
                if (kingposition_i > 0 && kingposition_i < 6) {
                    if (grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier &&
                            grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier ||
                            grid.getElement(kingposition_i - 1, kingposition_j) instanceof BlackSoldier &&
                            grid.getElement(kingposition_i + 1, kingposition_j) instanceof Escape ||
                            grid.getElement(kingposition_i - 1, kingposition_j) instanceof Escape &&
                            grid.getElement(kingposition_i + 1, kingposition_j) instanceof BlackSoldier) {
                        grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                        System.out.println("Black win");
                        model.setIdWinner(0);
                        return 2;
                    }
                }

                // Vérifier à gauche et à droite du roi
                if (kingposition_j > 0 && kingposition_j < 6) {
                    if (grid.getElement(kingposition_i, kingposition_j - 1) instanceof BlackSoldier &&
                            grid.getElement(kingposition_i, kingposition_j + 1) instanceof BlackSoldier ||
                            grid.getElement(kingposition_i, kingposition_j - 1) instanceof Escape &&
                                    grid.getElement(kingposition_i, kingposition_j + 1) instanceof BlackSoldier ||
                            grid.getElement(kingposition_i, kingposition_j - 1) instanceof BlackSoldier &&
                                    grid.getElement(kingposition_i, kingposition_j + 1) instanceof Escape) {
                        grid.removeElement(grid.getElement(kingposition_i,kingposition_j));
                        System.out.println("Black win");
                        model.setIdWinner(0);
                        return 2;
                    }
                }

            }
        }



        return 0;
    }

    /**
     * Returns a list of positions of all white pawns (king and soldiers) on the game board. (king first)
     * @param posKing the position of the white king pawn
     * @param grid the game board on which to find the white pawns
     * @return a list of string representing the positions of all white pawns in the format "row column"
     */
    public ArrayList<String> storeWhitePawns(String posKing, GridElement grid){
        ArrayList<String> pionsDep = new ArrayList<>();
        for (int i = 0; i < 7; i++) {       //met tout les pions blanc dans un tableau
            for (int j = 0; j < 7; j++) {
                if (grid.getElement(i, j) instanceof WhithPawn) {
                    pionsDep.add(i + "" + j);
                }
            }
        }
        return pionsDep;



    }

    public void verifThrone(GridElement grid,BrandubhStageModel bs){
        if(grid.isEmptyAt(3,3)){
            BrandubhBoard board = bs.getBoard();
            GameStageView s3 = view.getGameStageView();
            board.putElement(bs.getThrone(),3,3);
            ThroneLook t = new ThroneLook(50,grid.getElement(3,3));
            t.onChange();
            s3.addLook(t);
        }
    }

    /**
     * This method finds the positions where a player can win in one or two moves.
     * @param possiblePos1Moves an ArrayList of String that will contain the positions where a player can win in one move.
     * @param possiblePos2Moves an ArrayList of String that will contain the positions where a player can win in two moves.
     * @param grid the GridElement object that represents the current state of the game board.
     * @param reachableCellsCopy a boolean array that represents the reachable cells on the game board.
     * @param stage the BrandubStageModel object that represents the current state of the game stage.
     */
    public void winIn1or2Move(ArrayList<String> possiblePos1Moves,
                              ArrayList<String> possiblePos2Moves,
                              GridElement grid,
                              boolean[][] reachableCellsCopy,
                              BrandubhStageModel stage){
        for(int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(reachableCellsCopy[i][j]){ //Check if cell is reachable on the copy
                    if (grid.getElement(i, j) instanceof Escape) {
                        possiblePos1Moves.add(i+""+j); // Add position to list of positions where player can win in one move
                    }
                    grid.resetReachableCells(false);// Reset initial tab
                    KingPawn kingVirtual = new KingPawn(stage,12); // Create king copy
                    grid.putElement(kingVirtual, i, j);     // Put it on the board
                    deplacementDispo(j, i, grid);    // Update possibilities
                    grid.removeElement(kingVirtual);    // Delete king copy
                    for(int y=0;y<7;y++) {          //loop on the possibility of possibilities
                        for(int x=0;x<7;x++) {
                            if(grid.canReachCell(y,x) && grid.getElement(y, x) instanceof Escape) {
                                possiblePos2Moves.add(i+""+j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**BrandubhStageModel
     * Removes unsafe positions from the list of possible moves for the king
     * @param posPossible2Coup the list of possible positions for the king
     * @param grid the game board on which to check the safety of the positions
     */
    public void checkTable2Move(ArrayList<String> posPossible2Coup, GridElement grid){
        //remove unsafe destinations from the table
        for(int q=0;q<posPossible2Coup.size();q++) {
            int rowKingDest = posPossible2Coup.get(q).charAt(0) - 48;
            int colKingDest = posPossible2Coup.get(q).charAt(1) - 48;
            if (willBeEaten(rowKingDest, colKingDest, false, 'W', grid)) {
                posPossible2Coup.remove(q);
                q-=1;
            }
        }
    }


    public int[] randomDests(GridElement grid){
        int randomPossibilite = (int) (Math.random() * (nbTrueReachableCells(grid) - 1));
        int compteurDest = 0;
        int[] randomDests = new int[2];
        boolean flagueDest = true;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (grid.canReachCell(i, j)) {
                    if (compteurDest == randomPossibilite) {
                        randomDests[0] = j;
                        randomDests[1] = i;
                        flagueDest = false;
                        break;
                    } else {
                        compteurDest += 1;
                    }
                }
            }
            if (!flagueDest) {
                break;
            }
        }
        return randomDests;
    }

    /**
     * Analyzes and plays a move based on the input line.
     *
     * @param line The input line containing the move information.
     * @return True if the move analysis and play were successful, False otherwise.
     */
    private boolean analyseAndPlay(String line) {
        BrandubhStageModel gameStage = (BrandubhStageModel) model.getGameStage();
        if(line.equals("stop")) {
            stopStage();
            return true;
        }
        // get the pawn value from the first char
        int col = (int) (line.charAt(0) - 'A');
        int row = (int) (line.charAt(1) - '1');

        int col2 = (int) (line.charAt(2) - 'A');
        int row2 = (int) (line.charAt(3) - '1');
        // check coords validity
        if ((row<0)||(row>6)) return false;
        if ((col<0)||(col>6)) return false;

        if ((row2<0)||(row2>6)) return false;
        if ((col2<0)||(col2>6)) return false;
        GridElement grid = (GridElement) gameStage.getBoard();
        if (grid.isEmptyAt(row,col)) {
            System.out.println("erreur case vide");
            return false;
        }
        //vérifier joueur

        try {
            if (grid.getElement(row,col) instanceof Pawn){
                if (grid.getElement(row,col) instanceof WhithPawn){
                    if (model.getIdPlayer() == 0) return false;
                }
                else{
                    if (model.getIdPlayer() == 1) return false;
                }
            }
            else return false;
        }
        catch (Exception e){
            return false;
        }

        //vérifier déplacement
        if(!verfiDepla(col,row,col2,row2,grid)){
            System.out.println("erreur deplacement");
            return false;
        }

        GameElement pawn = grid.getElement(row,col);


        if(pawn instanceof KingPawn && grid.getElement(row2,col2) instanceof Escape){
            grid.removeElement(grid.getElement(row2,col2));
        }



        //row++;



        // compute valid cells for the chosen pawn
        ActionList actions = new ActionList(true);
        GameAction move = new MoveAction(model, pawn, "plateau", row2, col2, AnimationTypes.NONE , 1000, 0, 0);
        // add the action to the action list.
        actions.addSingleAction(move);

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        String duel = verifDuel(row2,col2,grid);
        System.out.println(duel);
        System.out.println("row2 : "+row2+" col2 : "+col2);
        for(int i = 0; i<duel.length();i++){
            if(duel.charAt(i)=='Y'){
                checkComrade(i+1,row2,col2,grid);

            }
            if(duel.charAt(i)=='R'){
                checkKing(i+1,row2,col2,grid);
            }
        }
        verifKingWin(grid);
        blackWinCheck(grid);
        return true;
    }

    @Override
    public void endGame() {
        //System.out.println("END THE GAME");

        String message = "";
        if (model.getIdWinner() != -1) {
            message = model.getPlayers().get(model.getIdWinner()).getName() + " wins";
        }
        else {
            message = "Draw game";
        }
        // disable all events
        model.setCaptureEvents(false);
        // create a dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // remove the frame around the dialog
        alert.initStyle(StageStyle.DECORATED);
        // make it a children of the main game window => it appears centered
        alert.initOwner(view.getStage());
        // set the message displayed
        alert.setHeaderText(message);
        // define new ButtonType to fit with our needs => one type is for Quit, one for New Game
        ButtonType quit = new ButtonType("Quit");
        ButtonType newGame = new ButtonType("New Game");
        ButtonType GameSetting = new ButtonType("Game Setting");
        // remove default ButtonTypes
        alert.getButtonTypes().clear();

        Rectangle rect = new Rectangle(0, 0, 100, 100);

        // add the new ones
        alert.getButtonTypes().addAll(quit, newGame,GameSetting);
        // show the dialog and wait for the result
        Optional<ButtonType> option = alert.showAndWait();
        // check if result is quit
        if (option.get() == quit) {
            System.exit(0);
        }
        // check if result is new game
        else if (option.get() == newGame) {
            try {
                startGame();
            } catch (GameException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        else if (option.get() == GameSetting){
            try {
                view.resetView();
                BrandubhRootPane rootPane = (BrandubhRootPane)view.getRootPane();
                ((BrandubhControllerAction)controlAction).setMenuHandlers();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        // abnormal case :-)
        else {
            System.err.println("Abnormal case: dialog closed with not choice");
            System.exit(1);
        }
    }


    public void checkfinishwin(GridElement grid){
        if (model.getIdWinner() == -1){
            verifKingWin(grid);
        }
        if (model.getIdWinner() == -1){
            blackWinCheck(grid);
        }
    }

}
