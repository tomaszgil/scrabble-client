package sample.models;

public class Room {
    private Integer id;
    private String name;
    private Integer freeSlots;

    public Room(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.freeSlots = 4;
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

    public boolean occupySlot() {
        if (freeSlots > 0) {
            freeSlots -= 1;
            return true;
        }

        return false;
    }
}
