import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class testGui {

  JFrame frame;
  JLabel label;

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {}
    testGui tester = new testGui();
    tester.go();
  }

  public void go() {
    frame = new JFrame();
    JButton button = new JButton("Change Colour");
    JButton lbutton = new JButton("Change Label");
    button.addActionListener(new colorAction());
    label = new JLabel("Hello");
    lbutton.addActionListener(new labelAction());



    MyDrawPanel panel = new MyDrawPanel();
    panel.setSize(250,250);

    frame.getContentPane().add(BorderLayout.EAST, label);
    frame.getContentPane().add(BorderLayout.WEST, lbutton);
    frame.getContentPane().add(BorderLayout.SOUTH, button);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setVisible(true);
  }

  class MyDrawPanel extends JPanel {
    public void paintComponent(Graphics g) {
      Graphics2D g2D = (Graphics2D)g;
      int red = (int)(Math.random() * 256);
      int green = (int)(Math.random() * 256);
      int blue = (int)(Math.random() * 256);
      Color startRandColor = new Color(red, green, blue);

      red = (int)(Math.random() * 256);
      green = (int)(Math.random() * 256);
      blue = (int)(Math.random() * 256);
      Color endRandColor = new Color(red, green, blue);

      g2D.setPaint(new GradientPaint(120, 120, startRandColor, 250, 250, endRandColor));
      g2D.fillOval(70, 70, 150, 250);
    }
  }

  class colorAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      frame.repaint();

      label.setText("000!");
    }
  }

  class labelAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      label.setText("See Ya!");
    }
  }
}
