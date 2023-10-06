package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import model.BrandubhStageModel;

public class BrandubhStageView extends GameStageView {
    public BrandubhStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
        width = 920;
        height = 920;
    }

    @Override
    public void createLooks() {
        BrandubhStageModel model = (BrandubhStageModel)gameStageModel;

        addLook(new BrandubhBoardLook(750, model.getBoard()));

        for(int i=0;i<8;i++) {
            addLook(new BlackSoldierLook(30,model.getBlackPawns()[i]));
        }
        for (int i=0;i<4;i++){
            addLook(new WhiteSoldierLook(30,model.getWhitePawns()[i]));

        }
        addLook(new KingLook(30,model.getWhitePawns()[4]));
        for (int i=0;i<4;i++){
            addLook(new EscapeLook(70,model.getEscapes()[i]));
        }

        addLook(new ThroneLook(30,model.getThrone()));

        addLook(new TextLook(24, "0x000000", model.getPlayerName()));
    }
}
