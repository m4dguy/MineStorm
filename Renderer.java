import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by m4dguy on 24.03.2015.
 */
public class Renderer {

    protected static Mesh playerIcon;
    protected static final int ICONSIZE = 12;

    protected Engine engine;
    protected BufferedImage bufferBackground;
    protected BufferedImage bufferObjects;
    protected BufferedImage bufferInterface;
    protected BufferedImage graphics;

    protected final Color colorBg = new Color(0f, 0f, 0f);
    protected final Color colorFg = new Color(1f, 1f, 1f);

    public Renderer(Engine e){
        engine = e;
        playerIcon = new Mesh("gfx/player.vo");

        bufferBackground = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_RGB);
        bufferObjects = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_ARGB);
        bufferInterface = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_ARGB);
        graphics = new BufferedImage(engine.fieldWidth, engine.fieldHeight, BufferedImage.TYPE_INT_RGB);

        initializeBackground();
    }

    public void initializeBackground(){
        Graphics g = bufferBackground.getGraphics();
        g.setColor(colorBg);
        g.fillRect(0, 0, engine.fieldWidth, engine.fieldHeight);
    }

    public void renderBackground() {
        Graphics g = graphics.getGraphics();
        g.drawImage(bufferBackground, 0, 0, null);
    }

    public void renderObjects() {
        //Graphics g = bufferObjects.getGraphics();
        //g.setColor(colorFg);

        engine.renderGame(graphics);

    }

    /*public void renderNPC(NPC n) {
        Mesh m = n.model;
        Graphics g = graphics.getGraphics();
        g.setColor(colorFg);

        m.transformationReset();
        m.scale(n.size);

        if(n.angle != 0)
            m.rotate(n.angle);

        m.displace(n.x, n.y);


        int i, j;
        for(int e=0; e<m.edgeCount; ++e) {
            i = m.edges[e][0];
            j = m.edges[e][1];
            g.drawLine((int) m.nodesDisplaced[i][0], (int) m.nodesDisplaced[i][1], (int) m.nodesDisplaced[j][0], (int) m.nodesDisplaced[j][1]);
        }
    }*/

    public void renderInterface() {
        Graphics g = graphics.getGraphics();
        g.setColor(colorFg);

        //draw score
        g.drawString(String.valueOf(engine.score), 20, 30);

        //draw lives
        for(int i=0; i<engine.lives; ++i) {
            playerIcon.transformationReset();
            playerIcon.scale(ICONSIZE);
            playerIcon.displace(engine.fieldWidth - (i*ICONSIZE*2) - 30, engine.fieldHeight - 45);
            playerIcon.render(graphics);
        }
    }

    public BufferedImage render() {
        renderBackground();
        renderObjects();
        renderInterface();
        return graphics;
    }

}
