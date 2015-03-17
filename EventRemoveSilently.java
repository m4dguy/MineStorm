/**
 * remove sender silently (without awarding points)
 * Created by m4dguy on 09.03.2015.
 */

public class EventRemoveSilently extends EngineEvent{

    public EventRemoveSilently(NPC s){super(s);}

    public boolean execute()
    {
        engine.npcs.remove(sender);
        return true;
    }
}
