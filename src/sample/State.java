package sample;

import sample.models.Board;
import sample.models.Letter;
import sample.models.Player;
import sample.models.Room;

import java.util.ArrayList;

public final class State {
    private static Player player;
    private static Board board;
    private static ArrayList<Letter> availableLetters;
    private static ArrayList<Room> roomList;
    private static ArrayList<Player> otherPlayers;
    private static Room room;

    public State() {
        player = null;
        board = null;
        availableLetters = null;
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

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        State.board = board;
    }

    public static ArrayList<Letter> getAvailableLetters() {
        return availableLetters;
    }

    public static void setAvailableLetters(ArrayList<Letter> availableLetters) {
        State.availableLetters = availableLetters;
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
