package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Pawn;

public abstract class PawnLook extends ElementLook {

    public PawnLook(int radius, GameElement element) {
        super(element);
    }

    @Override
    public abstract void onSelectionChange();

    @Override
    public abstract void onChange();

}
