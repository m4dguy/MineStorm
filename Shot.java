/**
 * Created by m4dguy on 27.02.2015.
 */

public class Shot extends NPC{

    public static final int shotLife = (int)(0.9 * Engine.fieldWidth);

    public Shot(Engine e) {
        this(e, 0f, 0f, 0f, 0f);
    }

    public Shot(Engine e, float px, float py, float dx, float dy) {
        super(e);
        model = MeshLoader.loadVectorObject("gfx/shot.vo");
        size = Engine.SMALLSIZE;
        life = shotLife;
        x = px;
        y = py;

        this.setDirection(dx, dy);
        this.setAngle(dx, dy);
        speed = 5f;
    }

    public void act()
    {
        if(active()) {
            life -= speed;
            active = life > 0;
        }

        if(life <= 0){
            engine.addEvent(new EventRemoveSilently(this));
        }
    }

    public void setPosition(float px, float py) {
        x = px;
        y = py;
    }

    public void collide(NPC other) {
        this.active = false;
        engine.addEvent(new EventRemoveSilently(this));
    }

    public void destroy() {
        destroyed = true;
    }

    public Mesh getModel(){return model;}

    public int getScore(){return 110;}
}
