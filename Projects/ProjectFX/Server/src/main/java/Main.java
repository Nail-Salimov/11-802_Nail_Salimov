import server.protocol.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(4321);
    }
}
