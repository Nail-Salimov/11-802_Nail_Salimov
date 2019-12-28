package online;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.info.GameInfo;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private ObjectMapper mapper;

    private BufferedReader br;
    private BufferedWriter bw;

    private String code;
    private GameInfo info;

    public GameClient(String code, GameInfo info) {
        this.code = code;
        this.info = info;
    }

    private boolean stop;

    public void start(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            scanner = new Scanner(System.in);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            mapper = new ObjectMapper();
            stop = false;

            Message message = new Message("Code", code);
            out.write(mapper.writeValueAsString(message) + "\n");
            out.flush();

            String jsonReply = in.readLine();
            JsonNode node = mapper.readTree(jsonReply);
            String type = node.path("type").asText();
            String description = node.path("description").asText();

            if (type.equals("Partner found")) {
                info.setReady(true);
                if (description.equals("left")) {
                    info.setRight(false);
                } else if (description.equals("right")) {
                    info.setRight(true);
                } else {
                    throw new IllegalStateException("not correct description: " + description);
                }

                new ClientReader().start();
                new ClientWriter().start();
            } else {
                throw new IllegalStateException("not correct response: " + type);
            }


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void disconnect() {
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private class ClientReader extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    if (clientSocket.isClosed() || stop) {
                        break;
                    }

                    String inMessage = in.readLine();
                    JsonNode rootNode = mapper.readTree(inMessage);
                    String type = rootNode.path("type").asText();
                    String description = rootNode.path("description").asText();

                    if (type.equals("Partner found")) {
                        info.setReady(true);
                    } else if (type.equals("Enemy move")) {

                        info.setEnemyY(Integer.parseInt(description));
                    } else if (type.equals("Ball")) {

                        String[] strings = description.split(" ");
                        info.setBallX(Double.parseDouble(strings[0]));
                        info.setBallY(Double.parseDouble(strings[1]));
                    } else if (type.equals("New Round")) {
                        info.setUpdateRound(true);
                    } else {
                        throw new IllegalStateException("not correct response");
                    }

                } catch (IOException e) {

                }
            }
        }
    }

    public void stopped(){
        stop = true;
    }

    private class ClientWriter extends Thread {


        private int coordinate = 0;

        @Override
        public void run() {
            try {
                while (true) {
                    if (clientSocket.isClosed() || stop) {
                        disconnect();
                        break;
                    }

                    int y = info.getPlayerY();
                    if (coordinate != y) {
                        coordinate = y;
                        String description = String.valueOf(coordinate);
                        Message message = new Message("Move", description);
                        out.write(mapper.writeValueAsString(message) + "\n");
                        out.flush();
                    }


                    if (info.isNewRound()) {
                        info.setNewRound(false);
                        Message message = new Message("New Round", "");
                        out.write(mapper.writeValueAsString(message) + "\n");
                        out.flush();
                    }

                    /*
                    if (1 == 2) {
                        String description = String.valueOf(info.getBallX()) + " " + String.valueOf(info.getBallY());

                        Message message = new Message("Ball", description);
                        out.write(mapper.writeValueAsString(message) + "\n");
                        out.flush();
                    }
                     */
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
