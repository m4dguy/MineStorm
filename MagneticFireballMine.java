/**
 * Created by m4dguy on 24.02.2015.
 */
public class MagneticFireballMine extends Entity {

    public MagneticFireballMine(Engine e){
        super(e);
        model = MeshLoader.loadVectorObject("gfx/fireballmine.vo");
    }

    public void act(){
        float dx, dy;
        dx = engine.player.x - this.x;
        dy = engine.player.y - this.y;
        setDirection(dx, dy);
    }

    public void destroy() {
        if (size == Engine.LARGESIZE) {
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.MEDIUMSIZE, Engine.MEDIUMSPEED));
        }

        if (size == Engine.MEDIUMSIZE) {
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
            engine.addEvent(new EventSpawnMagneticFireballMine(this, Engine.SMALLSIZE, Engine.FASTSPEED));
        }

        engine.addEvent(new EventSpawnFireball(this));
        destroyed = true;
    }

    public int getScore(){
        if (size == Engine.LARGESIZE)
            return 750;

        if (size == Engine.MEDIUMSIZE)
            return 785;

        if (size == Engine.SMALLSIZE)
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
