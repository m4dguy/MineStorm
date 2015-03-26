/**
 * Created by m4dguy on 12.03.2015.
 */
public class EventMovePlayer extends EngineEvent {

    float dirX;
    float dirY;

    public EventMovePlayer(NPC s, float dx, float dy){
        super(s);
        dirX = dx;
        dirY = dy;
    }

    public boolean execute(){
        engine.player.setDirection(dirX, dirY);
        engine.player.accelerate(dirX, dirY);
        return true;
    }

}
