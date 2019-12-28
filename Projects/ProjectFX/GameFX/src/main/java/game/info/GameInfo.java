package game.info;

public class GameInfo {
    private double ballX;
    private double ballY;

    private int enemyY;

    private int playerY;

    private int width;
    private int height;

    private boolean ready;

    private boolean isRight;

    private boolean newRound;
    private boolean updateRound;

    public int getWidth() {
        return width;
    }

    public GameInfo setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public GameInfo setHeight(int height) {
        this.height = height;
        return this;
    }

    public double getBallX() {
        return ballX;
    }

    public GameInfo setBallX(double ballX) {
        this.ballX = ballX;
        return this;
    }

    public double getBallY() {
        return ballY;
    }

    public GameInfo setBallY(double ballY) {
        this.ballY = ballY;
        return this;
    }

    public int getEnemyY() {
        return enemyY;
    }

    public GameInfo setEnemyY(int enemyY) {
        this.enemyY = enemyY;
        return this;
    }

    public int getPlayerY() {
        return playerY;
    }

    public GameInfo setPlayerY(int playerY) {
        this.playerY = playerY;
        return this;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "ballX=" + ballX +
                ", ballY=" + ballY +
                ", enemyY=" + enemyY +
                ", playerY=" + playerY +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isNewRound() {
        return newRound;
    }

    public void setNewRound(boolean newRound) {
        this.newRound = newRound;
    }

    public boolean isUpdateRound() {
        return updateRound;
    }

    public void setUpdateRound(boolean updateRound) {
        this.updateRound = updateRound;
    }
}
