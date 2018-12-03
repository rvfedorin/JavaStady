package beat_box;

import javax.sound.midi.*;

public class MusicTest2 {
    public static void main(String[] args){
        MusicTest2 app = new MusicTest2();
        app.play();
    }

    public void play(){
        try{
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, 44, 100);
            MidiEvent noteOn = new MidiEvent(a, 4);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 44, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            player.setSequence(seq);

            player.start();

        } catch (Exception ex) {
            System.out.println("Ошибка получения синтезатора.");
            ex.printStackTrace();
        }

        System.out.println("Exit");
    } // play()

} // class MusicTest2
