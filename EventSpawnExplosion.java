/**
 * Spawn an explosion at the position of given Entity.
 */
public class EventSpawnExplosion extends EngineEvent {

    /**
     * Simple initialization.
     * @param s sender
     */
    public EventSpawnExplosion(Entity s) {super(s);}

    /**
     * Spawn the explosion.
     * @return always true
     */
    public boolean execute()
    {
        Explosion ex = new Explosion(engine);
        ex.x = sender.x;
        ex.y = sender.y;
        engine.addEntity(ex);

        return true;
    }
}
