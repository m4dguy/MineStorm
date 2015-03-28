/**
 * Created by m4dguy on 27.03.2015.
 */
public class EventPlayerDeath extends EngineEvent {

    public EventPlayerDeath(Entity s){
        super(s);
    }

    /**
     * Tell the engine that the Player died.
     * @return always true
     */
    public boolean execute(){
        engine.playerDeath();
        return true;
    }
}
