/**
 * Create a shot pointing to the sender's (mouse pointer) position.
 * The Shot appears at the exact Player position.
 */
public class EventPlayerShoot extends EngineEvent{

    /**
     * Simple initialization.
     * Submit a Dummy containing the target point.
     * @param s sender
     */
    public EventPlayerShoot(NPC s) {super(s);}

    /**
     * Create the Shot and add it to the Engine.
     * @return always true.
     */
    public boolean execute()
    {
        float dx, dy;
        dx = sender.x - engine.player.x;
        dy = sender.y - engine.player.y;

        if((dx == 0) && (dy == 0))
            return true;

        Shot s = new Shot(engine, engine.player.x, engine.player.y, dx, dy);
        s.activate();
        s.affiliation = Engine.affiliation.SHOT;
        engine.addNPC(s);

        return true;
    }
}
