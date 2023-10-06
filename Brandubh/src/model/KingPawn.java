package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameStageModel;

public class KingPawn extends WhithPawn{

    public int color;


    public KingPawn(GameStageModel gameStageModel, int number) {
        super(gameStageModel, number);
        ElementTypes.register("kingPawn",53);
        this.type = ElementTypes.getType("kingPawn");
        this.color = 2;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
