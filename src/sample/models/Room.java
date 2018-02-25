package sample.models;

public class Room {
    private Integer id;
    private String name;
    private Integer freeSlots;

    public Room(String name, int freeSlots) {
        this.name = name;
        this.freeSlots = freeSlots;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(Integer freeSlots) {
        this.freeSlots = freeSlots;
    }
}
