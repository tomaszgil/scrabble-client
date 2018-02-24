package sample;

import sample.models.Game;
import sample.models.Player;
import sample.models.Room;

import java.util.ArrayList;

public final class State {
    private static Player player;
    private static Game game;
    private static ArrayList<Room> roomList;
    private static ArrayList<Player> otherPlayers;
    private static Room room;

    public State() {
        player = null;
        game = null;
        roomList = null;
        room = null;
        otherPlayers = null;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        State.player = player;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        State.game = game;
    }

    public static ArrayList<Room> getRoomList() {
        return roomList;
    }

    public static void setRoomList(ArrayList<Room> roomList) {
        State.roomList = roomList;
    }

    public static Room getRoom() {
        return room;
    }

    public static void setRoom(Room room) {
        State.room = room;
    }

    public static ArrayList<Player> getOtherPlayers() {
        return otherPlayers;
    }

    public static void setOtherPlayers(ArrayList<Player> otherPlayers) {
        State.otherPlayers = otherPlayers;
    }
}
