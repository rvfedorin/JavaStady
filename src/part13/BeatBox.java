package part13;

import javax.swing.*;
import javax.sound.midi.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BeatBox {
//    private JPanel mainPanel;
    private ArrayList<JCheckBox> checkBoxesList;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
//    private JFrame theFrame;

    private String[] instrumentNames = {"Buss Drum", "Closed Hi-Hat", "Open Hi-Hat",
        "Acoustic Share", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
        "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom",
        "High Agogo", "Open High Conga"};

    private int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new BeatBox().buildGui();
    }

    private void buildGui() {
        JFrame theFrame = new JFrame();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        checkBoxesList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Up Temp");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Down Temp");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i=0; i < 16; i++) {
            nameBox.add(new JLabel(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        JPanel mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i=0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxesList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);

    } // private void buildGui()

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }  // private void setUpMidi()

    private void buildTrackAndStart(){
        int[] trackList;
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i=0; i < 16; i++) {
            trackList = new int[16];

            int key = instruments[i];

            for (int j=0; j < 16; j++) {
                JCheckBox jc = checkBoxesList.get(j + 16*i);
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            } // for (int j=0; j < 16; j++)

            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        } // for (int i=0; i < 16; i++)
        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } // private void buildTrackAndStart()

    class MyStartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            buildTrackAndStart();
        }
    } // class MyStartListener implements ActionListener

    class MyStopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            sequencer.stop();
        }
    } // class MyStopListener implements ActionListener

    class MyUpTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            float tempFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempFactor * 1.03));
        }
    } // class MyUpTempoListener implements ActionListener

    class MyDownTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            float tempFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempFactor * .97));
        }
    } // class MyDownTempoListener implements ActionListener

    private void makeTracks(int[] list) {
        for (int i=0; i < 16; i++) {
            int key = list[i];

            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i+1));
            }
        } // for (int i=0; i < 16; i++)
    } // private void makeTracks(int[] list)

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage message = new ShortMessage();
            message.setMessage(comd, chan, one, two);
            event = new MidiEvent(message, tick);
        } catch (Exception ex ) {
            ex.printStackTrace();
        }

        return event;
    } // close MidiEvent makeEvent()

} // public class BeatBox
