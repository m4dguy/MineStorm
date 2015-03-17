/**
 * Created by m4dguy on 10.03.2015.
 */
public class EventSpawnMagneticFireballMine extends EngineEvent{

    float size, speed;
    public EventSpawnMagneticFireballMine(NPC s, float f, float v){
        super(s);
        size = f;
        speed = v;
    }

    public boolean execute()
    {
        MagneticFireballMine mine = new MagneticFireballMine(engine);
        mine.setSize(size);
        mine.setSpeed(speed);
        engine.addNPC(mine);
        return true;
    }
}

