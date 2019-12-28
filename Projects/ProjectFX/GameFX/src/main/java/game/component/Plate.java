package game.component;

import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Plate extends Pane {
    private Rectangle rectangle;

    public Plate(int v1, int v2, Color color) {
        rectangle = new Rectangle(v1, v2, color);
        getChildren().addAll(rectangle);
    }

    public void moveY(int y) {
        for (int i = 0; i < Math.abs(y); i++) {
            if (y > 0) {
                this.setLayoutY(this.getLayoutY() + 10);
            } else {
                this.setLayoutY(this.getLayoutY() - 10);
            }
        }
    }

    public double getY() {
        return this.getLayoutY();
    }

    public Plate setX(int x) {
        this.setLayoutX(x);
        return this;
    }

    public Plate setY(int y) {
        this.setLayoutX(y);
        return this;
    }
}
