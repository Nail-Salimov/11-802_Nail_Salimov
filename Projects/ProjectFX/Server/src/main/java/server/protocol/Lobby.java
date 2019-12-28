package server.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Lobby {

    private List<Room> rooms;

    public Lobby() {
        this.rooms = new CopyOnWriteArrayList<>();
    }

    public Room addInRoom(Server.ClientHandler client) {
        synchronized (this) {
            Room freeRoom = null;
            for (Room r : rooms) {
                if (r.isFree()) {
                    freeRoom = r;
                    break;
                }
            }

            if (freeRoom != null){
                freeRoom.add(client);
            }else {
                freeRoom = new Room();
                rooms.add(freeRoom);
                freeRoom.add(client);
            }
            return freeRoom;
        }
    }

    /*
 private Set<String> set;
    private Map<String, Room> map;
    public Lobby() {
        this.set = new CopyOnWriteArraySet<>();
        this.map = new HashMap<>();
    }

    public void createNewRoom(String code) {
        if (set.contains(code)) {
            throw new IllegalStateException("this room already exists");
        }

        set.add(code);
        Room room = new Room();
        map.put(code, room);
    }

    public void addClient(String code, Server.ClientHandler client) {
        synchronized (this) {
            Room room = map.get(code);
            room.add(client);
        }
    }

    public Server.ClientHandler getOpponent(String code, Server.ClientHandler client) {
        synchronized (this) {
            Room room = map.get(code);
            return room.getOpponent(client);
        }
    }

    public boolean roomIsFree(String code) {
        synchronized (this) {
            Room room = map.get(code);
            return room.roomIsFree();
        }
    }

    public boolean roomIsExist(String code) {
        return set.contains(code);
    }

    public void clean(List<Server.ClientHandler> clients){

    }


 */
}
