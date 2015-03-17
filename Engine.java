import java.awt.*;
import java.util.Random;
import java.util.Vector;


/**
 * Created by m4dguy on 24.02.2015.
 */

public class Engine {

    public class Controller extends Thread {
        boolean terminate = false;
        Engine owner;

        public Controller(Engine e){
            super();
            owner = e;
        }

        public void run(){
            long time = (long)((1f/ticks) * 1000f);
            while(!terminate){
                owner.tick();

                //TODO: adapt timing
                try{
                    this.sleep(time);
                }catch(InterruptedException e){
                    System.out.println(e);
                    System.exit(1);
                }
            }
        }
    }

    //affiliations
    public static enum affiliation {ALLY, NEUTRAL, ENEMY, SHOT};

    //constants
    public static final float collisionDistance = 2f;

    public static final float SMALLSIZE = 10f;
    public static final float MEDIUMSIZE = 20f;
    public static final float LARGESIZE = 30f;
    public static final float[] sizes = {SMALLSIZE, MEDIUMSIZE, LARGESIZE};

    public static final float SLOWSPEED = .5f;
    public static final float MEDIUMSPEED = 1f;
    public static final float FASTSPEED = 1.5f;
    public static final float[] speed = {SLOWSPEED, MEDIUMSPEED, FASTSPEED};

    public static final int MAXENEMIES = 50;               //total number of enemies (mines, fireballs, minelayers)
    public static final int MAXSHOTS = 5;                  //max number of shots

    //variables
    public static int ticks = 60;                           //maximum ticks per second
    public static int fieldWidth = 480;                     //game board dimension
    public static int fieldHeight = 620;                    //game board dimension

    protected int lives;
    protected int level;
    protected int score;
    protected int shots;

    Random rand;
    protected Player player;
    protected Vector<NPC> npcs;
    protected Vector<EngineEvent> events;
    protected Controller controller;

    public Engine() {
        rand = new Random();

        player = new Player(this);

        npcs = new Vector<NPC>(MAXENEMIES+MAXSHOTS);
        events = new Vector<EngineEvent>(50);
        controller = new Controller(this);
    }

    public void init() {
        lives = 4;
        level = 1;
        score = 0;
        shots = 0;
        player.resetPlayer();
        npcs.clear();
    }

    public void startGame() {
        init();
        loadLevel(level);
        controller.start();
    }

    public void tick() {
        //move everything
        for(NPC n : npcs)
        {
            if(n.active() && !n.destroyed())
            {
                n.act();
                n.move();
            }
        }

        player.move();

        //check collisions and events
        checkWrap();
        checkCollisions();
        checkEvents();
        removeDestroyedNPCs();

        if(levelClear()) {
            ++level;
            resetLevel();
        }
    }

    //placeholder
    public void gameOver(){
        System.exit(0);
    }

    //TODO: placeholder. use level loader
    public void loadLevel(int lvl) {
        player.x = fieldWidth / 2;
        player.y = fieldHeight / 2;
        npcs.clear();

        for(int i=0; i<3; ++i){
            FloatingMine mine = new FloatingMine(this);
            mine.setSize(LARGESIZE);
            mine.setSpeed(MEDIUMSPEED);
            mine.setPosition(rand.nextInt(fieldWidth), rand.nextInt(fieldHeight));
            addNPC(mine);
        }
    }

    public void resetLevel() {
        loadLevel(level);
    }

    public void playerDeath()
    {
        if(lives <= 0)
            gameOver();

        --lives;
        resetLevel();
    }

    public void renderGame(Image img) {
        player.render(img);

        for(int i=0; i<npcs.size(); ++i) {
            if (npcs.get(i).active()) {
                npcs.get(i).render(img);
            }
        }
    }

    public void checkCollisions() {
        //collision of NPCs
        for(int i=0; i<npcs.size(); ++i){
            for(int j=i; j<npcs.size(); ++j) {
                if(i==j)
                    continue;

                if(npcs.get(i).checkBoundCollision(npcs.get(j))) {
                    npcs.get(i).collide(npcs.get(j));
                    npcs.get(j).collide(npcs.get(i));
                }

            }
        }

        //collision with player
        Dummy dummy = new Dummy(this);
        dummy.x = player.x;
        dummy.y = player.y;
        /*for(int i=0; i<npcs.size(); ++i){
            if(npcs.get(i).collision(player.getModel())) {
                playerDeath();
            }
        }*/
    }

    public void addNPC(NPC n){
        n.activate();
        npcs.add(n);
    }

    public void addEvent(EngineEvent e){
        events.add(e);
    }

    public void checkEvents() {
        for(int i=0; i<events.size(); ++i) {
            //TODO: possible read/write conflict
            if(events.get(i).execute()) {
                events.remove(i);
            }
        }
    }

    public void removeDestroyedNPCs() {
        //Vector<NPC> cleaned = new Vector<NPC>(npcs.size());

        for(int i=0; i<npcs.size(); ++i) {
            //TODO: possible read/write conflict
            if(npcs.get(i).destroyed()) {
                score += npcs.get(i).getScore();
                npcs.remove(i);
            }else {
                //cleaned.add(npcs.get(i));
            }
        }
        //npcs = cleaned;
    }

    public boolean levelClear(){

        for(int i=0; i<npcs.size(); ++i)
            if(!npcs.get(i).destroyed())
                return false;

        return true;
    }

    //check for wraparound
    public void checkWrap(){
        for(NPC n: npcs) {
            if(n.x<0 || n.x>fieldWidth || n.y<0 || n.y>fieldHeight) {
                addEvent(new EventWrapNPC(n));
            }
        }

        if(player.x<0 || player.x>fieldWidth || player.y<0 || player.y>fieldHeight) {
            Dummy dummy = new Dummy(this);
            addEvent(new EventWrapPlayer(dummy));
        }
    }
}
