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
                engine.addEvent(new EventPinEntity(mine, 60));

                break;
            }
        }

        return true;
    }
}
