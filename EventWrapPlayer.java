/**
 * Created by m4dguy on 16.03.2015.
 */
public class EventWrapPlayer extends EngineEvent {

    public EventWrapPlayer(NPC s) {super(s);}

    public boolean execute()
    {
        engine.player.x = (engine.player.x<0)? Engine.fieldWidth : engine.player.x;
        engine.player.y = (engine.player.y<0)? Engine.fieldHeight : engine.player.y;
        engine.player.x = (engine.player.x>Engine.fieldWidth)? 0 : engine.player.x;
        engine.player.y = (engine.player.y>Engine.fieldHeight)? 0 : engine.player.y;
        return true;
    }

}
