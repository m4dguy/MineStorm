/**
 * Created by m4dguy on 09.04.2015.
 */
public class Explosion extends Entity {

    protected static final float EXPLOSIONMAX = 220;
    protected static final float SCALESTEP = 7f;

    public Explosion(Engine e){
        super(e);
        affiliation = Engine.affiliation.NEUTRAL;
        Mesh mesh = MeshLoader.loadVectorObject("gfx/explosion.vo");
        model = new MeshModifier(mesh);
        model.scaling = 1f;
    }

    public void act()
    {
        if(model.scaling >= EXPLOSIONMAX)
            destroy();

        model.scaling += SCALESTEP;
    }

    public void destroy()
    {
        destroyed = true;
    }

    public int getScore() {return 0;}

    public void collide(Entity other) {}
}
