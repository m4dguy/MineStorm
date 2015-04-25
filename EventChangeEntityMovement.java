/**
 * Created by m4dguy on 24.04.2015.
 */
public class EventChangeEntityMovement extends EngineEvent {

    float speed;
    float dirX;
    float dirY;
    public EventChangeEntityMovement(Entity s, float v, float dx, float dy)
    {
        super(s);
        speed = v;
        dirX = dx;
        dirY = dy;
    }

    public boolean execute()
    {
        sender.setSpeed(speed);
        sender.setDirection(dirX, dirY);
        return true;
    }
}
