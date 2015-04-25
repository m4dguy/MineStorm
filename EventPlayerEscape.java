/**
 * Created by m4dguy on 27.03.2015.
 */
public class EventPlayerEscape extends EngineEvent {

    public EventPlayerEscape(Entity s){super(s);}

    /**
     * Change player location.
     * @return always true
     */
    public boolean execute(){
        engine.addEvent(new EventSpawnParticles(sender));
        sender.x = engine.rand.nextFloat() * Engine.fieldWidth;
        sender.y = engine.rand.nextFloat() * Engine.fieldHeight;
        sender.model.setRotation(0);
        sender.speed = 0;

        return true;
    }
}
