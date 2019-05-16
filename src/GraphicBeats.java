import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphicBeats {



  static JFrame frame = new JFrame("My First Grpahics Beat Maker");
  static drawingPanel m1;

  public static void main(String [] args) {
    GraphicBeats testObj = new GraphicBeats();
    testObj.go();
  }

  public void setupGUI() {
    m1 = new drawingPanel();
    frame.setSize(500, 500);
    frame.getContentPane().add(m1);
    frame.setVisible(true);
  }

  public void go() {
    setupGUI();

    try {
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();
      int[] eventsIWant = {127};
      Sequence seq = new Sequence(Sequence.PPQ, 4);
      Track trck = seq.createTrack();
      sequencer.addControllerEventListener(m1, eventsIWant);


     for (int i = 5; i < 60; i += 4) {
        trck.add(makeEvent(144, 1, i, 100, i));
        trck.add(makeEvent(176, 1, 127, 0,i));
        trck.add(makeEvent(128, 1, i, 100, i + 2));
      }

      sequencer.setSequence(seq);
      sequencer.setTempoInBPM(220);
      sequencer.start();



    } catch(Exception ex) {}
  }


  public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
    MidiEvent event = null;
    try {
      ShortMessage a = new ShortMessage();
      a.setMessage(comd, chan, one, two);
      event = new MidiEvent(a, tick);
    } catch (Exception ex) {}
    return event;
  }

  public class drawingPanel extends JPanel implements ControllerEventListener {
    boolean msg = false;

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int red = (int)(Math.random()*250);
        int green = (int)(Math.random()*250);
        int blue = (int)(Math.random()*250);

        g.setColor(new Color(red, green, blue));

        int ht = (int)((Math.random()*120) + 10);
        int width = (int)((Math.random()*120) + 10);
        int x = (int)((Math.random()*300) + 10);
        int y = (int)((Math.random()*300) + 10);
        g.fillRect(x, y, width, ht);
        msg = false;


    }

    public void controlChange(ShortMessage event) {
      msg= true;
      repaint();
    }
  }
}
