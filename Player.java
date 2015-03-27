/**
 * Created by m4dguy on 24.02.2015.
 */

public class Player extends Entity{

    public Player(Engine e) {
        model = MeshLoader.loadVectorObject("gfx/player.vo");
        engine = e;
        size = Engine.LARGESIZE;
        affiliation = Engine.affiliation.PLAYER;
        resetPlayer();
    }

    public void resetPlayer(){
        angle = 0;
        speed = 0;
    }

    public void act(){
        setAngle(dirX / dirY);
        speed *= .99f;
    }

    public void destroy() {

    }

    public void collide(Entity other){
        destroy();
    }

    public int getScore(){
        return 0;
    }
}
