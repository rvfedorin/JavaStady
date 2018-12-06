import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MiniMusicPlayer2{

    static JFrame frame = new JFrame();
    static MyDrawPanel1 panel1;


    public static void main(String[] args){
        MiniMusicPlayer2 mini = new MiniMusicPlayer2();
        mini.go();
    }

    private void setupGui(){
        panel1 = new MyDrawPanel1();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
    }  // close setupGui()

    private void go(){
        setupGui();

        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(panel1, eventsIWant);
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 60; i+=4){

                int r = (int) ((Math.random() * 50) + 1);
                track.add(MakeEvent(144, 1, r, 100, i));
                track.add(MakeEvent(176, 1, 127 , 0, i));  // 176 = ControllerEvent
                track.add(MakeEvent(128, 1, r, 100, i));
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


}

class MyDrawPanel1 extends JPanel implements ControllerEventListener{
    boolean msg = false;

    @Override
    public void controlChange(ShortMessage event) {
        msg = true;
        repaint();
    }

    public void paintComponent(Graphics g){
        if (msg) {
            Graphics2D g2 = (Graphics2D) g;

            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);

            Color c = new Color(red, green, blue);
            g2.setColor(c);

            int h = (int) ((Math.random() * 120) + 10);
            int w = (int) ((Math.random() * 120) + 10);

            int x  = (int) ((Math.random() * 40) + 10);
            int y  = (int) ((Math.random() * 40) + 10);

            g2.fillRect(x, y, h, w);
            msg = false;
        }
    }
}
