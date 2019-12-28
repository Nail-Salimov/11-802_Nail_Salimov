package game.component;


import game.info.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import online.GameClient;


public class Field extends Application {
    private Pane root;
    private final int WIDTH = 1000;
    private final int HEIGHT = 400;

    private static Player keyboardPlayer;
    private static Player otherPlayer;
    private Ball ball;

    private Line line;
    private static GameInfo gameInfo;

    private int playerScore1 = 0;
    private int playerScore2 = 0;

    private AnimationTimer timer;

    private Text textScore1;
    private Text textScore2;

    private Stage primaryStage;

    private GameClient gameClient;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setResizable(false);
        primaryStage.setTitle("Pong");
        Scene scene = new Scene(menu());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buttonsSingle() {
        primaryStage.getScene().setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.W) {
                if (keyboardPlayer.getLayoutY() > 5) {
                    gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY() - 30);
                }
            }

            if (keyEvent.getCode() == KeyCode.S) {
                if (keyboardPlayer.getLayoutY() < HEIGHT - 85) {
                    gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY() + 30);
                }
            }
        });
    }

    private void buttonsMulti() {
        primaryStage.getScene().setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.W) {
                if (keyboardPlayer.getLayoutY() > 5) {
                    gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY() - 30);
                }
            }

            if (keyEvent.getCode() == KeyCode.S) {
                if (keyboardPlayer.getLayoutY() < HEIGHT - 85) {
                    gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY() + 30);
                }
            }

        });

        primaryStage.setOnCloseRequest(windowEvent -> {
            if (gameClient != null) {
                gameClient.stopped();
            }
        });
    }

    private void buttonsForTwo() {
        primaryStage.getScene().setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.W) {
                if (keyboardPlayer.getLayoutY() > 5) {
                    gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY() - 30);
                }
            }

            if (keyEvent.getCode() == KeyCode.S) {
                if (keyboardPlayer.getLayoutY() < HEIGHT - 85) {
                    gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY() + 30);
                }
            }

            if (keyEvent.getCode() == KeyCode.UP) {
                if (otherPlayer.getLayoutY() > 5) {
                    gameInfo.setEnemyY((int) otherPlayer.getLayoutY() - 30);
                }
            }

            if (keyEvent.getCode() == KeyCode.DOWN) {
                if (otherPlayer.getLayoutY() < HEIGHT - 85) {
                    gameInfo.setEnemyY((int) otherPlayer.getLayoutY() + 30);
                }
            }
        });
    }

    private Parent menu() {
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        Text menuText = new Text("PONG");
        menuText.setLayoutX(WIDTH / 2 - 75);
        menuText.setLayoutY(100);
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 50);
        menuText.setFont(font);

        Button btnSingle = new Button();
        Button btnMulti = new Button();
        Button btnForTwo = new Button();
        btnSingle.setText("Bot");
        btnMulti.setText("Multiplayer");
        btnForTwo.setText("For two");

        btnSingle.setTranslateX(WIDTH / 2 - 60);
        btnSingle.setTranslateY(200);
        btnMulti.setTranslateX(WIDTH / 2 - 60);
        btnMulti.setTranslateY(250);
        btnForTwo.setTranslateX(WIDTH / 2 - 60);
        btnForTwo.setTranslateY(300);

        btnSingle.setPrefSize(120, 40);
        btnMulti.setPrefSize(120, 40);
        btnForTwo.setPrefSize(120, 40);

        btnSingle.setOnAction(actionEvent -> {
            gameInfo = new GameInfo();
            Plate playerPlate = new Plate(10, 80, Color.YELLOW);
            keyboardPlayer = new KeyboardPlayer(playerPlate);


            Plate botPlate = new Plate(10, 80, Color.WHITE);
            otherPlayer = new BotPlayer(botPlate);

            keyboardPlayer.setLayoutX(0);
            keyboardPlayer.setLayoutY(400 / 2 - 40);
            otherPlayer.setLayoutX(1000 - 10);
            otherPlayer.setLayoutY(400 / 2 - 40);

            Scene gameScene = new Scene(createContent());
            primaryStage.setScene(gameScene);
            primaryStage.show();
            buttonsSingle();
        });

        btnMulti.setOnAction(actionEvent -> {
            gameInfo = new GameInfo();

            gameClient = new GameClient("2", gameInfo);
            gameClient.start("localhost", 4321);

            Plate playerPlate = new Plate(10, 80, Color.YELLOW);
            keyboardPlayer = new KeyboardPlayer(playerPlate);


            Plate botPlate = new Plate(10, 80, Color.WHITE);
            otherPlayer = new OnlinePlayer(botPlate);

            if (!gameInfo.isRight()) {
                keyboardPlayer.setLayoutX(0);
                keyboardPlayer.setLayoutY(400 / 2 - 40);
                otherPlayer.setLayoutX(1000 - 10);
                otherPlayer.setLayoutY(400 / 2 - 40);
            } else {
                keyboardPlayer.setLayoutX(1000 - 10);
                keyboardPlayer.setLayoutY(400 / 2 - 40);
                otherPlayer.setLayoutX(0);
                otherPlayer.setLayoutY(400 / 2 - 40);
            }

            Scene gameScene = new Scene(createContent());
            primaryStage.setScene(gameScene);
            primaryStage.show();
            buttonsMulti();
        });

        btnForTwo.setOnAction(actionEvent -> {
            gameInfo = new GameInfo();
            Plate playerPlate = new Plate(10, 80, Color.YELLOW);
            keyboardPlayer = new KeyboardPlayer(playerPlate);


            Plate botPlate = new Plate(10, 80, Color.WHITE);
            otherPlayer = new KeyboardPlayer2(botPlate);

            keyboardPlayer.setLayoutX(0);
            keyboardPlayer.setLayoutY(400 / 2 - 40);
            otherPlayer.setLayoutX(1000 - 10);
            otherPlayer.setLayoutY(400 / 2 - 40);

            Scene gameScene = new Scene(createContent());
            primaryStage.setScene(gameScene);
            primaryStage.show();
            buttonsForTwo();
        });

        root.getChildren().addAll(btnSingle, btnMulti, btnForTwo, menuText);
        return root;
    }

    private Parent createContent() {

        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        root.setStyle("-fx-background-color: black");

        textScore1 = new Text(String.valueOf(playerScore1));
        textScore1.setLayoutX(100);
        textScore1.setLayoutY(50);
        textScore1.setFont(new Font(20));
        textScore1.setStroke(Color.WHITE);

        textScore2 = new Text(String.valueOf(playerScore2));
        textScore2.setLayoutX(900);
        textScore2.setLayoutY(50);
        textScore2.setFont(new Font(20));
        textScore2.setStroke(Color.WHITE);

        line = new Line(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        line.setStroke(Color.WHITE);

        Circle circle = new Circle(5);
        circle.setFill(Color.WHITE);
        ball = new Ball(circle, keyboardPlayer, otherPlayer);
        ball.setLayoutX(WIDTH / 2);
        ball.setLayoutY(HEIGHT / 2);


        gameInfo.setPlayerY((int) keyboardPlayer.getLayoutY());
        gameInfo.setBallY(ball.getLayoutY());
        gameInfo.setBallX(ball.getLayoutX());
        gameInfo.setWidth(WIDTH);
        gameInfo.setHeight(HEIGHT);
        gameInfo.setEnemyY((int) otherPlayer.getLayoutY());

        root.getChildren().addAll(line, keyboardPlayer, otherPlayer, ball, textScore1, textScore2);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameUpdate();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        timer.start();

        return root;
    }

    private void gameUpdate() {
        ball.action(gameInfo);
        keyboardPlayer.action(gameInfo);
        otherPlayer.action(gameInfo);

        if (gameInfo.isUpdateRound()) {
            ball.defaultSettings(gameInfo);
            if (ball.getLayoutX() < WIDTH / 2) {
                playerScore2++;
            } else {
                playerScore1++;
            }
            textScore1.setText(String.valueOf(playerScore1));
            textScore2.setText(String.valueOf(playerScore2));
            gameInfo.setUpdateRound(false);
        }

        if (gameInfo.getBallX() < 0) {
            gameInfo.setNewRound(true);
            playerScore2++;
            ball.defaultSettings(gameInfo);
            textScore1.setText(String.valueOf(playerScore1));
            textScore2.setText(String.valueOf(playerScore2));

        }

        if (gameInfo.getBallX() > 1000) {
            gameInfo.setNewRound(true);
            playerScore1++;
            ball.defaultSettings(gameInfo);
            textScore1.setText(String.valueOf(playerScore1));
            textScore2.setText(String.valueOf(playerScore2));
        }

    }

    public static Player getKeyboardPlayer() {
        return keyboardPlayer;
    }

    public static void setKeyboardPlayer(Player keyboardPlayer) {
        Field.keyboardPlayer = keyboardPlayer;
    }

    public static Player getOtherPlayer() {
        return otherPlayer;
    }

    public static void setOtherPlayer(Player otherPlayer) {
        Field.otherPlayer = otherPlayer;
    }

    public static GameInfo getGameInfo() {
        return gameInfo;
    }

    public static void setGameInfo(GameInfo gameInfo) {
        Field.gameInfo = gameInfo;
    }
}
