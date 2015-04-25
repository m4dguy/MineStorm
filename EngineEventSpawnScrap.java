/**
 * Created by m4dguy on 25.04.2015.
 */
public class EngineEventSpawnScrap extends EngineEvent {

    /**
     * Simple initialization.
     * @param s sender
     */
    public EngineEventSpawnScrap(Entity s) {super(s);}

    /**
     * Spawn the explosion.
     * @return always true
     */
    public boolean execute()
    {
        Scrap sc = new Scrap(engine);
        sc.x = sender.x;
        sc.y = sender.y;
        engine.addEntity(sc);

        return true;
    }
}
