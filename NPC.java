import java.awt.*;

/**
 * Created by m4dguy on 24.02.2015.
 */

public abstract class NPC {

    protected int life;

    //shape modifiers
    protected float x = 0f;
    protected float y = 0f;
    protected float size = 1f;
    protected float angle = 0f;

    protected float dirX = 0f;
    protected float dirY = 0f;
    protected float speed = 1f;

    protected Engine.affiliation affiliation = Engine.affiliation.ENEMY;
    protected Mesh model;
    protected boolean destroyed = false;
    protected boolean active = false;

    public Engine engine;

    public NPC() {this(null);}
    public NPC(Engine e) {
        engine = e;
    }

    //defines a specific enemy action
    //such includes movement and sending events to the engine
    public abstract void act();

    //sets the enemy as active
    public void activate() {
        active = true;
        destroyed = false;
    }

    //play animation and set to destroyed
    //do specific action if required
    public abstract void destroy();

    //return score
    public abstract int getScore();

    //interaction upon collision
    public abstract void collide(NPC other);

    //simple movement
    public void move(){
        x += dirX * speed;
        y += dirY * speed;
    }

    //tell engine if enemy is active
    public boolean active(){ return active; }

    public void setSize(float s){
        size = s;
    }

    public void setDirection(float dx, float dy) {
        float norm = (float) Math.sqrt(dx*dx + dy*dy);
        dirX = dx / norm;
        dirY = dy / norm;
    }

    public void setSpeed(float s) {
        speed = s;
    }

    public void setAngle(float dx, float dy){
        angle = (float)Math.atan(dx / dy);
    }

    public void setAngle(float a) {
        angle = a;
    }

    public void setPosition(float px, float py) {
        x = px;
        y = py;
    }

    public float getPositionX() {
        return x;
    }

    public float getPositionY() { return y; }

    public float getSize(){return size;}

    //tell the engine if enemy still active
    public boolean destroyed(){
        return destroyed;
    }

    public float distance(NPC other) {
        float dist, dx, dy;
        dx = this.x - other.x;
        dy = this.y - other.y;
        dist = (float)Math.sqrt(dx*dx + dy*dy);
        return dist;
    }

    public boolean checkBoundCollision(NPC other) {
        if(this.affiliation == other.affiliation)
            return false;

        if(this.destroyed() || !this.active || other.destroyed() || !other.active)
            return false;

        float dist = distance(other);
        return dist < (this.model.bound*this.size + other.model.bound*other.size);
    }
    public boolean checkCollision(Mesh m){return model.collision(m);}

    public void render(Image img){
        model.transformationReset();
        model.scale(size);

        if(angle != 0)
            model.rotate(angle);

        model.displace(x, y);
        model.render(img);
    }
}
