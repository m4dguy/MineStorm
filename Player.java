/**
 * Created by m4dguy on 24.02.2015.
 */

public class Player extends Entity{

    public Player(Engine e) {
        model = MeshLoader.loadVectorObject("gfx/player.vo");
        engine = e;
        size = Engine.LARGESIZE;
        affiliation = Engine.affiliation.PLAYER;
        reset();
    }

    public void reset(){
        destroyed = false;
        active = true;
        setPosition((Engine.fieldWidth/2f), (Engine.fieldHeight/2f));
        angle = 0;
        speed = 0;
    }

    public void act(){
        speed *= .99f;
    }

    public void destroy() {
        destroyed =true;
        active = false;
        engine.addEvent(new EventPlayerDeath(this));
    }

    public void collide(Entity other){
        destroy();
    }

    public int getScore(){
        return 0;
    }
}
