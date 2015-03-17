/**
 * Created by m4dguy on 24.02.2015.
 */
public class MineLayer extends NPC {

    int mines;
    int distance;

    static final int maxMines = 4;
    static final int travelDistance = 100;

    public MineLayer(Engine e){
        super(e);
        model = new Mesh("gfx/floatingmine.vo");
        mines = 0;
        distance = 0;
    }

    public void act(){
        //TODO: change movement
        dirX = 1f;
        dirY = 1f;

        //lay mine
        if((distance == travelDistance) && (mines < maxMines))  {
            engine.addEvent(new EventSpawnSleepingMine(this));
            distance = 0;
            ++mines;
        }
    }

    public int getScore(){
        return 1000;
    }

    public void collide(NPC other) {
        if(this.affiliation != other.affiliation) {
            destroy();
        }
    }

    public void destroy()
    {
        destroyed = true;
    }

}
