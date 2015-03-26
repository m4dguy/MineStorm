import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A dedicated Renderer for the Engine.
 * Does all graphical operations and returns a rendered image upon calling render().
 *
 */
public class Renderer {

    protected static Mesh playerIcon;
    protected static final int ICONSIZE = 12;

    protected Engine engine;
    protected BufferedImage bufferBackground;
    //protected BufferedImage bufferObjects;
    //protected BufferedImage bufferInterface;
    protected BufferedImage graphics;

    protected final Color colorBg = new Color(0f, 0f, 0f);
    protected final Color colorFg = new Color(1f, 1f, 1f);


    /**
     * Create a Renderer which renders all objects contained in the submitted Engine.
     * @param e the engine object to which this Renderer is being attached.
     */
    public Renderer(Engine e){
        engine = e;
        playerIcon = MeshLoader.loadVectorObject("gfx/player.vo");

        bufferBackground = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_RGB);
        //bufferObjects = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_ARGB);
        //bufferInterface = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_ARGB);
        graphics = new BufferedImage(engine.fieldWidth, engine.fieldHeight, BufferedImage.TYPE_INT_RGB);

        initializeBackground();
    }

    /**
     * Initializes the background as a black area.
     */
    public void initializeBackground(){
        Graphics g = bufferBackground.getGraphics();
        g.setColor(colorBg);
        g.fillRect(0, 0, engine.fieldWidth, engine.fieldHeight);
    }

    /**
     * Paints the initialized background in the first layer.
     */
    public void renderBackground() {
        Graphics g = graphics.getGraphics();
        g.drawImage(bufferBackground, 0, 0, null);
    }

    /**
     * Renders all objects contained in the engine.
     * Such include world geometry, the Player and NPCs.
     * @see renderEnvironment()
     * @see renderPlayer()
     * @see renderNPCs()
     */
    public void renderObjects() {
        renderEnvironment();
        renderPlayer();
        renderNPCs();
    }

    /**
     * Empty. MineStorm does not have world geometry.
     */
    public void renderEnvironment() {
        //nothing to do for MineStorm
    }

    /**
     * Render the Player.
     */
    public void renderPlayer() {
        Player player = engine.player;
        Mesh model = player.model;
        model.transformationReset();
        model.scale(player.size);
        model.rotate(player.angle);
        model.displace(player.x, player.y);
        renderMesh(model);
    }

    /**
     * Render each single NPC.
     */
    public void renderNPCs() {
        NPC n;
        Mesh m;

        for(int a=0; a<engine.npcs.size(); ++a){
            n = engine.npcs.get(a);

            if(!n.active()){
                continue;
            }

            m = n.model;

            m.transformationReset();
            m.scale(n.size);

            if(n.angle != 0)
                m.rotate(n.angle);

            m.displace(n.x, n.y);
            renderMesh(m);
        }
    }

    /**
     * Renders a single Mesh object.
     * @param m the Mesh which is supposed to be rendered.
     */
    public void renderMesh(Mesh m) {
        Graphics g = graphics.getGraphics();

        int i, j;
        for(int e=0; e<m.edgeCount; ++e) {
            i = m.edges[e][0];
            j = m.edges[e][1];
            g.drawLine((int) m.nodesDisplaced[i][0], (int) m.nodesDisplaced[i][1], (int) m.nodesDisplaced[j][0], (int) m.nodesDisplaced[j][1]);
        }
    }

    /**
     * Renders the game interface in the topmost image layer.
     */
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
            renderMesh(playerIcon);
        }
    }

    /**
     * Renders the background, active objects in the game and the interface.
     * @return a reference to the rendered image.
     * @see renderBackground()
     * @see renderObjects()
     * @see renderInterface()
     */
    public BufferedImage render() {
        renderBackground();
        renderObjects();
        renderInterface();
        return graphics;
    }

}
