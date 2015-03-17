/**
 * Created by m4dguy on 09.03.2015.
 */
public abstract class EngineEvent {
    public NPC sender;
    public Engine engine;

    public EngineEvent(NPC s){
        sender = s;
        engine = s.engine;
    }
    public abstract boolean execute();
}
