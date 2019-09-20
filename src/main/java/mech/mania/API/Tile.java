package mech.mania.API;

/**
 * Class to represent a single space on the board.
 * Created to be deserialized from the game state JSON object.
 */
public class Tile {
    private long id;
    private Unit unit; // the Unit present on this tile (or null, if no unit is present)
    private Type type; // the type of tile this is (see Type enum below)
    private int hp; // health of this tile (only important for DESTRUCTIBLE type)

    public enum Type {
        BLANK, // blank tile -- nothing is on it (except maybe a unit)
        DESTRUCTIBLE, // destructible terrain -- becomes BLANK after hp is reduced to or below 0
        // units cannot be on DESTRUCTIBLE-type tiles
        INDESTRUCTIBLE // indestructible terrain -- becomes BLANK after hp is reduced to or below 0
        // units cannot be on INDESTRUCTIBLE-type tiles
    }

    public Tile(long id, Unit unit, Type type, int hp) {
        this.id = id;
        this.unit = unit;
        this.type = type;
        this.hp = hp;
    }

    public long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    /**
     * Used for printing out human-readable string of the map
     * @return a 3-character representation of this tile
     */
    public String shortString() {
//        if (unit != null) {
//            return " *" + unit.getId() + "* ";
//        }
//        return String.format(" %03d ", id);
        if (type == Type.DESTRUCTIBLE) {
            return " D ";
        } else if (type == Type.INDESTRUCTIBLE) {
            return " I ";
        } else if (unit != null) {
            return " " + unit.getId() + " ";
        } else return " . ";
    }
}
