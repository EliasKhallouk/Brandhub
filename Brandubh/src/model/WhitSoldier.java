package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameStageModel;

public class WhitSoldier extends WhithPawn{

    public int color;

        public WhitSoldier(GameStageModel gameStageModel, int number) {
            super(gameStageModel , number);
            ElementTypes.register("whiteSoldier",52);
            this.type = ElementTypes.getType("whiteSoldier");
            this.color = 1;
        }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }



}
