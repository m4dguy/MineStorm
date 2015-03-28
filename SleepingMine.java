/**
 * Created by m4dguy on 09.03.2015.
 */
public class SleepingMine extends Entity {

    public SleepingMine(Engine e){
        super(e);
        affiliation = Engine.affiliation.NEUTRAL;
        Mesh mesh = MeshLoader.loadVectorObject("gfx/sleepingmine.vo");
        model = new MeshModifier(mesh);

    }

    public void act()
    {
        //does nothing
        //will be spawn point for another mine
    }

    public void destroy()
    {
        destroyed = true;
    }

    public int getScore() {return 0;}

    public void collide(Entity other) {}

}
