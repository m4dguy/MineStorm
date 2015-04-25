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

        //find and replace suitable SleepingMine
        Entity e;
        for(int i=0; i<engine.npcs.size(); ++i)
        {
            e = engine.getEntity(i);
            if(e instanceof SleepingMine)
            {
                mine.setPosition(e.getPositionX(), e.getPositionY());
                engine.addEntity(mine);
                engine.removeEntity(i);
                break;
            }
        }

        return true;
    }
}
