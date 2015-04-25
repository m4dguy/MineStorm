
/**
 * Created by m4dguy on 24.02.2015.
 */
public class FloatingMine extends Entity {

    public FloatingMine(Engine e){
        super(e);

        Mesh mesh =  MeshLoader.loadVectorObject("gfx/floatingmine.vo");
        model = new MeshModifier(mesh);

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
        if (model.scaling == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (model.scaling == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnFloatingMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        engine.addEvent(new EventSpawnExplosion(this));
        destroyed = true;
    }

    public void collide(Entity other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

    public int getScore(){
        if (model.scaling == Engine.LARGESIZE)
            return 100;

        if (model.scaling == Engine.MEDIUMSIZE)
            return 135;

        if (model.scaling == Engine.SMALLSIZE)
            return 200;

        //debug
        return 1337;
    }
}
