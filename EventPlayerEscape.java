/**
 * Created by m4dguy on 27.03.2015.
 */
public class EventPlayerEscape extends EngineEvent {

    public EventPlayerEscape(Entity s){super(s);}

    /**
     * Change player direction.
     * @return always true
     */
    public boolean execute(){
        sender.x = engine.rand.nextFloat() * Engine.fieldWidth;
        sender.y = engine.rand.nextFloat() * Engine.fieldHeight;
        engine.player.resetPlayer();
        return true;
    }
}
