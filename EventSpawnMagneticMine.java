/**
 * Created by m4dguy on 10.03.2015.
 */
public class EventSpawnMagneticMine extends EngineEvent{

    float size, speed;
    public EventSpawnMagneticMine(Entity s, float f, float v){
        super(s);
        size = f;
        speed = v;
    }

    public boolean execute()
    {
        MagneticMine mine = new MagneticMine(engine);
        mine.setSize(size);
        mine.setSpeed(speed);
        mine.setPosition(sender.x, sender.y);
        engine.addEntity(mine);
        return true;
    }
}
