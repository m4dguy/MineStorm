/**
 * Created by m4dguy on 23.02.2015.
 */

public class main {
    public static void main(String[] args) {
        Mainframe frame = new Mainframe(new Engine());
        //frame.attachDebugWindow();
        frame.setVisible(true);
        frame.gameStart();
    }
}
