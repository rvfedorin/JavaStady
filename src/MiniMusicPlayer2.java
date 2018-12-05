import javax.sound.midi.*;

public class MiniMusicPlayer2  implements ControllerEventListener{

    public static void main(String[] args){
        MiniMusicPlayer2 mini = new MiniMusicPlayer2();
        mini.go();
    }

    private void go(){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this, eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 60; i+=4){
                track.add(MakeEvent(144, 1, i, 100, i));
                track.add(MakeEvent(176, 1, 127 , 0, i));  // 176 = ControllerEvent
                track.add(MakeEvent(128, 1, i, 100, i));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();

        } catch (Exception ex) {}
    }  // close  void go()

    private static MidiEvent MakeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage message = new ShortMessage();
            message.setMessage(comd, chan, one, two);
            event = new MidiEvent(message, tick);
        } catch (Exception ex ) {}

        return event;
    } // close MidiEvent MakeEvent()

    @Override
    public void controlChange(ShortMessage event) {
        System.out.println("Ля");
    }
}
