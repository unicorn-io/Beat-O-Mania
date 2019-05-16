import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class simpleAnimation {

  JFrame frame;

  int x = 70;
  int y = 70;

  public static void main(String [] args) {
    simpleAnimation testObj = new simpleAnimation();
    testObj.go();
  }

  public void go() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    TheDrawPanel drawpanel = new TheDrawPanel();

    frame.getContentPane().add(drawpanel);
    frame.setSize(300, 300);
    frame.setVisible(true);

    for (int i = 0; i < 130; i++) {
      x++;
      y++;

      drawpanel.repaint();

      try {
        Thread.sleep(20);
      } catch(Exception ex) {}
    }
    for (int i = 0; i < 130; i++) {
      x--;
      y--;

      drawpanel.repaint();

      try {
        Thread.sleep(20);
      } catch(Exception ex) {}
    }
  }

  public class TheDrawPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.setColor(Color.white);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      g.setColor(Color.GREEN);
      g.fillOval(x, y, 40, 40);

    }
  }

}
