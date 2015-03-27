/**
 * Created by m4dguy on 10.03.2015.
 */
public class EventSpawnFireballMine extends EngineEvent{

    float size, speed;
    public EventSpawnFireballMine(Entity s, float f, float v){
        super(s);
        size = f;
        speed = v;
    }

    public boolean execute()
    {
        FireballMine mine = new FireballMine(engine);
        mine.setSize(size);
        mine.setSpeed(speed);
        mine.setPosition(sender.x, sender.y);
        engine.addEntity(mine);
        return true;
    }
}
