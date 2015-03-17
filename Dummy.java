/**
 * Dummy NPC for external events
 * Created by m4dguy on 12.03.2015.
 */

public final class Dummy extends NPC{
    public Dummy(Engine e){super(e);}
    public void init() {}
    public void act() {destroy();}
    public void collide(NPC other) {}
    public void destroy() {destroyed = true; active = false;}
    public int getScore() {return 0;}
}
