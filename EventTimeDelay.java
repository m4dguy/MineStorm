/**
 * Executes the given EngineEvent after some milliseconds.
 */
public class EventTimeDelay extends EngineEvent {

    long delay;
    long startTime;
    EngineEvent delayedEvent;

    /**
     * Simple initialization.
     * @param s sender
     * @param d time delay (milliseconds)
     * @param e EngineEvent to be fired after delay
     */
    public EventTimeDelay(Entity s, long d, EngineEvent e) {
        super(s);
        delay = d;
        delayedEvent = e;
        startTime = System.currentTimeMillis();
    }

    /**
     * Execute the Event if the specified number of ticks has expired.
     * @return false until execution time reached
     */
    public boolean execute()
    {
        if(System.currentTimeMillis() < (startTime + delay)){
            return false;
        }else{
            engine.addEvent(delayedEvent);
            return true;
        }
    }
}
