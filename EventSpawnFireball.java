/**
 * spawn a fireball (shot) directed at the player
 * Created by m4dguy on 09.03.2015.
 */

public class EventSpawnFireball extends EngineEvent{

    public EventSpawnFireball(NPC s){super(s);}

    public boolean execute()
    {
        Shot s = new Shot(engine);

        float dx, dy;
        dx = engine.player.x - sender.getPositionX();
        dy = engine.player.y - sender.getPositionY();

        //TODO: check/ fix fireball speed
        s.affiliation = Engine.affiliation.ENEMY;
        s.speed = Engine.MEDIUMSPEED;
        s.setSize(Engine.MEDIUMSIZE);
        s.setPosition(sender.getPositionX(), sender.getPositionY());
        s.setDirection(dx, dy);
        s.setAngle(dx, dy);
        engine.addNPC(s);

        return true;
    }
}
