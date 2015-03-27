/**
 * Remove the sender silently (without awarding points).
 */
public class EventRemoveSilently extends EngineEvent{

    /**
     * Simple initialization.
     * @param s sender
     */
    public EventRemoveSilently(Entity s){super(s);}

    /**
     * Remove sender from the Engine's NPC list.
     * @return always true
     */
    public boolean execute()
    {
        engine.npcs.remove(sender);
        return true;
    }
}
