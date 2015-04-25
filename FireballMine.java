
/**
 * Created by m4dguy on 24.02.2015.
 */
public class FireballMine extends Entity {

    public FireballMine(Engine e){
        super(e);
        Mesh mesh = MeshLoader.loadVectorObject("gfx/fireballmine.vo");
        model = new MeshModifier(mesh);

        float dx = engine.rand.nextFloat();
        float dy = engine.rand.nextFloat();
        dx *= (engine.rand.nextFloat() < 0.5f)? 1f : -1f;
        dy *= (engine.rand.nextFloat() < 0.5f)? 1f : -1f;
        setDirection(dx, dy);
    }

    public void act(){
        //stupid enemy without AI
    }

    public void destroy() {
        if (model.scaling == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnFireballMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnFireballMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (model.scaling == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnFireballMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnFireballMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        engine.addEvent(new EventSpawnFireball(this));
        engine.addEvent(new EventSpawnExplosion(this));
        destroyed = true;
    }

    public int getScore() {
        if (model.scaling == Engine.LARGESIZE)
            return 325;

        if (model.scaling == Engine.MEDIUMSIZE)
            return 360;

        if (model.scaling == Engine.SMALLSIZE)
            return 425;

        //debug
        return 1337;
    }

    public void collide(Entity other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

}
