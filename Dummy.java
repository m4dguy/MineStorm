/**
 * Created by m4dguy on 09.04.2015.
 */
public class Dummy extends Entity {

    public Dummy(Engine e){
        super(e);
        affiliation = Engine.affiliation.NEUTRAL;
    }

    public void act() {}

    public void destroy() {}

    public int getScore() {return 0;}

    public void collide(Entity other) {}

}
