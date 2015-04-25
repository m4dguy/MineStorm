/**
 * Created by m4dguy on 24.02.2015.
 */

public class Player extends Entity{

    public Player(Engine e) {
        Mesh mesh = MeshLoader.loadVectorObject("gfx/player.vo");
        model = new MeshModifier(mesh);

        engine = e;
        model.scaling = Engine.LARGESIZE;
        affiliation = Engine.affiliation.PLAYER;
        reset();
    }

    public void reset(){
        destroyed = false;
        active = true;
        setPosition((Engine.fieldWidth/2f), (Engine.fieldHeight/2f));
        model.setRotation(0);
        speed = 0;
    }

    public void act(){
        speed *= .975f;
    }

    public void destroy() {
        destroyed =true;
        active = false;

        EventPlayerDeath evDeath = new EventPlayerDeath(this);
        EventTickDelay evDelay = new EventTickDelay(this, 50, evDeath);
        engine.addEvent(evDelay);
        engine.addEvent(new EventSpawnExplosion(this));
        engine.addEvent(new EngineEventSpawnScrap(this));
    }

    public void collide(Entity other){
        destroy();
    }

    public int getScore(){
        return 0;
    }
}
