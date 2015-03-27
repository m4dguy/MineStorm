/**
 * The blueprint of an event.
 * Override execute() to define an action and manipulate the Engine.
 * Call super() constructor to make sure that sender and engine are defined.
 */
public abstract class EngineEvent {
    public NPC sender;
    public Engine engine;

    /**
     * Constructor which defines the Engine reference and the sender responsible for this EngineEvent.
     * @param s the NPC responsible for creating this EngineEvent.
     */
    public EngineEvent(NPC s){
        sender = s;
        engine = s.engine;
    }

    /**
     * Override this method for event execution.
     * Return true, if the event was successful.
     * If false is returned, the EngineEvent remains in the Engine's event list and will be executed again in the next tick.
     * @return true, if the event was executed successfully.
     * @see Engine.checkEvents()
     */
    public abstract boolean execute();
}
