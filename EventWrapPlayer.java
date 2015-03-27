/**
 * Fired if the Player leaves the game field bounds.
 * Wraps the Player position.
 */
public class EventWrapPlayer extends EngineEvent {

    /**
     * Simple initialization.
     * @param s a Dummy representing the Player
     */
    public EventWrapPlayer(NPC s) {super(s);}

    /**
     * Update the Player position as a wrap-around.
     * @return always true
     */
    public boolean execute()
    {
        engine.player.x = (engine.player.x<0)? Engine.fieldWidth : engine.player.x;
        engine.player.y = (engine.player.y<0)? Engine.fieldHeight : engine.player.y;
        engine.player.x = (engine.player.x>Engine.fieldWidth)? 0 : engine.player.x;
        engine.player.y = (engine.player.y>Engine.fieldHeight)? 0 : engine.player.y;
        return true;
    }

}
