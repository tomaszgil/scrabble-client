package sample;

import sample.models.*;

import java.util.ArrayList;

public final class State {
    private static Player player;
    private static Board board;
    private static AvailableLetters availableLetters;
    private static ArrayList<Room> roomList;
    private static ArrayList<Player> otherPlayers;
    private static Room room;
    private static boolean myTurn;

    static {
        player = null;
        board = null;
        roomList = null;
        room = null;
        otherPlayers = null;
        myTurn = false;
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

    public static AvailableLetters getAvailableLetters() {
        return availableLetters;
    }

    public static void setAvailableLetters(AvailableLetters availableLetters) {
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

    public static boolean isMyTurn() {
        return myTurn;
    }

    public static void setMyTurn(boolean myTurn) {
        State.myTurn = myTurn;
    }
}
