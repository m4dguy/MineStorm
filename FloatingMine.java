
/**
 * Created by m4dguy on 24.02.2015.
 */
public class FloatingMine extends NPC {

    public FloatingMine(Engine e){
        super(e);
        model = MeshLoader.loadVectorObject("gfx/floatingmine.vo");

        float dx = engine.rand.nextFloat();
        float dy = engine.rand.nextFloat();
        dx *= (engine.rand.nextFloat() < 0.5f)? 1f : -1f;
        dy *= (engine.rand.nextFloat() < 0.5f)? 1f : -1f;
        setDirection(dx, dy);
    }

    public void act(){
        //this enemy is stupid; its not doing anything smart
    }

    public void destroy() {
        if (size == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (size == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        destroyed = true;
    }

    public void collide(NPC other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

    public int getScore(){
        if (size == Engine.LARGESIZE)
            return 100;

        if (size == Engine.MEDIUMSIZE)
            return 135;

        if (size == Engine.SMALLSIZE)
            return 200;

        //debug
        return 1337;
    }
}
