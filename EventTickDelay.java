/**
 * Executes the given EngineEvent after some ticks.
 */
public class EventTickDelay extends EngineEvent {

    long delay;
    EngineEvent delayedEvent;

    /**
     * Simple initialization.
     * @param s sender
     * @param d time delay (milliseconds)
     * @param e EngineEvent to be fired after delay
     */
    public EventTickDelay(Entity s, long d, EngineEvent e) {
        super(s);
        delay = d;
        delayedEvent = e;
    }

    /**
     * Execute the Event if the specified number of ticks has expired.
     * @return false until execution tick reached
     */
    public boolean execute()
    {
        if(delay > 0){
            --delay;
            return false;
        }else{
            engine.addEvent(delayedEvent);
            return true;
        }
    }
}
