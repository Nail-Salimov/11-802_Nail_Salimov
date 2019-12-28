package game.component;

import game.info.GameInfo;
import javafx.scene.layout.Pane;

public abstract class Player extends Pane {
    abstract public void action(GameInfo gameInfo);
}
