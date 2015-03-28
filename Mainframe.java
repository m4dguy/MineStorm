import java.awt.*;
import java.awt.event.*;

/**
 * Mainframe class for displaying the game.
 * Also handles key and mouse input and rendering.
 *
 * Created by m4dguy on 24.02.2015.
 */

public class Mainframe extends Frame implements MouseMotionListener, MouseListener{


    /**
     * Controller contains a thread for handling rendering.
     * Rendering is done by calling repaint of the Mainframe after sleep time.
     */
    public class Controller extends Thread {
        boolean terminate = false;
        Mainframe owner;

        public Controller(Mainframe o){
            super();
            owner = o;
        }

        public void run(){
            long startTime, difference, sleepTime;
            final long maxTime = (long)(1000f/(float)frames);
            while(!terminate){
                startTime = System.currentTimeMillis();
                if(dbgWin != null)
                    dbgWin.update();

                owner.repaint();

                if(frames != NO_FPS_LIMIT) {
                    difference = System.currentTimeMillis() - startTime;
                    sleepTime = Math.max(0, maxTime - difference);

                    try {
                        sleep(sleepTime);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                        System.exit(1);
                    }
                }
            }
        }
    }

    public static final int NO_FPS_LIMIT = -1;      //render without sleep time
    public static final int CINEMATIC = 24;         //cinematic frame rate. yes, it sucks. now go home and play a modern console game.
    protected static int frames;                    //locked to 60fps by default


    protected DbgWindow dbgWin;

    protected Controller controller;
    protected Engine engine;

    protected Renderer renderer;
    protected Panel view;

    public Mainframe(Engine e){
        super("MineStorm");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt) {
                controller.terminate = true;
                System.exit(0);}
        });

        int frameWidth = 480;
        int frameHeight = 620;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        Panel cp = new Panel(new BorderLayout());
        add(cp);
        setResizable(false);
        view = new Panel(null);
        view.addMouseMotionListener(this);
        view.addMouseListener(this);
        cp.add(view, "Center");

        setResizable(false);
        setVisible(true);

        engine = e;
        //frames = NO_FPS_LIMIT;
        //frames = CINEMATIC;
        frames = 60;
        controller = new Controller(this);
        renderer = new Renderer(engine);
    }

    /**
     * Calls the renderer for updating graphics.
     * TODO: it appears that paint is sometimes called before the Renderer is ready. We then get a Nullpointer exception.
     * @see Mainframe.Controller
     */
    public void paint(Graphics g){
        super.paint(g);

        g = view.getGraphics();
        g.drawImage(renderer.render(), 0, 0, null);
    }

    /**
     * Starts all update threads.
     * Tells the engine to start the game.
     */
    public void gameStart() {
        controller.start();
        engine.startGame();
    }

    /**
     * Call to create a debug window displaying game variables.
     */
    public void attachDebugWindow(){
        dbgWin = new DbgWindow(engine);
        dbgWin.setLocation(getX()+getWidth()+10, getY());
    }

    public void handleMouseEvent(MouseEvent e){
        switch(e.getButton()) {
            case MouseEvent.BUTTON1:
                float dx = e.getX() - engine.player.x;
                float dy = e.getY() - engine.player.y;
                engine.addEvent(new EventMoveEntity(engine.player, dx, dy, Engine.FASTSPEED));
                break;

            case MouseEvent.BUTTON2:
                engine.addEvent(new EventPlayerEscape(engine.player));
                break;

            case MouseEvent.BUTTON3:
                engine.addEvent(new EventEntityShoot(engine.player, e.getX(), e.getY()));
                break;

            default:
        }
    }

    public void mouseDragged(MouseEvent e) {
        //handleMouseEvent(e);
        float dx = e.getX() - engine.player.x;
        float dy = e.getY() - engine.player.y;
        engine.player.setAngle(dx, dy);
        engine.addEvent(new EventMoveEntity(engine.player, dx, dy, Engine.MAXSPEED));
    }

    public void mouseMoved(MouseEvent e) {
        float dx = e.getX() - engine.player.x;
        float dy = e.getY() - engine.player.y;
        engine.player.setAngle(dx, dy);
    }

    public void mouseClicked(MouseEvent e) {
        handleMouseEvent(e);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

