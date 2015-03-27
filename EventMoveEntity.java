/**
 * Set the Entity's direction and speed.
 */
public class EventMoveEntity extends EngineEvent {

    float dirX;
    float dirY;

    /**
     * Simple initialization.
     * @param s sender
     * @param dx x component of directional vector
     * @param dy y component of directional vector
     */
    public EventMoveEntity(Entity s, float dx, float dy){
        super(s);
        dirX = dx;
        dirY = dy;
    }

    /**
     * Change player direction.
     * @return always true
     */
    public boolean execute(){
        sender.setDirection(dirX, dirY);
        sender.setSpeed(Engine.FASTSPEED);
        return true;
    }
}
