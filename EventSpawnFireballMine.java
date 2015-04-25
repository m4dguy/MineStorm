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

    public boolean execute() {
        FireballMine mine = new FireballMine(engine);
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
