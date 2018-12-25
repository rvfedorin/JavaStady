package beat_box;

import javax.sound.midi.*;

public class MusicTest1 {

    public void play(){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            System.out.println("Мы получили синтезатор!!!");
        } catch (MidiUnavailableException ex) {
            System.out.println("Не удалось получить синтезатор. ");
            ex.printStackTrace();
        }

    }

    public static void main(String[] args){
        MusicTest1 mt = new MusicTest1();
        mt.play();

    }
}
