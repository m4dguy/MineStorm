/**
 * Created by m4dguy on 25.04.2015.
 */
public class Particles extends Entity{

    static final float MAXSIZE = 120;
    protected static final float SCALESTEP = 6f;

        public Particles(Engine e){
            super(e);
            affiliation = Engine.affiliation.NEUTRAL;
            Mesh mesh = MeshLoader.loadVectorObject("gfx/particles.vo");
            model = new MeshModifier(mesh);
            model.scaling = MAXSIZE;
        }

        public void act()
        {
            if(model.scaling <= 0)
                destroy();

            model.scaling -= SCALESTEP;
        }

        public void destroy()
        {
            destroyed = true;
        }

        public int getScore() {return 0;}

        public void collide(Entity other) {}

}
