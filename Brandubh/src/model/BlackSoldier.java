package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameStageModel;

public class BlackSoldier extends Pawn{

    static int color;

    public BlackSoldier(GameStageModel gameStageModel, int number) {
        super(gameStageModel, number);
        ElementTypes.register("blackSoldier",51);
        this.type = ElementTypes.getType("blackSoldier");
        this.color = 0;
    }




}
