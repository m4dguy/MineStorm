/**
 * Created by m4dguy on 12.03.2015.
 */
public class EventPlayerShoot extends EngineEvent{

    public EventPlayerShoot(NPC s) {super(s);}

    public boolean execute()
    {
        float dx, dy, norm;
        dx = sender.x - engine.player.x;
        dy = sender.y - engine.player.y;

        if((dx == 0) && (dy == 0))
            return true;

        Shot s = new Shot(engine, engine.player.x, engine.player.y, dx, dy);
        s.activate();
        s.affiliation = Engine.affiliation.SHOT;
        engine.addNPC(s);

        return true;
    }
}
