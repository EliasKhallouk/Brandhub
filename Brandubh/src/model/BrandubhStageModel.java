package model;

import boardifier.model.*;

public class BrandubhStageModel extends GameStageModel {

    // states
    public final static int STATE_SELECTPAWN = 1; // the player must select a pawn
    public final static int STATE_SELECTDEST = 2; // the player must select a destination

    private BrandubhBoard board;
    private Pawn[] blackPawns;
    private Pawn[] WhitePawns;
    private int blackPawnsToPlay;
    private int whitePawnsToPlay;

    private Escape[] escapes;

    private Throne Throne;
    private TextElement playerName;

    public BrandubhStageModel(String name, Model model) {
        super(name, model);
        state = STATE_SELECTPAWN;
        blackPawnsToPlay = 8;
        whitePawnsToPlay = 5;
        setupCallbacks();
    }

    public BrandubhBoard getBoard() {
        return board;
    }


    public GameStageModel getStageModel() {
        return this;
    }

    public void setBoard(BrandubhBoard board) {
        this.board = board;
        addGrid(board);
    }

    public Pawn[] getBlackPawns() {
        return blackPawns;
    }

    public Pawn[] getWhitePawns() {
        return WhitePawns;
    }

    public Escape[] getEscapes() {
        return escapes;
    }

    public Throne getThrone(){
        return Throne;
    }


    public void setBlackPawns(Pawn[] blackPawns) {
        this.blackPawns = blackPawns;
        for(int i=0;i<blackPawns.length;i++) {
            addElement(blackPawns[i]);
        }
    }

    public void setWhitePawns(Pawn[] whitePawns) {
        this.WhitePawns = whitePawns;
        for(int i=0;i<whitePawns.length;i++) {
            addElement(whitePawns[i]);
        }
    }
    public void setThrone(Throne Throne){
        this.Throne = Throne;
        addElement(Throne);
    }
    public void setEscapes(Escape[] escapes) {
        this.escapes = escapes;
        for(int i=0;i<escapes.length;i++) {
            addElement(escapes[i]);
        }
    }
    public TextElement getPlayerName() {
        return playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    private void setupCallbacks() {
        onSelectionChange( () -> {
            // get the selected pawn if any
            if (selected.size() == 0) {
                board.resetReachableCells(false);
                return;
            }
            Pawn pawn = (Pawn) selected.get(0);
            System.out.println("Pawn selected: " + pawn.getNumber() + " " + pawn.getColor() );
            board.setValidCells(pawn.getNumber());
        });

        onPutInGrid( (element, gridDest, rowDest, colDest) -> {
            if (gridDest != board) return;

        });
    }

    private void computePartyResult() {
        int idWinner = -1;
        // get the empty cell, which should be in 2D at [0,0], [0,2], [1,1], [2,0] or [2,2]
        // i.e. or in 1D at index 0, 2, 4, 6 or 8
        int i = 0;
        int nbBlack = 0;
        int nbRed = 0;
        int countBlack = 0;
        int countRed = 0;
        Pawn p = null;
        int row, col;
        for (i = 0; i < 9; i+=2) {
            if (board.isEmptyAt(i / 3, i % 3)) break;
        }
        // get the 4 adjacent cells (if they exist) starting by the upper one
        row = (i / 3) - 1;
        col = i % 3;
        for (int j = 0; j < 4; j++) {
            // skip invalid cells
            if ((row >= 0) && (row <= 2) && (col >= 0) && (col <= 2)) {
                p = (Pawn) (board.getElement(row, col));
                if (p.getColor() == Pawn.PAWN_BLACK) {
                    nbBlack++;
                    countBlack += p.getNumber();
                } else {
                    nbRed++;
                    countRed += p.getNumber();
                }
            }
            // change row & col to set them at the correct value for the next iteration
            if ((j==0) || (j==2)) {
                row++;
                col--;
            }
            else if (j==1) {
                col += 2;
            }
        }
        System.out.println("nb black: "+nbBlack+", nb red: "+nbRed+", count black: "+countBlack+", count red: "+countRed);
        // decide whose winning
        if (nbBlack < nbRed) {
            idWinner = 0;
        }
        else if (nbBlack > nbRed) {
            idWinner = 1;
        }
        else {
            if (countBlack < countRed) {
                idWinner = 0;
            }
            else if (countBlack > countRed) {
                idWinner = 1;
            }
        }
        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopGame();
    }




    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new BrandubhStageFactory(this);
    }
}
