/**
 * Spawn a Fireball (Shot) directed at the Player.
 * The Fireball is spawned at the sender's position.
 */

public class EventSpawnFireball extends EngineEvent{

    /**
     * Simple initialization.
     * @param s sender
     */
    public EventSpawnFireball(NPC s){super(s);}

    /**
     * Spawn the fireball and set its direction and speed.
     * @return always true
     */
    public boolean execute()
    {
        Shot s = new Shot(engine);

        float dx, dy;
        dx = engine.player.x - sender.getPositionX();
        dy = engine.player.y - sender.getPositionY();

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
