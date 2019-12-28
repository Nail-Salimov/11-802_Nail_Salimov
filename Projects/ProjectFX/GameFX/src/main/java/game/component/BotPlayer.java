package game.component;

import game.info.GameInfo;

public class BotPlayer extends Player {
    private Plate plate;

    public BotPlayer(Plate plate) {
        this.plate = plate;
        this.getChildren().addAll(plate);
    }


    public void action(GameInfo info) {
        this.setLayoutY(info.getEnemyY());

        int width = info.getWidth();

        double x = info.getBallX();
        double y = info.getBallY();

        if (x > info.getWidth() / 2 && info.getEnemyY() > y) {
            this.setLayoutY(this.getLayoutY() - 10);
        }

        if (x > info.getWidth() / 2 && info.getEnemyY() + 80 < y) {
            this.setLayoutY(this.getLayoutY() + 10);
        }

        info.setEnemyY((int) this.getLayoutY());
    }
}
