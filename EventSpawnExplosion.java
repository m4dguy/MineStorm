/**
 * Spawn an explosion at the given position.
 */
public class EventSpawnExplosion extends EngineEvent {

    /**
     * Simple initialization.
     * @param s sender
     */
    public EventSpawnExplosion(NPC s) {super(s);}

    /**
     * Spawn the explosion.
     * @return always true
     */
    public boolean execute()
    {
        //TODO: explosion!
        //engine.addNPC(new Dummy(engine));


        return true;
    }
}
