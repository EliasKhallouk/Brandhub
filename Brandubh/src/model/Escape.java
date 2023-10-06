package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameStageModel;

public class Escape extends CaseSpecial{


    public Escape(GameStageModel gameStageModel){
        super(gameStageModel);
        ElementTypes.register("Escape",56);
        type = ElementTypes.getType("Escape");
    }
}
