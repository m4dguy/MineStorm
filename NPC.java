/**
 * Non-playable character object.
 *
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

    /**
     * Defines an action specific to the NPC. This action is executed in every tick of the game.
     * Override this method to control movement, destruction, EngineEvent triggering and others.
     */
    public abstract void act();

    /**
     * Sets this NPC as active.
     * Active NPCs are not destroyed and are allowed to use act() in every game tick.
     */
    public void activate() {
        active = true;
        destroyed = false;
    }

    /**
     * Use this to set the NPC's state to destroyed and (if necessary) )to trigger EngineEvents.
     */
    public abstract void destroy();

    /**
     * Return the score the destruction of this NPCs gives the player.
     * @return the awarded score.
     */
    public abstract int getScore();

    /**
     * Define the action that is triggered upon collision with another NPC.
     * @param other the colliding NPC.
     */
    public abstract void collide(NPC other);

    /**
     * Move the NPC into the current direction with given speed factor.
     */
    public void move(){
        x += dirX * speed;
        y += dirY * speed;
    }

    /**
     * Tell the Engine if this NPC is active.
     * @return true, if the active flag is set to true.
     */
    public boolean active(){ return active; }

    /**
     * Set the scaling factor for the internal Mesh object.
     * @param s scaling factor.
     */
    public void setSize(float s){
        size = s;
    }

    /**
     * Change the direction in which the NPC is moving with a vector.
     * This vector is internally normalized to unit length.
     * @param dx x component of the directional vector.
     * @param dy x component of the directional vector.
     */
    public void setDirection(float dx, float dy) {
        float norm = (float) Math.sqrt(dx*dx + dy*dy);
        dirX = dx / norm;
        dirY = dy / norm;
    }

    /**
     * Set the movement speed.
     * @param s the new movement speed.
     */
    public void setSpeed(float s) {
        speed = s;
    }

    /**
     * Change the angle in which the NPC is facing with a vector.
     * The direction does not have an influence on the movement direction.
     * @param dx x component of the directional vector.
     * @param dy y component of the directional vector.
     */
    public void setAngle(float dx, float dy){
        angle = (float)Math.atan(dx / dy);
    }

    /**
     * Change the angle in which the NPC is facing (in radians).
     * The direction does not have an influence on the movement direction.
     * @param a the new angle.
     */
    public void setAngle(float a) {
        angle = a;
    }

    /**
     * Sets the new absolute position of the NPC.
     * @param px x position.
     * @param py y position.
     */
    public void setPosition(float px, float py) {
        x = px;
        y = py;
    }

    /**
     * Get the x position.
     * @return x position.
     */
    public float getPositionX() {
        return x;
    }

    /**
     * Get the y position.
     * @return y position.
     */
    public float getPositionY() { return y; }

    /**
     * Returns the size of the NPC.
     * @return the NPC's size.
     */
    public float getSize(){
        return size;
    }

    /**
     * Tell the Engine if this NPC has been destroyed.
     * @return true, if the destroyed flag is set to true.
     */
    public boolean destroyed(){
        return destroyed;
    }

    /**
     * Calculates the distance to another NPC.
     * @param other the NPC for the distance check.
     * @return distance to the other NPC.
     * @see NPC.checkBoundCollision(NPC)
     */
    public float distance(NPC other) {
        float dist, dx, dy;
        dx = this.x - other.x;
        dy = this.y - other.y;
        dist = (float)Math.sqrt(dx*dx + dy*dy);
        return dist;
    }

    /**
     * Checks if the bounding spheres of this and the given NPC collide.
     * Very cheap to calculate.
     * @param other the other NPC for the collision check.
     * @return
     */
    public boolean checkBoundCollision(NPC other) {
        if(this.affiliation == other.affiliation)
            return false;

        if(this.destroyed() || !this.active || other.destroyed() || !other.active)
            return false;

        float dist = distance(other);
        return dist < (this.model.bound*this.size + other.model.bound*other.size);
    }

    /**
     * Checks if this Mesh and the given mesh collide.
     * Collision is checked in two steps:
     * fuzzy collision of the bounding spheres first, exact collision of the Mesh nodes and edges afterwards.
     * @param m
     * @return true, if both objects collide.
     */
    public boolean checkCollision(Mesh m){
        return model.collision(m);
    }
}
