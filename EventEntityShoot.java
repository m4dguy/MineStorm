/**
 * Create a shot pointing to the sender's (mouse pointer) position.
 * The Shot appears at the exact Player position.
 */
public class EventEntityShoot extends EngineEvent{

    float dirX;
    float dirY;

    /**
     * Simple initialization.
     * Submit a sender who will spawn the shot.
     * @param s sender
     * @param tx x component of the target point for the shot
     * @param ty y component of the target point for the shot
     */
    public EventEntityShoot(Entity s, int tx, int ty) {
        super(s);
        dirX = tx - sender.x;
        dirY = ty - sender.y;
    }

    /**
     * Create the Shot and add it to the Engine.
     * @return always true.
     */
    public boolean execute()
    {
        if((dirX == 0) && (dirY == 0))
            return true;

        Shot s = new Shot(engine, sender.x, sender.y, dirX, dirY);
        s.activate();
        s.affiliation = sender.affiliation;
        engine.addEntity(s);

        return true;
    }
}
