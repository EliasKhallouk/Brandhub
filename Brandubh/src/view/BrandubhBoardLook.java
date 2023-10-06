package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeType;
import model.BrandubhBoard;
import model.KingPawn;
import model.Throne;

import java.security.Key;

public class BrandubhBoardLook extends GridLook {

    // the array of rectangle composing the grid
    private Rectangle[][] cells;

    public BrandubhBoardLook(int size, GameElement element) {
        // NB: To have more liberty in the design, GridLook does not compute the cell size from the dimension of the element parameter.
        // then, cells have a size of (size-20)/3
        super(size, size, (size-20)/7, (size-20)/7, 10, "0X000000", element);
        cells = new Rectangle[7][7];
        // create the rectangles.
        for (int i=0;i<7;i++) {
            for(int j=0;j<7;j++) {
                Color c;
                if ((i+j)%2 == 0) {
                    c = Color.BEIGE;
                }
                else {
                    c = Color.DARKGRAY;
                }
                cells[i][j] = new Rectangle(cellWidth, cellHeight, c);
                cells[i][j].setX(j*cellWidth+borderWidth);
                cells[i][j].setY(i*cellHeight+borderWidth);
                addShape(cells[i][j]);
            }
        }
    }

    public void throneLook(){

        SVGPath path1 = new SVGPath();
        path1.setContent("M 23.738281 1.714844 L 22.015625 3.4375 L 22.511719 4.929688 C 23.011719 6.445312 23.03125 6.632812 22.75 7.183594 C 22.632812 7.410156 21.585938 8.066406 21.328125 8.066406 C 21.265625 8.066406 21.105469 7.898438 20.96875 7.691406 L 20.71875 7.3125 L 20.917969 6.792969 C 21.285156 5.816406 21.097656 4.871094 20.371094 4.144531 C 19.074219 2.859375 16.855469 3.367188 16.304688 5.089844 C 16.097656 5.726562 16.097656 5.996094 16.296875 6.664062 C 16.605469 7.710938 17.730469 8.445312 18.835938 8.347656 C 19.3125 8.296875 19.34375 8.308594 19.632812 8.6875 L 19.941406 9.0625 L 19.433594 9.621094 C 18.109375 11.066406 17.441406 12.589844 17.183594 14.761719 L 17.121094 15.238281 L 14.644531 15.238281 L 14.644531 13.894531 C 14.644531 12.582031 14.644531 12.550781 14.941406 12.082031 C 16.167969 10.148438 15.171875 7.679688 12.9375 7.113281 C 11.015625 6.613281 9.074219 8.027344 8.894531 10.050781 C 8.835938 10.726562 9.035156 11.542969 9.414062 12.09375 L 9.664062 12.460938 L 9.664062 27.960938 L 9.402344 28.199219 C 8.84375 28.71875 8.867188 28.367188 8.867188 37.972656 L 8.867188 46.835938 L 8.40625 47.285156 C 7.628906 48.070312 7.171875 49.238281 7.171875 50.453125 L 7.171875 51 L 43.730469 51 L 43.730469 50.324219 C 43.730469 49.097656 43.351562 48.140625 42.535156 47.292969 L 42.035156 46.777344 L 42.035156 37.882812 C 42.035156 28.238281 42.066406 28.726562 41.496094 28.199219 L 41.238281 27.960938 L 41.238281 20.21875 C 41.238281 12.480469 41.238281 12.480469 41.449219 12.210938 C 41.804688 11.765625 42.035156 11.007812 42.035156 10.308594 C 42.035156 9.003906 41.359375 7.9375 40.164062 7.351562 C 39.585938 7.0625 39.40625 7.023438 38.75 7.03125 C 37.773438 7.042969 37.003906 7.371094 36.335938 8.078125 C 35.679688 8.777344 35.429688 9.484375 35.480469 10.46875 C 35.519531 11.105469 35.582031 11.324219 35.890625 11.894531 L 36.257812 12.5625 L 36.257812 15.238281 L 33.765625 15.238281 L 33.765625 14.890625 C 33.765625 14.324219 33.480469 13.070312 33.160156 12.242188 C 32.800781 11.304688 32.292969 10.5 31.546875 9.703125 L 30.988281 9.09375 L 31.265625 8.714844 C 31.535156 8.347656 31.546875 8.347656 32.285156 8.328125 C 32.75 8.316406 33.128906 8.246094 33.328125 8.136719 C 34.625 7.449219 35.121094 6.035156 34.503906 4.800781 C 33.578125 2.949219 31.027344 2.96875 30.050781 4.832031 C 29.792969 5.339844 29.761719 6.375 30.011719 6.84375 C 30.242188 7.269531 30.230469 7.371094 29.894531 7.769531 L 29.605469 8.117188 L 29.046875 7.828125 C 28.320312 7.460938 27.992188 7.070312 27.992188 6.59375 C 27.992188 6.394531 28.199219 5.609375 28.449219 4.839844 L 28.90625 3.464844 L 27.183594 1.734375 L 25.449219 0 Z M 26.285156 3.128906 L 27.015625 3.855469 L 26.644531 4.992188 C 26.199219 6.375 26.199219 7.03125 26.65625 7.867188 C 27.003906 8.515625 27.414062 8.875 28.308594 9.324219 C 29.625 9.992188 30.429688 10.71875 31.167969 11.914062 C 31.816406 12.980469 32.0625 14.003906 32.074219 15.660156 L 32.074219 16.933594 L 36.257812 16.933594 L 36.257812 27.960938 L 36 28.199219 C 35.472656 28.699219 35.460938 28.738281 35.460938 32.074219 C 35.460938 33.765625 35.441406 35.164062 35.421875 35.164062 C 35.402344 35.164062 35.101562 35.023438 34.765625 34.851562 C 34.355469 34.652344 33.867188 34.515625 33.300781 34.433594 C 32.53125 34.324219 32.460938 34.304688 32.503906 34.117188 C 32.53125 34.007812 32.75 33.128906 33 32.171875 C 34.035156 28.199219 34.566406 24.234375 34.566406 20.351562 L 34.566406 18.625 L 30.511719 18.625 L 30.449219 16.902344 C 30.410156 15.507812 30.359375 15.082031 30.191406 14.601562 C 29.59375 12.917969 28.320312 11.742188 26.625 11.304688 C 24.535156 10.757812 22.152344 11.8125 21.148438 13.714844 C 20.609375 14.722656 20.519531 15.210938 20.519531 17.003906 L 20.519531 18.625 L 16.304688 18.625 L 16.367188 20.9375 C 16.476562 25.332031 17.144531 29.671875 18.308594 33.679688 L 18.507812 34.324219 L 17.632812 34.433594 C 17.023438 34.515625 16.59375 34.632812 16.226562 34.832031 C 15.925781 34.984375 15.660156 35.121094 15.617188 35.140625 C 15.570312 35.164062 15.539062 33.925781 15.539062 32.382812 C 15.539062 29.183594 15.480469 28.757812 14.980469 28.269531 L 14.644531 27.941406 L 14.644531 16.933594 L 18.828125 16.933594 L 18.828125 15.847656 C 18.828125 14.484375 19.015625 13.46875 19.453125 12.53125 C 20.109375 11.136719 21.128906 10.089844 22.542969 9.363281 C 24.523438 8.335938 24.992188 7.132812 24.265625 4.929688 L 23.9375 3.933594 L 24.691406 3.15625 C 25.101562 2.738281 25.46875 2.390625 25.5 2.390625 C 25.53125 2.390625 25.878906 2.71875 26.285156 3.128906 Z M 19.175781 5.328125 C 19.703125 5.847656 19.355469 6.671875 18.617188 6.671875 C 17.910156 6.671875 17.570312 5.828125 18.078125 5.328125 C 18.230469 5.167969 18.4375 5.078125 18.628906 5.078125 C 18.816406 5.078125 19.027344 5.167969 19.175781 5.328125 Z M 32.8125 5.289062 C 33 5.4375 33.070312 5.578125 33.070312 5.855469 C 33.070312 6.605469 32.242188 6.953125 31.726562 6.425781 C 31 5.707031 32.003906 4.652344 32.8125 5.289062 Z M 12.988281 8.894531 C 14.503906 9.75 13.765625 12.09375 12.023438 11.921875 C 10.617188 11.792969 10.03125 10.058594 11.078125 9.09375 C 11.585938 8.617188 12.363281 8.535156 12.988281 8.894531 Z M 39.484375 8.863281 C 40.074219 9.144531 40.382812 9.652344 40.382812 10.308594 C 40.382812 11.8125 38.570312 12.5 37.53125 11.394531 C 36.328125 10.082031 37.871094 8.078125 39.484375 8.863281 Z M 26.945312 13.167969 C 27.441406 13.425781 28.148438 14.105469 28.390625 14.554688 C 28.707031 15.140625 28.789062 15.796875 28.789062 18.050781 L 28.789062 20.320312 L 32.949219 20.320312 L 32.910156 21.335938 C 32.710938 25.777344 32.074219 29.734375 30.917969 33.539062 L 30.667969 34.367188 L 25.460938 34.367188 C 20.578125 34.367188 20.242188 34.355469 20.191406 34.1875 C 18.855469 29.632812 18.21875 25.988281 18.058594 22.035156 L 18 20.320312 L 22.09375 20.320312 L 22.125 17.851562 L 22.164062 15.390625 L 22.492188 14.722656 C 22.878906 13.933594 23.496094 13.347656 24.246094 13.046875 C 24.980469 12.761719 26.246094 12.808594 26.945312 13.167969 Z M 13.027344 20.660156 L 13 27.742188 L 12.183594 27.769531 L 11.355469 27.800781 L 11.355469 13.597656 L 12.203125 13.585938 L 13.050781 13.585938 Z M 39.546875 20.6875 L 39.546875 27.789062 L 37.953125 27.789062 L 37.953125 13.597656 L 39.546875 13.597656 Z M 13.847656 30.230469 L 13.847656 31.078125 L 10.558594 31.078125 L 10.558594 29.382812 L 13.847656 29.382812 Z M 40.441406 30.230469 L 40.441406 31.078125 L 37.054688 31.078125 L 37.054688 30.300781 C 37.054688 29.871094 37.085938 29.484375 37.125 29.453125 C 37.15625 29.414062 37.921875 29.382812 38.816406 29.382812 L 40.441406 29.382812 Z M 13.847656 39.394531 L 13.847656 46.019531 L 12.429688 46.019531 C 11.644531 46.019531 10.90625 46.050781 10.789062 46.078125 L 10.558594 46.140625 L 10.558594 32.773438 L 13.847656 32.773438 Z M 40.441406 39.457031 L 40.441406 46.140625 L 40.222656 46.078125 C 40.09375 46.050781 39.335938 46.019531 38.527344 46.019531 L 37.054688 46.019531 L 37.054688 32.773438 L 40.441406 32.773438 Z M 33.679688 36.179688 C 34.335938 36.398438 35.003906 37.003906 35.222656 37.574219 C 35.28125 37.742188 34.773438 37.75 25.46875 37.75 C 15.808594 37.75 15.660156 37.75 15.707031 37.5625 C 15.839844 37.066406 16.65625 36.359375 17.332031 36.148438 C 17.761719 36.019531 33.289062 36.039062 33.679688 36.179688 Z M 35.441406 40.210938 L 35.472656 41.039062 L 15.539062 41.039062 L 15.539062 39.34375 L 25.480469 39.367188 L 35.410156 39.394531 Z M 35.460938 44.375 L 35.460938 46.019531 L 15.539062 46.019531 L 15.539062 42.734375 L 35.460938 42.734375 Z M 40.589844 47.902344 C 41.027344 48.101562 41.578125 48.609375 41.796875 49.027344 L 41.945312 49.304688 L 25.5 49.304688 C 16.464844 49.304688 9.066406 49.277344 9.066406 49.226562 C 9.066406 48.878906 9.910156 48.050781 10.480469 47.84375 C 10.726562 47.753906 14.453125 47.722656 25.5 47.722656 C 39.523438 47.710938 40.210938 47.722656 40.589844 47.902344 Z M 40.589844 47.902344 ");
        path1.setLayoutX(3*cellWidth+ 26 +borderWidth);
        path1.setLayoutY(3*cellHeight+ 25 +borderWidth);
        path1.setFill(Color.YELLOW);

        addShape(path1);

    }

    @Override
    public void onChange() {
        // in a pawn is selected, reachableCells changes. Thus, the look of the board must also changes.
        BrandubhBoard board = (BrandubhBoard)element;
        boolean[][] reach = board.getReachableCells();
        for(int i=0;i<7;i++) {
            for(int j=0;j<7;j++) {
                if (reach[i][j]) {
                    cells[i][j].setStrokeWidth(3);
                    cells[i][j].setStrokeMiterLimit(10);
                    cells[i][j].setStrokeType(StrokeType.CENTERED);
                    cells[i][j].setStroke(Color.valueOf("0x333333"));
                } else {
                    cells[i][j].setStrokeWidth(0);
                }
            }
        }
        cells[3][3].setStrokeWidth(3);
        cells[3][3].setStrokeMiterLimit(10);
        cells[3][3].setStrokeType(StrokeType.CENTERED);
        cells[3][3].setStroke(Color.valueOf("0xFFFF00"));
        setEscapeborder();
        if(board.getElement(3,3) instanceof Throne){
            throneLook();
        }

    }

    public void setEscapeborder(){
        cells[0][0].setStrokeWidth(3);
        cells[0][0].setStrokeMiterLimit(10);
        cells[0][0].setStrokeType(StrokeType.CENTERED);
        cells[0][0].setStroke(Color.GREEN);

        cells[0][6].setStrokeWidth(3);
        cells[0][6].setStrokeMiterLimit(10);
        cells[0][6].setStrokeType(StrokeType.CENTERED);
        cells[0][6].setStroke(Color.GREEN);

        cells[6][0].setStrokeWidth(3);
        cells[6][0].setStrokeMiterLimit(10);
        cells[6][0].setStrokeType(StrokeType.CENTERED);
        cells[6][0].setStroke(Color.GREEN);

        cells[6][6].setStrokeWidth(3);
        cells[6][6].setStrokeMiterLimit(10);
        cells[6][6].setStrokeType(StrokeType.CENTERED);
        cells[6][6].setStroke(Color.GREEN);
    }
}
