package game.component;

import game.info.GameInfo;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BallTest {

    private GameInfo info;
    Player player1;
    Player player2;

    @Before
    public void setUp(){
        info = new GameInfo();

        Plate player1Plate = new Plate(10, 80, Color.YELLOW);
        player1 = new OnlinePlayer(player1Plate);

        Plate player2Plate = new Plate(10, 80, Color.WHITE);
        player2 = new OnlinePlayer(player2Plate);

        player1.setLayoutX(0);
        player1.setLayoutY(400 / 2 - 40);
        player2.setLayoutX(1000 - 10);
        player2.setLayoutY(400 / 2 - 40);
    }

    @Test
    public void testPunchX() {
        info.setBallX(1);
        info.setBallY(160);
        Ball ball = new Ball(new Circle(5), player1, player2);
        ball.action(info);
        int expected = 4;
        int actual = (int)info.getBallX();

        assertEquals(expected, actual);
    }

    @Test
    public void testPunchY() {
        info.setBallX(1);
        info.setBallY(160);
        Ball ball = new Ball(new Circle(5), player1, player2);
        ball.action(info);
        int expected = 157;
        int actual = (int)info.getBallY();

        assertEquals(expected, actual);
    }
}