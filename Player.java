import java.awt.Image;

/**
 * Created by m4dguy on 24.02.2015.
 */

public class Player {

    protected float x;
    protected float y;
    protected float angle;
    protected final float size = Engine.LARGESIZE;

    protected float speed;
    protected float dirX;
    protected float dirY;
    protected float accelerationX;
    protected float accelerationY;

    protected Mesh model;
    protected Engine engine;

    public Player(Engine e) {
        model = MeshLoader.loadVectorObject("gfx/player.vo");
        engine = e;
        resetPlayer();
    }

    public void resetPlayer(){
        angle = 0;
        speed = 0;

        accelerationX = 0;
        accelerationY = 0;
    }

    public void escape(){
        x = engine.rand.nextFloat() * Engine.fieldWidth;
        y = engine.rand.nextFloat() * Engine.fieldHeight;
        resetPlayer();
    }

    public void rotate(float a){
        angle = a;
    }

    public void setAngle(float dx, float dy) { angle = (float)Math.atan(dx / dy); }

    public void move() {
        x += dirX * speed;
        y += dirY * speed;
        speed *= .1f;
        speed += .5/(float)Math.sqrt(accelerationX*accelerationX + accelerationY*accelerationY+1);
        accelerationX = 0f;
        accelerationY = 0f;
    }

    public void setDirection(float dx, float dy) {
        float norm = (float)Math.sqrt(dx*dx + dy*dy);
        dirX = dx / norm;
        dirY = dy / norm;
        angle = (float)Math.atan(dx / dy);
    }

    public void accelerate(float forceX, float forceY) {
        accelerationX = forceX;
        accelerationY = forceY;
    }

    public void render(Image img){
        model.transformationReset();
        model.scale(size);
        model.rotate(angle);
        model.displace(x, y);
        model.render(img);
    }

    public Mesh getModel(){return model;}

}
