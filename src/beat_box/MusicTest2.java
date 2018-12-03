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

            int tackt = 1;
            for(int i = 1; i < 120; i += 20) {

                ShortMessage a = new ShortMessage();
                a.setMessage(144, 1, i, 100);
                MidiEvent noteOn = new MidiEvent(a, tackt);
                track.add(noteOn);

                ShortMessage b = new ShortMessage();
                b.setMessage(128, 1, i, 100);
                MidiEvent noteOff = new MidiEvent(b, tackt+8);
                track.add(noteOff);

                player.setSequence(seq);
                tackt += 10;


            }
            player.start();

        } catch (Exception ex) {
            System.out.println("Ошибка получения синтезатора.");
            ex.printStackTrace();
        }

        System.out.println("Exit");
    } // play()

} // class MusicTest2
