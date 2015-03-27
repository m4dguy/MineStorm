/**
 * Created by m4dguy on 10.03.2015.
 */
public class EventSpawnMagneticFireballMine extends EngineEvent{

    float size, speed;
    public EventSpawnMagneticFireballMine(Entity s, float f, float v){
        super(s);
        size = f;
        speed = v;
    }

    public boolean execute()
    {
        MagneticFireballMine mine = new MagneticFireballMine(engine);
        mine.setSize(size);
        mine.setSpeed(speed);
        mine.setPosition(sender.x, sender.y);
        engine.addEntity(mine);
        return true;
    }
}

