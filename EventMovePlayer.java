/**
 * Set the player's direction and speed.
 */
public class EventMovePlayer extends EngineEvent {

    float dirX;
    float dirY;

    /**
     * Simple initialization.
     * @param s sender
     * @param dx x component of directional vector
     * @param dy y component of directional vector
     */
    public EventMovePlayer(NPC s, float dx, float dy){
        super(s);
        dirX = dx;
        dirY = dy;
    }

    /**
     * Change player direction.
     * @return always true
     */
    public boolean execute(){
        engine.player.setDirection(dirX, dirY);
        engine.player.accelerate(dirX, dirY);
        return true;
    }

}
