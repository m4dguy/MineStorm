/**
 * Created by m4dguy on 24.02.2015.
 */
public class MagneticMine extends NPC {

    public MagneticMine(Engine e){
        super(e);
        model = MeshLoader.loadVectorObject("gfx/magneticmine.vo");
    }

    public void act(){
        float dx = engine.player.x - this.x;
        float dy = engine.player.y - this.y;
        setDirection(dx, dy);
    }

    public void destroy() {
        if (size == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (size == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnMagneticMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        destroyed = true;
    }


    public int getScore() {
        if (size == Engine.LARGESIZE)
            return 500;

        if (size == Engine.MEDIUMSIZE)
            return 535;

        if (size == Engine.SMALLSIZE)
            return 600;

        //debug
        return 1337;
    }

    public void collide(NPC other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

}
