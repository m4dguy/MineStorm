import java.util.Random;
import java.util.Vector;

/**
 * The Game engine itself. Handles game state updating and object management.
 * Uses event-based system which fires if certain conditions are met.
 * Contains a controller for autonomous updating.
 * Also contains some helpers such as enums, constants and a random number generator.
 *
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
            long startTime, difference, sleepTime;
            final long maxTime = (long)(1000f/(float)ticks);
            while(!terminate){
                startTime = System.currentTimeMillis();

                owner.tick();

                difference = System.currentTimeMillis() - startTime;
                sleepTime = Math.max(0, maxTime - difference);

                try{
                    this.sleep(sleepTime);
                }catch(InterruptedException e){
                    System.out.println(e);
                    System.exit(1);
                }
            }
        }
    }

    //affiliations
    public static enum affiliation {PLAYER, ALLY, NEUTRAL, ENEMY};

    //constants
    public static final float collisionDistance = 2f;
    public static final float MAXSPEED = 2f;

    public static final float SMALLSIZE = 10f;
    public static final float MEDIUMSIZE = 20f;
    public static final float LARGESIZE = 30f;
    public static final float[] sizes = {SMALLSIZE, MEDIUMSIZE, LARGESIZE};

    public static final float SLOWSPEED = .5f;
    public static final float MEDIUMSPEED = 1f;
    public static final float FASTSPEED = 1.5f;
    public static final float[] speeds = {SLOWSPEED, MEDIUMSPEED, FASTSPEED};

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

    public Random rand;
    protected Player player;
    protected Vector<Entity> npcs;
    protected Vector<EngineEvent> events;
    protected Controller controller;

    public Engine() {
        rand = new Random();

        player = new Player(this);

        npcs = new Vector<Entity>(MAXENEMIES+MAXSHOTS);
        events = new Vector<EngineEvent>(50);
        controller = new Controller(this);
    }

    /**
     * Set up all variables s.t. everything is in default state.
     * @see public void startGame()
     */
    public void init() {
        lives = 4;
        level = 1;
        score = 0;
        shots = 0;
        player.reset();
        npcs.clear();
    }

    /**
     * Set up start conditions and run controller.
     */
    public void startGame() {
        init();
        loadLevel(level);
        controller.start();
    }

    /**
     * Update the game.
     * Move all objects and check for events.
     * @see Engine.Controller
     */
    public void tick() {
        //move everything
        for(Entity n : npcs)
        {
            if(n.active() && !n.destroyed())
            {
                n.act();
                n.move();
            }
        }

        player.act();
        player.move();

        //check collisions and events
        checkBorders();
        checkCollisions();
        checkEvents();
        removeDestroyedNPCs();

        if(levelClear()) {
            ++level;
            resetLevel();
        }
    }

    /**
     * Game over event.
     */
    //TODO: placeholder
    public void gameOver(){
        System.exit(0);
    }


    /**
     * Level loading method.
     * Changes the level according to the condition table.
     * @param lvl the number for accessing the level condition table.
     */
    //TODO: placeholder. use level loader
    public void loadLevel(int lvl) {
        //events.clear();
        player.reset();
        npcs.clear();

        for(int i=0; i<3; ++i){
            FireballMine mine = new FireballMine(this);
            mine.setSize(LARGESIZE);
            mine.setSpeed(MEDIUMSPEED);
            mine.setPosition(rand.nextInt(fieldWidth), rand.nextInt(fieldHeight));
            addEntity(mine);
        }
    }

    /**
     * Reloads the current level.
     * @see public void loadLevel()
     */
    public void resetLevel() {
        loadLevel(level);
    }

    /**
     * Event called if the player dies.
     * Results in loss of one life and reloading the level or game over.
     */
    public void playerDeath()
    {
        if(lives <= 0)
            gameOver();

        --lives;
        resetLevel();
    }

    /**
     * Check collisions in two steps.
     * First step is a cheap proximity check which uses NPC.checkBoundCollision().
     * Second step is precise distance calculation for the vector objects.
     */
    public void checkCollisions() {
        //collision of NPCs
        Entity n;
        for(int i=0; i<npcs.size(); ++i){
            n = npcs.get(i);
            for(int j=i; j<npcs.size(); ++j) {
                if(i==j)
                    continue;

                if(npcs.get(i).checkCollision(npcs.get(j))) {
                    n.collide(npcs.get(j));
                    npcs.get(j).collide(n);
                }
            }

            //collision with player
            if(player.checkCollision(n)) {
                //n.collide(player);
                player.collide(n);
            }
        }


        /*for(int i=0; i<npcs.size(); ++i){

        }*/
    }

    /**
     * Adds a new NPC to the game.
     * @param n NPC which is supposed to be added.
     */
    public void addEntity(Entity n){
        n.activate();
        npcs.add(n);
    }

    /**
     * Adds a new EngineEvent to the game.
     * @param e EngineEvent which is supposed to be added.
     */
    public void addEvent(EngineEvent e){
        events.add(e);
    }

    /**
     * Executes all events in the current event list.
     * Events which are not successfully executed remain in the list.
     */
    public void checkEvents() {
        for(int i=0; i<events.size(); ++i) {
            //TODO: possible read/write conflict
            if(events.get(i).execute()) {
                events.remove(i);
            }
        }
    }

    /**
     * Removes all NPC with their destroyed flag set to true.
     */
    public void removeDestroyedNPCs() {
        for(int i=0; i<npcs.size(); ++i) {
            //TODO: use list (first in, first out!)
            if(npcs.get(i).destroyed()) {
                score += npcs.get(i).getScore();
                npcs.remove(i);
            }
        }
    }

    /**
     * Checks if the conditions for clearing the level are met.
     * @return true, if all Enemies are destroyed.
     */
    public boolean levelClear(){
        for(Entity n: npcs)
            if(!n.destroyed())
                return false;

        return true;
    }

    /**
     * Check if an Entity has left the game area.
     * If so, fire an event to deal with this.
     */
    public void checkBorders(){
        for(Entity n: npcs) {
            if(n.x<0 || n.x>fieldWidth || n.y<0 || n.y>fieldHeight) {
                addEvent(new EventWrapEntity(n));
            }
        }

        if(player.x<0 || player.x>fieldWidth || player.y<0 || player.y>fieldHeight) {
            addEvent(new EventWrapEntity(player));
        }
    }
}
