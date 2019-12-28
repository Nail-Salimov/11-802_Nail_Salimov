package game.component;

import game.info.GameInfo;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BotPlayerTest {

    private GameInfo info;

    @Before
    public void setUp() {
        info = new GameInfo();
        info.setBallX(980);
        info.setBallY(340);
    }

    @Test
    public void testActionBot1() {
        Plate botPlate = new Plate(10, 80, Color.WHITE);
        Player bot = new BotPlayer(botPlate);
        bot.setLayoutX(1000 - 10);
        bot.setLayoutY(160);
        info.setEnemyY((int) bot.getLayoutY());

        System.out.println(info.getEnemyY());
        for (int i = 0; i < 10; i++) {
            bot.action(info);
        }
        int expected = 140;
        int actual = (int)info.getEnemyY();

        assertEquals(expected , actual);
    }

    @Test
    public void testActionBot2() {
        Plate botPlate = new Plate(10, 80, Color.WHITE);
        Player bot = new BotPlayer(botPlate);
        bot.setLayoutX(1000 - 10);
        bot.setLayoutY(160);
        info.setEnemyY((int) bot.getLayoutY());

        System.out.println(info.getEnemyY());
        for (int i = 0; i < 10; i++) {
            bot.action(info);
        }
        int expected = 260;
        int actual = (int)info.getEnemyY();

        assertEquals(expected , actual);
    }

}