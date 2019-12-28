package server.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.state.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private Lobby lobby;

    public Server() {
        clients = new CopyOnWriteArrayList<ClientHandler>();
        lobby = new Lobby();
    }

    public void startServer(int port) {
        try {
            start(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private void start(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            serverSocket.close();
        }
    }

    public class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private Scanner scanner;
        private ObjectMapper mapper;

        private boolean isFree;
        private boolean partnerReady;
        private ClientHandler partner;
        private String code;
        private boolean isRight = false;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            scanner = new Scanner(System.in);
            mapper = new ObjectMapper();
            isFree = true;
            partnerReady = false;

            start();
        }

        @Override
        public void run() {

            try {
                while (true) {

                    String command = in.readLine();

                    if (command == null) {
                        disconnect();
                        break;
                    }

                    JsonNode rootNode = mapper.readTree(command);
                    String type = rootNode.path("type").asText();
                    String description = rootNode.path("description").asText();

                    if (type.equals("Code")) {
                        Room room = lobby.addInRoom(this);
                        boolean isRight = room.isRight(this);
                        while (room.isFree());

                        System.out.println("out");
                        partner = room.getOpponent(this);

                        Message message;
                        if (!isRight) {
                            message = new Message("Partner found", "left");
                        }else {
                            message = new Message("Partner found", "right");
                        }

                        String json = mapper.writeValueAsString(message);
                        partner.out.write(json + "\n");
                        partner.out.flush();

                        /*
                        boolean isRight;
                        if (!lobby.roomIsExist(description)) {
                            lobby.createNewRoom(description);
                            lobby.addClient(description, this);
                            code = description;
                            isRight = false;
                        } else {
                            lobby.addClient(description, this);
                            code = description;
                            isRight = true;
                        }
                        while (lobby.roomIsFree(description)) ;

                        partner = lobby.getOpponent(description, this);

                        Message message;
                        if (!isRight) {
                            message = new Message("Partner found", "left");
                        }else {
                            message = new Message("Partner found", "right");
                        }

                        String json = mapper.writeValueAsString(message);
                        partner.out.write(json + "\n");
                        partner.out.flush();

                         */

                    } else if (type.equals("Move")) {
                        Message message = new Message("Enemy move", description);
                        String json = mapper.writeValueAsString(message);
                        partner.out.write(json + "\n");
                        partner.out.flush();
                    } else  if(type.equals("Ball")){
                        partner.out.write(command + "\n");
                        partner.out.flush();
                    }else  if (type.equals("New Round")){
                        partner.out.write(command + "\n");
                        partner.out.flush();
                    }


                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private void disconnect() {                //отключение от сервера
            try {
                clients.remove(this);
                clientSocket.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
