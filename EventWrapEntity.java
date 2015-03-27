/**
 * Fired if the sending NPC leaves the game field bounds.
 * Wraps the NPC position.
 */
public class EventWrapEntity extends EngineEvent {

    /**
     * Simple initialization.
     * @param s NPC to be wrapped
     */
    public EventWrapEntity(Entity s) {super(s);}

    /**
     * Update the NPC position as a wrap-around.
     * @return always true
     */
    public boolean execute()
    {
        sender.x = (sender.x<0)? Engine.fieldWidth : sender.x;
        sender.y = (sender.y<0)? Engine.fieldHeight : sender.y;
        sender.x = (sender.x>Engine.fieldWidth)? 0 : sender.x;
        sender.y = (sender.y>Engine.fieldHeight)? 0 : sender.y;
        return true;
    }
}
