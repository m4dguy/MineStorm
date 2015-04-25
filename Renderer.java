import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A dedicated Renderer for the Engine.
 * Does all graphical operations and returns a rendered image upon calling render().
 *
 */
public class Renderer {

    protected static final float AFTERGLOW = .2f;
    protected static MeshModifier playerIcon;
    protected static final int ICONSIZE = 12;

    protected Engine engine;
    protected BufferedImage bufferBackground;
    //protected BufferedImage bufferObjects;
    //protected BufferedImage bufferInterface;
    protected BufferedImage graphics;

    protected final Color colorBg = new Color(0f, 0f, 0f, AFTERGLOW);
    protected final Color colorFg = new Color(1f, 1f, 1f);


    /**
     * Create a Renderer which renders all objects contained in the submitted Engine.
     * @param e the engine object to which this Renderer is being attached.
     */
    public Renderer(Engine e){
        engine = e;
        Mesh mesh = MeshLoader.loadVectorObject("gfx/player.vo");
        playerIcon = new MeshModifier(mesh);
        playerIcon.setScaling(ICONSIZE);

        bufferBackground = new BufferedImage(engine.fieldWidth, engine.fieldHeight,  BufferedImage.TYPE_INT_ARGB);
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
        if(player.active())
            renderEntity(player);
    }

    /**
     * Render all NPCs.
     */
    public void renderNPCs() {
        Entity e;
        MeshModifier m;

        for(int a=0; a<engine.npcs.size(); ++a){
            e = engine.npcs.get(a);

            if(!e.active()){
                continue;
            }

            renderEntity(e);
        }
    }

    /**
     * Renders a single Entity.
     * @param e the Entity which is supposed to be rendered.
     */
    public void renderEntity(Entity e) {
        renderMesh(e.getMesh());
    }

    /**
     * Renders a single Entity.
     * @param e the Entity which is supposed to be rendered.
     */
    public void renderMesh(MeshModifier m) {
        Graphics g = graphics.getGraphics();
        m.applyTransformations();

        int i, j;
        for(int e=0; e<m.getEdgeCount(); ++e) {
            i = m.getEdges()[e][0];
            j = m.getEdges()[e][1];
            g.drawLine((int) m.modified[i][0], (int) m.modified[i][1], (int) m.modified[j][0], (int) m.modified[j][1]);
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

        float dx, dy;

        //draw lives
        for(int i=0; i<engine.lives; ++i) {
            dx = engine.fieldWidth - (i*ICONSIZE*2) - 30;
            dy = engine.fieldHeight - 45;

            playerIcon.setDisplacement(dx, dy);
            playerIcon.applyTransformations();
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
