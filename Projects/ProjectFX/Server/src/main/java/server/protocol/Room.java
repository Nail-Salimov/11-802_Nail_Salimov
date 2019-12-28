package server.protocol;

public class Room {
    private Server.ClientHandler clientRight;
    private Server.ClientHandler clientLeft;


    public boolean isFree() {
        System.out.println();
        return clientRight == null || clientLeft == null;
    }


    public void add(Server.ClientHandler client) {

        if (clientRight == null) {
            clientRight = client;
        } else if (clientLeft == null) {
            clientLeft = client;
        } else {
            throw new IllegalStateException("room already is full");
        }
    }

    public Server.ClientHandler getOpponent(Server.ClientHandler thisClient) {
        if ((clientRight != thisClient) && (clientRight != null)) {
            return clientRight;
        } else if ((clientLeft != thisClient) && (clientLeft != null)) {
            return clientLeft;
        } else {
            throw new IllegalStateException("клиент не из этой комнаты");
        }
    }

    public boolean isRight(Server.ClientHandler thisClient) {
        System.out.println(clientRight + " " + clientLeft);
        if ((clientRight == thisClient) && (clientRight != null)) {
            return true;
        } else if ((clientLeft == thisClient) && (clientLeft != null)) {
            return false;
        } else {
            throw new IllegalStateException("клиент не из этой комнаты");
        }
    }

    /*
    public void add(Server.ClientHandler client) {

        if (clientRight == null) {
            clientRight = client;
        } else if (clientLeft == null) {
            clientLeft = client;
        } else {
            throw new IllegalStateException("room already is full");
        }
    }

    public Server.ClientHandler getOpponent(Server.ClientHandler thisClient) {
        if ((clientRight != thisClient) && (clientRight != null)) {
            return clientRight;
        } else if ((clientLeft != thisClient) && (clientLeft != null)) {
            return clientLeft;
        } else {
            throw new IllegalStateException("клиент не из этой комнаты");
        }
    }

    public boolean roomIsFree() {
        return clientRight == null || clientLeft == null;
    }

    public Server.ClientHandler getClientRight() {
        return clientRight;
    }

    public void setClientRight(Server.ClientHandler clientRight) {
        this.clientRight = clientRight;
    }

    public Server.ClientHandler getClientLeft() {
        return clientLeft;
    }

    public void setClientLeft(Server.ClientHandler clientLeft) {
        this.clientLeft = clientLeft;
    }

     */
}
