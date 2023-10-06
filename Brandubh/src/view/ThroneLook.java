package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.Escape;
import model.Pawn;
import model.Throne;

public class ThroneLook extends ElementLook{

    private Rectangle rectangle;

    public ThroneLook(int radius, GameElement element) {
        super(element);
        rectangle = new Rectangle(0, 0, Color.YELLOW);
        rectangle.setX(0);
        rectangle.setY(0);
        addShape(rectangle);

    }


    @Override
    public void onSelectionChange() {
        Throne throne = (Throne)getElement();
        if (throne.isSelected()) {
            rectangle.setStrokeWidth(3);
            rectangle.setStrokeMiterLimit(10);
            rectangle.setStrokeType(StrokeType.CENTERED);
            rectangle.setStroke(Color.valueOf("0x333333"));
        }
        else {
            rectangle.setStrokeWidth(0);
        }
    }



    @Override
    public void onChange() {

    }

}
