package game.component;


import game.info.GameInfo;

public class KeyboardPlayer2 extends Player {
    private Plate plate;

    public KeyboardPlayer2(Plate plate) {
        this.plate = plate;
        getChildren().addAll(plate);
    }

    public void action(GameInfo info){
        this.setLayoutY(info.getEnemyY());
        info.setEnemyY((int)this.getLayoutY());
    }
}
