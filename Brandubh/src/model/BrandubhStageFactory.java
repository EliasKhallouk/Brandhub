package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class BrandubhStageFactory extends StageElementsFactory {
    private BrandubhStageModel stageModel;

    public BrandubhStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (BrandubhStageModel) gameStageModel;
    }

    @Override
    public void setup() {
        // create the board
        stageModel.setBoard(new BrandubhBoard(10, 60, stageModel));

        // create the pawns
        Pawn[] blackPawns = new Pawn[8];
        for(int i=0;i<blackPawns.length;i++) {
            blackPawns[i] = new BlackSoldier(stageModel, i);
        }
        stageModel.setBlackPawns(blackPawns);




        Throne Throne = new Throne(stageModel);
        stageModel.setThrone(Throne);

        Pawn[] whitePawns = new Pawn[5];
        for(int i=0;i<4;i++) {
            whitePawns[i] = new WhitSoldier(stageModel, i+8);
        }
        whitePawns[4] = new KingPawn(stageModel , 12);

        stageModel.setWhitePawns(whitePawns);


        Escape[] escapes = new Escape[4];
        for (int i = 0 ; i<4;i++){
            escapes[i] = new Escape(stageModel);
        }
        stageModel.setEscapes(escapes);




        // assign pawns to their potitions on the board

        BrandubhBoard board = stageModel.getBoard();
        board.putElement(whitePawns[4], 3, 3);
        board.putElement(whitePawns[0], 2, 3);
        board.putElement(whitePawns[1], 3, 2);
        board.putElement(whitePawns[2], 4, 3);
        board.putElement(whitePawns[3], 3, 4);

        board.putElement(blackPawns[0], 0, 3);
        board.putElement(blackPawns[1], 1, 3);

        board.putElement(blackPawns[2], 3, 0);
        board.putElement(blackPawns[3], 3, 1);

        board.putElement(blackPawns[4], 3, 5);
        board.putElement(blackPawns[5], 3, 6);

        board.putElement(blackPawns[6], 5, 3);
        board.putElement(blackPawns[7], 6, 3);

        board.putElement(escapes[0], 0, 0);
        board.putElement(escapes[1], 0, 6);
        board.putElement(escapes[2], 6, 0);
        board.putElement(escapes[3], 6, 6);


        // create the text
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(10,30);
        text.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setPlayerName(text);
    }


    public int[] getCooPawn(int number){
        int[] coo = new int[2];
        for (int i = 0 ; i<7;i++){
            for (int j = 0 ; j<7;j++){

                if (stageModel.getBoard().getElement(i,j) != null){
                    Pawn pawn = (Pawn)stageModel.getBoard().getElement(i,j);
                    if ( pawn.getNumber() == number){
                        coo[0] = i;
                        coo[1] = j;
                        return coo;
                    }
                }
            }

        }
        return coo;
    }



}
