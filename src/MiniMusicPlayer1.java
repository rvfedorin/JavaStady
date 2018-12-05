import javax.swing.*;
import javax.sound.midi.*;

public class MiniMusicPlayer1 {

    public static void main(String[] args){
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 100; i+=4 ) {
                track.add(MakeEvent(144, 1, i, 100, i));
                track.add(MakeEvent(128, 1, i, 100, i+2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (Exception ex) {}

    } // close main()

    public static MidiEvent MakeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage message = new ShortMessage();
            message.setMessage(comd, chan, one, two);
            event = new MidiEvent(message, tick);
        } catch (Exception ex ) {}

        return event;
    } // close MidiEvent MakeEvent()
}
