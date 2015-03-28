/**
 * Created by m4dguy on 24.02.2015.
 */
public class MagneticFireballMine extends Entity {

    public MagneticFireballMine(Engine e){
        super(e);
        Mesh mesh = MeshLoader.loadVectorObject("gfx/fireballmine.vo");
        model = new MeshModifier(mesh);
    }

    public void act(){
        float dx, dy;
        dx = engine.player.x - this.x;
        dy = engine.player.y - this.y;
        setDirection(dx, dy);
    }

    public void destroy() {
        if (model.scaling == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (model.scaling == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        engine.addEvent(new EventSpawnFireball(this));
        destroyed = true;
    }

    public int getScore(){
        if (model.scaling == Engine.LARGESIZE)
            return 750;

        if (model.scaling == Engine.MEDIUMSIZE)
            return 785;

        if (model.scaling == Engine.SMALLSIZE)
            return 850;

        //debug
        return 1337;
    }

    public void collide(Entity other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

}
