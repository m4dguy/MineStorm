import java.awt.*;
import java.awt.event.*;

/**
 * A simplistic Debug window which can be used to display different game variables.
 * Attach an engine to it and (if necessary) add more code to display single variables.
 */
public class DbgWindow extends Frame {

    Engine engine;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private Label label3 = new Label();
    private Label label4 = new Label();
    private Label label5 = new Label();
    private TextField tf_npcs = new TextField();
    private TextField tf_events = new TextField();
    private TextField tf_player = new TextField();
    private TextField tf_lifes = new TextField();
    private TextField tf_score = new TextField();
    private Label label6 = new Label();
    private TextField tf_speed = new TextField();
    private Label label7 = new Label();
    private TextField tf_acceleration = new TextField();


    public DbgWindow(Engine e) {

        super("MineStorm Debug");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) { dispose(); }});
        int frameWidth = 284;
        int frameHeight = 243;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setResizable(false);
        Panel cp = new Panel(null);
        add(cp);


        engine = e;
        label1.setBounds(8, 8, 107, 17);
        label1.setText("NPCs");
        cp.add(label1);
        label2.setBounds(8, 32, 110, 20);
        label2.setText("Events");
        cp.add(label2);
        label3.setBounds(8, 80, 110, 20);
        label3.setText("Score");
        cp.add(label3);
        label4.setBounds(8, 56, 110, 20);
        label4.setText("Lifes");
        cp.add(label4);
        label5.setBounds(8, 128, 110, 20);
        label5.setText("Player");
        cp.add(label5);
        label6.setBounds(8, 152, 110, 20);
        label6.setText("Player speed");
        cp.add(label6);
        tf_speed.setBounds(120, 152, 150, 20);
        cp.add(tf_speed);
        label7.setBounds(8, 176, 110, 20);
        label7.setText("Player acceleration");
        cp.add(label7);

        tf_npcs.setBounds(120, 8, 150, 20);
        cp.add(tf_npcs);
        tf_events.setBounds(120, 32, 150, 20);
        cp.add(tf_events);
        tf_player.setBounds(120, 128, 150, 20);
        cp.add(tf_player);
        tf_lifes.setBounds(120, 56, 150, 20);
        cp.add(tf_lifes);
        tf_score.setBounds(120, 80, 150, 20);
        cp.add(tf_score);
        tf_acceleration.setBounds(120, 176, 150, 20);
        cp.add(tf_acceleration);

        setVisible(true);
    }

    /**
     * Updates all TextField contents.
     */
    public void update() {
        tf_npcs.setText("" + engine.npcs.size());
        tf_events.setText("" + engine.events.size());
        tf_player.setText("" + (int)engine.player.x + " / " + (int)engine.player.y);
        tf_lifes.setText("" + engine.lives);
        tf_score.setText("" + engine.score);
        tf_speed.setText("" + engine.player.dirX + " / " + engine.player.dirY);
        tf_acceleration.setText("" + engine.player.accelerationX + " / " + engine.player.accelerationY);
    }

}
