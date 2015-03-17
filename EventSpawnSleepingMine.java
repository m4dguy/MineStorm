/**
 * Created by m4dguy on 09.03.2015.
 */
public class EventSpawnSleepingMine extends EngineEvent{
    public EventSpawnSleepingMine(NPC s) {super(s);}

    public boolean execute(){
        SleepingMine mine = new SleepingMine(engine);
        mine.setPosition(sender.getPositionX(), sender.getPositionY());
        engine.addNPC(mine);
        return true;
    }
}
