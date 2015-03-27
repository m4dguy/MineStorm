/**
 * Engine object.
 */

public abstract class Entity {

    protected int life;

    //shape modifiers
    protected float x = 0f;
    protected float y = 0f;
    protected float size = 1f;
    protected float angle = 0f;

    protected float dirX = 0f;
    protected float dirY = 0f;
    protected float speed = 1f;

    protected int health;               //not used in MineStorm
    protected Engine.affiliation affiliation = Engine.affiliation.ENEMY;
    protected Mesh model;
    protected boolean destroyed = false;
    protected boolean active = false;

    public Engine engine;

    public Entity() {this(null);}
    public Entity(Engine e) {
        engine = e;
    }

    //defines a specific enemy action
    //such includes movement and sending events to the engine

    /**
     * Defines an action specific to the Entity. This action is executed in every tick of the game.
     * Override this method to control movement, destruction, EngineEvent triggering and others.
     */
    public abstract void act();

    /**
     * Sets this Entity as active.
     * Active Entities are not destroyed and are allowed to use act() in every game tick.
     */
    public void activate() {
        active = true;
        destroyed = false;
    }

    /**
     * Use this to set the Entity's state to destroyed and (if necessary) )to trigger EngineEvents.
     */
    public abstract void destroy();

    /**
     * Return the score the destruction of this Entitys gives the player.
     * @return the awarded score.
     */
    public abstract int getScore();

    /**
     * Define the action that is triggered upon collision with another Entity.
     * @param other the colliding Entity.
     */
    public abstract void collide(Entity other);

    /**
     * Move the Entity into the current direction with given speed factor.
     */
    public void move(){
        x += dirX * speed;
        y += dirY * speed;
    }

    /**
     * Tell the Engine if this Entity is active.
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
     * Change the direction in which the Entity is moving with a vector.
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
     * Change the angle in which the Entity is facing with a vector.
     * The direction does not have an influence on the movement direction.
     * @param dx x component of the directional vector.
     * @param dy y component of the directional vector.
     */
    public void setAngle(float dx, float dy){
        angle = (float)Math.atan(dx / dy);
    }

    /**
     * Change the angle in which the Entity is facing (in radians).
     * The direction does not have an influence on the movement direction.
     * @param a the new angle.
     */
    public void setAngle(float a) {
        angle = a;
    }

    /**
     * Sets the new absolute position of the Entity.
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
    public float getPositionY() {
        return y;
    }

    /**
     * Returns the size of the Entity.
     * @return the Entity's size.
     */
    public float getSize(){
        return size;
    }

    /**
     * Tell the Engine if this Entity has been destroyed.
     * @return true, if the destroyed flag is set to true.
     */
    public boolean destroyed(){
        return destroyed;
    }

    /**
     * Calculates the distance to another Entity.
     * @param other the Entity for the distance check.
     * @return distance to the other Entity.
     * @see Entity.checkBoundCollision(Entity)
     */
    public float distance(Entity other) {
        float dist, dx, dy;
        dx = this.x - other.x;
        dy = this.y - other.y;
        dist = (float)Math.sqrt(dx*dx + dy*dy);
        return dist;
    }

    /**
     * Checks if the bounding spheres of this and the given Entity collide.
     * Very cheap to calculate.
     * @param other the other Entity for the collision check.
     * @return true, if the bounds collide
     */
    public boolean checkBoundCollision(Entity other) {
        if(this.affiliation == other.affiliation)
            return false;

        if(this.destroyed() || !this.active || other.destroyed() || !other.active)
            return false;

        float dist = distance(other);
        return dist < (this.model.bound*this.size + other.model.bound*other.size);
    }

    /**
     * Checks if two Entities collide.
     * Checks if one of the nodes intersect with one of the edge of the other Entity.
     * Calculates an exact distance and checks if it is smaller than a predefined collision distance.
     * @param other the other Entity.
     * @return true, if both collide.
     */
    public boolean checkPreciseCollision(Entity other){
        float distance;

        float pos = 0f;     //value of interpolator
        float x1, y1;       //position of point
        float x2, y2;       //closest position of point on line

        float px1, py1;     //position of first node
        float px2, py2;     //position of second node

        //first check:
        //all nodes of this mesh with all edges of the other mesh
        for(int i=0; i<model.nodes.length; ++i) {
            x1 = model.nodesDisplaced[i][0];
            y1 = model.nodesDisplaced[i][1];

            for(int j=0; j<other.model.edges.length; ++j) {
                px1 = other.model.nodesDisplaced[other.model.edges[j][0]][0];
                py1 = other.model.nodesDisplaced[other.model.edges[j][0]][1];
                px2 = other.model.nodesDisplaced[other.model.edges[j][1]][0];
                py2 = other.model.nodesDisplaced[other.model.edges[j][1]][1];

                pos = (px2 - x1)/(px1 - px2) + (py2 - y1)/(py1 - py2);

                if ((pos > 1f) || (pos < 0f))
                    return false;

                x2 = px1 * pos + px2 * (1f - pos);
                y2 = py1 * pos + py2 * (1f - pos);

                //calculate actual distance
                distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
                distance = (float) Math.sqrt(distance);
                if (distance <= Engine.collisionDistance)
                    return true;
            }
        }

        //second check:
        //all nodes of the mesh with all edges of this mesh
        for(int i=0; i<other.model.nodes.length; ++i) {
            x1 = other.model.nodesDisplaced[i][0];
            y1 = other.model.nodesDisplaced[i][1];

            for(int j=0; j<model.edges.length; ++j) {
                px1 = model.nodesDisplaced[model.edges[j][0]][0];
                py1 = model.nodesDisplaced[model.edges[j][0]][1];
                px2 = model.nodesDisplaced[model.edges[j][1]][0];
                py2 = model.nodesDisplaced[model.edges[j][1]][1];

                pos = (px2 - x1)/(px1 - px2) + (py2 - y1)/(py1 - py2);

                if ((pos > 1f) || (pos < 0f))
                    return false;

                x2 = px1 * pos + px2 * (1f - pos);
                y2 = py1 * pos + py2 * (1f - pos);

                //calculate actual distance
                distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
                distance = (float) Math.sqrt(distance);
                if (distance <= Engine.collisionDistance)
                    return true;
            }
        }

        return false;
    }

    /**
     * Checks if this Mesh and the given mesh collide.
     * Collision is checked in two steps:
     * Fuzzy collision of the bounding spheres first, exact collision of the Mesh nodes and edges afterwards.
     * @param other other Entity for collision check.
     * @return true, if both objects collide.
     * @see checkBoundCollision(Entity)
     * @see checkPreciseCollision(Entity)
     */
    public boolean checkCollision(Entity other){
        if(!checkBoundCollision(other))
            return false;

        return true;
        //return checkPreciseCollision(other);
    }
}
