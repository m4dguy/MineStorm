/**
 * Created by m4dguy on 10.03.2015.
 */
public class EventSpawnFloatingMine extends EngineEvent{

    float size, speed;
    public EventSpawnFloatingMine(Entity s, float f, float v){
        super(s);
        size = f;
        speed = v;
    }

    public boolean execute()
    {
        FloatingMine mine = new FloatingMine(engine);
        mine.setSize(size);
        mine.setSpeed(speed);
        mine.setPosition(sender.x, sender.y);
        engine.addEntity(mine);
        return true;
    }
}
