package game.component;


import game.info.GameInfo;

public class OnlinePlayer extends Player {

    private Plate plate;

    public OnlinePlayer(Plate plate) {
        this.plate = plate;
        getChildren().addAll(plate);
    }

    @Override
    public void action(GameInfo gameInfo) {
        this.setLayoutY(gameInfo.getEnemyY());
    }
}
