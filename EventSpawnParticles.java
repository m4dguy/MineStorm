/**
 * Created by m4dguy on 25.04.2015.
 */
public class EventSpawnParticles extends EngineEvent {

    /**
     * Simple initialization.
     * @param s sender
     */
    public EventSpawnParticles(Entity s) {super(s);}

    /**
     * Spawn partilces.
     * @return always true
     */
    public boolean execute()
    {
        Particles parta = new Particles(engine);
        parta.x = sender.x;
        parta.y = sender.y;
        engine.addEntity(parta);
        return true;
    }
}
