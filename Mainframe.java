import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by m4dguy on 24.02.2015.
 */

public class Mainframe extends JFrame implements MouseMotionListener, MouseListener{

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
                if(frames != NO_FPS_LIMIT){
                    try{
                        this.sleep(time);
                    }catch(InterruptedException e){
                        System.out.println(e);
                        System.exit(1);
                    }

                }
            }
        }
    }

    public static final int NO_FPS_LIMIT = -1;
    protected static final Color colorFg = new Color(1f, 1f, 1f);
    protected static final Color colorBg = new Color(0f, 0f, 0f);
    protected static int frames;

    protected static Mesh playerIcon;
    protected static final int ICONSIZE = 12;

    protected DbgWindow dbgWin;

    protected Controller controller;
    protected Engine engine;
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

        playerIcon = new Mesh("gfx/player.vo");
    }

    public void paint(Graphics g){
        super.paint(g);

        BufferedImage engineGfx = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        g = buffer.getGraphics();

        //draw background
        g.setColor(colorBg);
        g.fillRect(0, 0, view.getWidth(), view.getHeight());

        drawInterface(buffer);
        engine.renderGame(engineGfx);
        g.drawImage(engineGfx, 0, 0, null);
        g = view.getGraphics();

        g.drawImage(buffer, 0, 0, null);
    }

    public void drawInterface(Image buffer) {
        Graphics g = buffer.getGraphics();
        g.setColor(colorFg);
        g.drawString(String.valueOf(engine.score), 20, 30);

        for(int i=0; i<engine.lives; ++i) {
            playerIcon.transformationReset();
            playerIcon.scale(ICONSIZE);
            playerIcon.displace(getWidth() - (i*ICONSIZE*2) - 30, getHeight() - 45);
            playerIcon.render(buffer);
        }
    }

    public void gameStart() {
        controller.start();
        engine.startGame();
    }

    public void attachDebugWindow(){
        dbgWin = new DbgWindow(engine);
        dbgWin.setLocation(getX()+getWidth()+10, getY());
    }


    public void mouseDragged(MouseEvent e) {
        float ax = e.getX() - engine.player.x;
        float ay = e.getY() - engine.player.y;
        float norm = ax * ax + ay * ay;
        double angle;

        angle = Math.atan(ax / ay);

        engine.player.setDirection(ax, ay);
        engine.player.accelerate(ax, ay);
        engine.player.rotate((float)angle);
    }

    public void mouseMoved(MouseEvent e) {
        float ax = e.getX() - engine.player.x;
        float ay = e.getY() - engine.player.y;
        double angle;

        angle = Math.atan(ax / ay);
        engine.player.rotate((float)angle);
    }

    public void mouseClicked(MouseEvent e) {

        switch(e.getButton()) {
            case MouseEvent.BUTTON1:
                float ax = e.getX() - engine.player.x;
                float ay = e.getY() - engine.player.y;
                float angle = (float)Math.atan(ax / ay);

                engine.player.accelerate(ax, ay);
                engine.player.rotate(angle);
                break;

            case MouseEvent.BUTTON3:
                Dummy dummy = new Dummy(engine);
                dummy.x = e.getX();
                dummy.y = e.getY();
                engine.addEvent(new EventPlayerShoot(dummy));
                break;

            default:
                return;
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

