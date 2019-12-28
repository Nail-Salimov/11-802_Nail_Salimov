package game.component;


import game.info.GameInfo;

public class KeyboardPlayer extends Player {
    private Plate plate;

    public KeyboardPlayer(Plate plate) {
        this.plate = plate;
        getChildren().addAll(plate);
    }

    public void action(GameInfo info){
        this.setLayoutY(info.getPlayerY());
        info.setPlayerY((int)this.getLayoutY());
    }
}
