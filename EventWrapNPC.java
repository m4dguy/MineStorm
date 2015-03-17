/**
 * Created by m4dguy on 16.03.2015.
 */
public class EventWrapNPC extends EngineEvent {

    public EventWrapNPC(NPC s) {super(s);}

    public boolean execute()
    {
        sender.x = (sender.x<0)? Engine.fieldWidth : sender.x;
        sender.y = (sender.y<0)? Engine.fieldHeight : sender.y;
        sender.x = (sender.x>Engine.fieldWidth)? 0 : sender.x;
        sender.y = (sender.y>Engine.fieldHeight)? 0 : sender.y;
        return true;
    }
}
