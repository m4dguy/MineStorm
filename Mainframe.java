import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Mainframe class for displaying the game.
 * Also handles key and mouse input and rendering.
 *
 * Created by m4dguy on 24.02.2015.
 */

public class Mainframe extends JFrame implements MouseMotionListener, MouseListener{


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
            long time = (long)((1f/frames) * 1000f);
            while(!terminate){
                if(dbgWin != null)
                    dbgWin.update();

                owner.repaint();

                //TODO: adapt timing
                if(frames != NO_FPS_LIMIT) {
                    try {
                        sleep(time);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                        System.exit(1);
                    }
                }
            }
        }
    }

    public static final int NO_FPS_LIMIT = -1;
    protected static int frames;


    protected DbgWindow dbgWin;

    protected Controller controller;
    protected Engine engine;

    protected Renderer renderer;
    protected BufferedImage buffer;
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
        JPanel cp = new JPanel(new BorderLayout());
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
        frames = 120;
        controller = new Controller(this);
        renderer = new Renderer(engine);
    }

    /**
     * Calls the renderer for updating graphics.
     * @see Mainframe.Controller
     */
    public void paint(Graphics g){
        super.paint(g);

        g = view.getGraphics();
        buffer = renderer.render();
        g.drawImage(buffer, 0, 0, null);
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


    public void mouseDragged(MouseEvent e) {
        float dx = e.getX() - engine.player.x;
        float dy = e.getY() - engine.player.y;
        engine.addEvent(new EventMovePlayer(new Dummy(engine), dx, dy));
    }

    public void mouseMoved(MouseEvent e) {
        float dx = e.getX() - engine.player.x;
        float dy = e.getY() - engine.player.y;
        engine.player.setAngle(dx, dy);
    }

    public void mouseClicked(MouseEvent e) {

        switch(e.getButton()) {
            case MouseEvent.BUTTON1:
                float dx = e.getX() - engine.player.x;
                float dy = e.getY() - engine.player.y;
                engine.addEvent(new EventMovePlayer(new Dummy(engine), dx, dy));
                break;

            case MouseEvent.BUTTON2:
                engine.player.escape();
                break;

            case MouseEvent.BUTTON3:
                Dummy dummy = new Dummy(engine);
                dummy.x = e.getX();
                dummy.y = e.getY();
                engine.addEvent(new EventPlayerShoot(dummy));
                break;

            default:
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

