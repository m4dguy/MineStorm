/**
 * Prevent an Entity from moving for given number of ticks.
 */
public class EventPinEntity extends EngineEvent {

    long ticks;
    protected float entitySpeed;

    /**
     * Simple initialization.
     * @param s Entity which will be pinned down
     * @param t duration of event in ticks
     */
    public EventPinEntity(Entity s, long t){
        super(s);
        ticks = t;
        entitySpeed = s.speed;
        sender.speed = 0f;
    }

    /**
     * Prevent the Entity from moving and restore the former speed after ticks expired.
     * @return false until ticks are down to 0
     */
    public boolean execute()
    {
        if(ticks > 0) {
            --ticks;
            return false;
        }else{
            sender.speed = entitySpeed;
            return true;
        }
    }
}
