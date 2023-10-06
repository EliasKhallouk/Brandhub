package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameStageModel;

public class Throne extends CaseSpecial{

    public Throne(GameStageModel gameStageModel){
        super(gameStageModel);
        ElementTypes.register("Throne",55);
        type = ElementTypes.getType("Throne");
    }


}
