import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame
{
    Frame()
    {
        this.add(new panel());
        this.setTitle("Snake Game");
        this.setResizable(false);
        this.setVisible(true);

        //this packs the application according to
        //the OS so that, the experience for every user is same
        this.pack();
    }
}
