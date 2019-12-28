package game.component;


import game.info.GameInfo;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Ball extends Pane {
    private Circle circle;
    private int speedX = 3, speedY = 3, dx = speedX, dy = speedY;

    private Player player1;
    private Player player2;

    public Ball(Circle circle, Player player1, Player player2) {
        this.getChildren().addAll(circle);
        this.player1 = player1;
        this.player2 = player2;
    }

    public void action(GameInfo info){

        double x = info.getBallX();
        double y = info.getBallY();
        this.setLayoutX(x);
        this.setLayoutY(y);

        Player player1 = this.player1;
        Player player2 = this.player2;

        if(info.isRight()){
            player1 = this.player2;
            player2 = this.player1;
        }

        if (x <= 10 && y > player1.getLayoutY() && y < player1.getLayoutY() + 80) {
            dx = speedX;
        }

        if (x >= info.getWidth() - 12.5 && y > player2.getLayoutY() && y < player2.getLayoutY() + 80) {
            speedX++;
            dx = -speedX;
        }

        if (y <= 0) {
            dy = speedY;
        }

        if (y >= info.getHeight() - 5) {
            dy = -speedY;
        }

        info.setBallX(this.getLayoutX() + dx);
        info.setBallY(this.getLayoutY() + dy);
    }

    public void defaultSettings(GameInfo info) {
        speedX = 3;
        dx = -dx;
        dy = -dy;
        speedY = 3;
        info.setBallX(info.getWidth() / 2);
        info.setBallY(info.getHeight() / 2);
    }

}
