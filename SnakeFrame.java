package Projects.Snake;

import javax.swing.JFrame;

public class SnakeFrame extends JFrame {

    SnakeFrame() {

// - create a panel to work on
//    -> .add(new SnakePanel())
        //SnakePanel panel = new SnakePanel();
        //this.add(panel);
    this.add(new SnakePanel());
//- add a title to the window frame
//    -> .setTitle(" ")
    this.setTitle("Snake test");
//- close the window on exit
//    -> .setDefaultCloseOperation(JFrame.Exit_On_Close)
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//- resize the window
//    -> .setResizable(boolean)
    this.setResizable(true);
// make the frame appear on the screen
    this.setVisible(true);
    this.pack();
// position the frame on the center of the screen.. null: center the frame
    this.setLocationRelativeTo(null);

    }
    
}
