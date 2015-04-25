/**
 * Created by m4dguy on 25.04.2015.
 */
public class Scrap extends Entity{

    protected static final float MAXSIZE = 180;
    protected static final float SCALESTEP = 3f;

    public Scrap(Engine e){
        super(e);
        affiliation = Engine.affiliation.NEUTRAL;
        Mesh mesh = MeshLoader.loadVectorObject("gfx/scrap.vo");
        model = new MeshModifier(mesh);
        model.scaling = 1f;
    }

    public void act()
    {
        if(model.scaling >= MAXSIZE)
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
