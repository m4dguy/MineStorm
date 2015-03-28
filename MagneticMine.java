/**
 * Created by m4dguy on 24.02.2015.
 */
public class MagneticMine extends Entity {

    public MagneticMine(Engine e){
        super(e);
        Mesh mesh = MeshLoader.loadVectorObject("gfx/magneticmine.vo");
        model = new MeshModifier(mesh);
    }

    public void act(){
        float dx = engine.player.x - this.x;
        float dy = engine.player.y - this.y;
        setDirection(dx, dy);
    }

    public void destroy() {
        if (model.scaling == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (model.scaling == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        destroyed = true;
    }


    public int getScore() {
        if (model.scaling == Engine.LARGESIZE)
            return 500;

        if (model.scaling == Engine.MEDIUMSIZE)
            return 535;

        if (model.scaling == Engine.SMALLSIZE)
            return 600;

        //debug
        return 1337;
    }

    public void collide(Entity other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

}
