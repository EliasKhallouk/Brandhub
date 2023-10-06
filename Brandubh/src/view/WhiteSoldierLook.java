package view;

import boardifier.model.GameElement;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Pawn;
import model.WhitSoldier;

public class WhiteSoldierLook extends PawnLook{


        public Circle circle;

        public WhiteSoldierLook(int size, GameElement element){
            super(size, element);
            WhitSoldier pawn = (WhitSoldier)element;

            circle = new Circle();
            circle.setRadius(size);
            circle.setFill(Color.WHITE);
            circle.setCenterX(size+5);
            circle.setCenterY(size+5);
            circle.setTranslateX(4);
            circle.setTranslateY(4);





            SVGPath svgPath = new SVGPath();
            svgPath.setContent("M 29.320312 0.101562 C 28.960938 -0.0703125 28.539062 -0.0195312 28.230469 0.226562 C 25.171875 2.6875 20.203125 2.6875 17.148438 0.226562 C 16.769531 -0.0742188 16.230469 -0.0742188 15.851562 0.226562 C 12.796875 2.6875 7.828125 2.6875 4.769531 0.226562 C 4.460938 -0.0195312 4.039062 -0.0703125 3.679688 0.101562 C 3.320312 0.273438 3.09375 0.632812 3.09375 1.03125 L 3.09375 19.59375 C 3.09375 25.574219 7.996094 30.570312 16.210938 32.960938 C 16.304688 32.988281 16.402344 33 16.5 33 C 16.597656 33 16.695312 32.988281 16.789062 32.960938 C 25.003906 30.570312 29.90625 25.574219 29.90625 19.59375 L 29.90625 1.03125 C 29.90625 0.632812 29.679688 0.273438 29.320312 0.101562 Z M 5.15625 2.914062 C 7.347656 4.015625 9.921875 4.351562 12.355469 3.929688 L 5.15625 11.785156 Z M 15.46875 30.554688 C 8.960938 28.324219 5.15625 24.292969 5.15625 19.59375 L 5.15625 17.53125 L 15.46875 17.53125 Z M 15.46875 15.46875 L 10.546875 15.46875 L 15.46875 9.929688 Z M 15.46875 6.824219 L 7.785156 15.46875 L 5.15625 15.46875 L 5.15625 14.839844 L 15.46875 3.589844 Z M 17.53125 17.53125 L 20.445312 17.53125 L 17.53125 20.929688 Z M 17.53125 24.101562 L 23.160156 17.53125 L 26.449219 17.53125 L 17.53125 27.296875 Z M 27.84375 19.59375 C 27.84375 24.292969 24.039062 28.324219 17.53125 30.554688 L 17.53125 30.351562 L 27.84375 19.058594 Z M 27.84375 15.46875 L 17.53125 15.46875 L 17.53125 2.894531 C 20.6875 4.492188 24.6875 4.507812 27.84375 2.914062 Z M 27.84375 15.46875 ");
            // Définit la couleur de remplissage et de contour
            svgPath.setFill(Color.BLACK);
            svgPath.setStroke(Color.BLACK);
            addShape(circle);
            svgPath.setTranslateX(22);
            svgPath.setTranslateY(22);
            addShape(svgPath);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.GRAY);
            dropShadow.setOffsetX(5);
            dropShadow.setOffsetY(5);
            dropShadow.setRadius(8);
            dropShadow.setSpread(0.5);
            circle.setEffect(dropShadow);



        }




    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(3);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(Color.valueOf("0x333333"));
        }
        else {
            circle.setStrokeWidth(0);
        }
    }
    @Override
    public void onChange() {
        clearShapes();
    }
}
