/**
 * Created by m4dguy on 27.02.2015.
 */

public class Shot extends Entity{

    public static final int shotLife = (int)(0.8 * Engine.fieldWidth);

    public Shot(Engine e) {
        this(e, 0f, 0f, 0f, 0f);
    }

    public Shot(Engine e, float px, float py, float dx, float dy) {
        super(e);
        Mesh mesh = MeshLoader.loadVectorObject("gfx/shot.vo");
        model = new MeshModifier(mesh);

        model.setScaling(Engine.SMALLSIZE);
        health = shotLife;
        x = px;
        y = py;

        this.setDirection(dx, dy);
        this.setAngle(dx, dy);
        speed = 5f;
    }

    public void act()
    {
        if(active()) {
            health -= speed;
            active = health > 0;
        }

        if(health <= 0){
            engine.addEvent(new EventRemoveSilently(this));
        }
    }

    public void setPosition(float px, float py) {
        x = px;
        y = py;
    }

    public void collide(Entity other) {
        this.active = false;
        engine.addEvent(new EventRemoveSilently(this));
    }

    public void destroy() {
        destroyed = true;
    }

    public int getScore(){return 110;}
}
