import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.sound.midi.*;

/**
 * The type Beat o mania.
 */
public class Beat_O_Mania {

  private JPanel mainPanel;
  private ArrayList<JCheckBox> checkBoxList;
  private Sequencer sequencer;
  private Sequence seq;
  private Track trck;
  private JLabel showTempo = new JLabel("120");

  private String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas"
  , "Whistle", "Low Congo", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Congo"};
  private int[] instruments = {36,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String [] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) { ex.printStackTrace(); }
    new Beat_O_Mania().setUpGUI();
  }

  private void setUpGUI() {
    JFrame frame = new JFrame("Music Box --> Beat Maker");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BorderLayout layout = new BorderLayout();
    JPanel background = new JPanel(layout);
    background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    checkBoxList = new ArrayList<JCheckBox>();
    Box buttonBox= new Box(BoxLayout.Y_AXIS);

    JButton start = new JButton("Start");
    start.addActionListener(new startActionListener());
    buttonBox.add(start);

    JButton stop = new JButton("Stop");
    stop.addActionListener(new stopActionListener());
    buttonBox.add(stop);

    JButton upTempo = new JButton("Tempo Up");
    upTempo.addActionListener(new upTempoActionListener());
    buttonBox.add(upTempo);

    JButton downTempo = new JButton("Tempo Down");
    downTempo.addActionListener(new downTempoActionListener());
    buttonBox.add(downTempo);

    JButton beatDrop = new JButton("Drop");
    beatDrop.addActionListener(new beatDropActionListener());
    buttonBox.add(beatDrop);

    JButton clearAll = new JButton("Clear All");
    clearAll.addActionListener(new clearAllActionListener());
    buttonBox.add(clearAll);

    buttonBox.add(showTempo);
    Font font = new Font("Courier", Font.BOLD, 18);
    showTempo.setFont(font);

    Box nameBox = new Box(BoxLayout.Y_AXIS);
    for (int i = 0; i < 16; i++) {
      nameBox.add(new Label(instrumentNames[i]));
    }

    background.add(BorderLayout.EAST, buttonBox);
    background.add(BorderLayout.WEST, nameBox);


    frame.getContentPane().add(background);

    GridLayout grid = new GridLayout(16, 16);
    grid.setVgap(0);
    grid.setHgap(2);

    mainPanel = new JPanel();
    mainPanel.setLayout(grid);
    background.add(BorderLayout.CENTER, mainPanel);

    for (int i = 0; i < 256; i++) {
      JCheckBox c = new JCheckBox();
      c.setSelected(false);
      checkBoxList.add(c);
      mainPanel.add(c);
    }
    setUpMidi();
    background.setBackground(Color.ORANGE);
    frame.setBounds(50, 50, 600, 600);
    frame.pack();
    frame.setVisible(true);
  }

  private void setUpMidi() {
    try {
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      seq = new Sequence(Sequence.PPQ, 4);
      trck = seq.createTrack();
      sequencer.setTempoInBPM(60);
    } catch (Exception ex) {/*hello*/}
  }

  private void buildTrackAndStart() {
    int[] trackList = null;
    seq.deleteTrack(trck);
    trck = seq.createTrack();
    for (int i = 0; i < 16; i++) {
      trackList = new int[16];
      int key = instruments[i];
      for (int j = 0; j < 16; j++) {
        JCheckBox c  = checkBoxList.get(j + 16 * i);
        if (c.isSelected()) {
          trackList[j] = key;
        } else {
          trackList[j] = 0;
        }
      }
      makeTracks(trackList);
      trck.add(makeEvent(176, 1, 127, 0, 16));
    }

    //trck.add(makeEvent(192, 9, 1, 0, 15));
    try {
      sequencer.setSequence(seq);
      sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
      sequencer.start();
      sequencer.setTempoInBPM(120);
    } catch(Exception e) {e.printStackTrace();}
  }

  private void makeTracks(int[] list) {
    for (int i = 0; i < 16; i++) {
      int key = list[i];
      if (key != 0) {
        trck.add(makeEvent(144, 9, key, 50, i));
        trck.add(makeEvent(144, 9, key, 116, i));
      }
    }
  }

  private void clearList() {
    for (JCheckBox c : checkBoxList) {
      c.setSelected(false);
    }
  }

  private void overFlowList() {
    for (JCheckBox c : checkBoxList) {
      c.setSelected(true);
    }
  }

  /**
   * The type Start action listener.
   */
  public class startActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      buildTrackAndStart();
    }
  }

  /**
   * The type Stop action listener.
   */
  public class stopActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      sequencer.stop();
    }
  }

  /**
   * The type Up tempo action listener.
   */
  public class upTempoActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      float tempoFactor = sequencer.getTempoFactor();
      sequencer.setTempoFactor((float)(tempoFactor * 1.03));
      showTempo.setText(sequencer.getTempoFactor() + "");
    }
  }

  /**
   * The type Down tempo action listener.
   */
  public class downTempoActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        showTempo.setText(sequencer.getTempoFactor() + "");

    }
  }

  /**
   * The type Beat drop action listener.
   */
  public class beatDropActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      float tempoFactor = sequencer.getTempoFactor();
      if (tempoFactor > 0.09) {
        sequencer.setTempoFactor(1);
      }
      showTempo.setText(sequencer.getTempoFactor() + "");
    }
  }

  /**
   * The type Clear all action listener.
   */
  public class clearAllActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      clearList();
    }
  }


  private static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
    MidiEvent event = null;
    try {
      ShortMessage a = new ShortMessage();
      a.setMessage(comd, chan, one, two);
      event = new MidiEvent(a, tick);
    } catch (Exception ex) {}
    return event;
  }
}
