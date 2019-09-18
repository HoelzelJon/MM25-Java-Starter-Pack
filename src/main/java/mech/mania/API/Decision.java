package mech.mania.API;

/**
 * Class to store the actions to be executed on the next turn by a single unit.
 * Created to be serialized to send to the game engine as a JSON object.
 */
public class Decision {
    private int priority;
    private Direction[] movement;
    private Direction attack;
    private int unitId;

    public Decision(int priority, Direction[] movement, Direction attack, int unitId){
        this.priority = priority;
        this.movement = movement;
        this.attack = attack;
        this.unitId = unitId;
    }

    public Direction getAttack() {
        return attack;
    }

    public Direction[] getMovement() {
        return movement;
    }

    public int getPriority() {
        return priority;
    }

    public int getUnitId() {
        return unitId;
    }
}
