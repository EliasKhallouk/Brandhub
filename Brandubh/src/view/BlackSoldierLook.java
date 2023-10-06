package view;


import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.BlackSoldier;
import model.Pawn;

public class BlackSoldierLook extends PawnLook{

    public Circle circle;

    public BlackSoldierLook(int radius, GameElement element) {
        super(radius, element);
        BlackSoldier pawn = (BlackSoldier)element;
        circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(Color.BLACK);
        circle.setCenterX(radius+5);
        circle.setCenterY(radius+5);
        circle.setTranslateX(4);
        circle.setTranslateY(4);
        addShape(circle);

        SVGPath path1 = new SVGPath();
        path1.setContent("M 7.273438 27.695312 C 7.050781 27.289062 6.769531 26.910156 6.433594 26.574219 C 6.082031 26.222656 5.679688 25.933594 5.25 25.707031 C 4.738281 25.433594 5.144531 24.851562 5.144531 24.851562 C 5.667969 24.054688 6.148438 23.253906 6.542969 22.554688 L 3.886719 19.898438 L 1.308594 19.898438 C 0.8125 19.898438 0.410156 19.496094 0.410156 19 C 0.410156 18.503906 0.8125 18.101562 1.308594 18.101562 L 4.257812 18.101562 C 4.496094 18.101562 4.726562 18.195312 4.894531 18.363281 L 14.601562 28.0625 C 14.769531 28.230469 14.863281 28.460938 14.863281 28.699219 L 14.863281 31.664062 C 14.863281 32.160156 14.460938 32.5625 13.964844 32.5625 C 13.46875 32.5625 13.066406 32.160156 13.066406 31.664062 L 13.066406 29.070312 L 10.421875 26.429688 C 9.707031 26.851562 8.878906 27.367188 8.054688 27.933594 C 8.054688 27.933594 7.582031 28.269531 7.273438 27.695312 Z M 7.273438 27.695312");
        path1.setFill(Color.valueOf("0xFFFFFF"));
        addShape(path1);
        path1.setTranslateX(22);
        path1.setTranslateY(21);
        SVGPath path2 = new SVGPath();
        path2.setContent("M 3.027344 26.960938 C 2.21875 26.960938 1.457031 27.273438 0.886719 27.84375 C 0.316406 28.414062 0 29.171875 0 29.980469 C 0 30.785156 0.316406 31.542969 0.886719 32.113281 C 1.457031 32.683594 2.21875 33 3.027344 33 C 3.832031 33 4.59375 32.683594 5.164062 32.113281 C 5.734375 31.542969 6.050781 30.785156 6.050781 29.980469 C 6.050781 29.171875 5.734375 28.414062 5.164062 27.84375 C 4.59375 27.273438 3.832031 26.960938 3.027344 26.960938 Z M 3.027344 26.960938");
        path2.setFill(Color.valueOf("0xFFFFFF"));
        addShape(path2);
        path2.setTranslateX(22);
        path2.setTranslateY(21);
        SVGPath path3 = new SVGPath();
        path3.setContent("M 11.484375 20.730469 C 11.085938 21.128906 10.433594 21.128906 10.035156 20.730469 L 9.03125 19.730469 C 8.632812 19.332031 8.632812 18.679688 9.03125 18.285156 L 26.132812 1.203125 C 26.53125 0.804688 27.316406 0.4375 27.875 0.386719 L 32.066406 0.00390625 C 32.628906 -0.046875 33.042969 0.371094 32.992188 0.929688 L 32.613281 5.113281 C 32.5625 5.675781 32.195312 6.457031 31.796875 6.855469 L 14.695312 23.941406 C 14.296875 24.339844 13.644531 24.339844 13.246094 23.941406 L 12.246094 22.9375 C 11.847656 22.539062 11.847656 21.890625 12.246094 21.492188 L 26.5625 7.1875 C 26.773438 6.976562 26.773438 6.636719 26.5625 6.425781 C 26.351562 6.214844 26.011719 6.214844 25.800781 6.425781 Z M 11.484375 20.730469");
        path3.setFill(Color.valueOf("0xFFFFFF"));
        addShape(path3);
        path3.setTranslateX(22);
        path3.setTranslateY(21);

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
