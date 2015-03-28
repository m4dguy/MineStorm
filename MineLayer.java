/**
 * Created by m4dguy on 24.02.2015.
 */
public class MineLayer extends Entity {

    protected int mines;                      //number of mines lied
    protected float targetX, targetY;         //target point

    static final float targetDist = 2f;
    static final float targetDistSq = targetDist*targetDist;
    static final int maxMines = 4;

    public MineLayer(Engine e){
        super(e);
        Mesh mesh = MeshLoader.loadVectorObject("gfx/floatingmine.vo");
        model = new MeshModifier(mesh);

        mines = 0;
        setNewTarget();
    }

    public void act(){
        float dx = targetX - this.x;
        float dy = targetY - this.y;
        setDirection(dx, dy);

        //lay mine
        if((dx*dx+dy*dy) < targetDistSq) {
            engine.addEvent(new EventSpawnSleepingMine(this));
            ++mines;
            setNewTarget();
        }
    }

    public void setNewTarget() {
        targetX = engine.rand.nextFloat() % engine.fieldWidth;
        targetY = engine.rand.nextFloat() % engine.fieldHeight;
    }

    public int getScore(){
        return 1000;
    }

    public void collide(Entity other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

    public void destroy()
    {
        destroyed = true;
    }

}
