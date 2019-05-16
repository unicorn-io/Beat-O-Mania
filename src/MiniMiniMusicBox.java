import javax.sound.midi.*;

public class MiniMiniMusicBox {

  public void play() {

    try {

      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();

      Sequence seq = new Sequence(Sequence.PPQ, 4);

      Track tk = seq.createTrack();

      ShortMessage first = new ShortMessage();
      first.setMessage(192, 1, 80, 0);
      MidiEvent changeInstrument = new MidiEvent(first, 1);
      tk.add(changeInstrument);

      ShortMessage a = new ShortMessage();
      a.setMessage(144, 1, 30, 100);
      MidiEvent noteOn = new MidiEvent(a, 1);
      tk.add(noteOn);

      ShortMessage b = new ShortMessage();
      b.setMessage(128, 1, 30, 127);
      MidiEvent noteOff = new MidiEvent(b, 12);
      tk.add(noteOff);

      ShortMessage c = new ShortMessage();
      c.setMessage(144, 1, 50, 100);
      MidiEvent noteOn1 = new MidiEvent(c, 10);
      tk.add(noteOn1);

      ShortMessage d = new ShortMessage();
      d.setMessage(128, 1, 50, 127);
      MidiEvent noteOff2 = new MidiEvent(d, 16);
      tk.add(noteOff2);



      sequencer.setSequence(seq);

      sequencer.start();

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public static void main(String[] args) {
    MiniMiniMusicBox test = new MiniMiniMusicBox();

    test.play();
  }


}
