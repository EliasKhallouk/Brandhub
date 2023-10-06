package model;

import boardifier.model.GameStageModel;

public abstract class WhithPawn extends Pawn{

    public int color = 1;



    public WhithPawn(GameStageModel gameStageModel, int number) {
        super(gameStageModel, number);
    }

    abstract public int getColor();

    abstract public void setColor(int color);
}
