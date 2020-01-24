/*
 *  Created by Roman V. Fedorin
 */
package frv.org.patterns.MVC;

import java.util.ArrayList;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author wolf
 */
public class BeatModel implements BeatModelInterface, MetaEventListener {

    Sequencer sequencer;
    Sequence sequence;
    Track track;
    ArrayList<BeatObserver> beatObserver = new ArrayList();
    ArrayList<BPMObserver> bpmObserver = new ArrayList();
    int bpm = 90;

    @Override
    public void initialize() {
        setUpMidi();
        buildTrackAndStart();
    }

    @Override
    public void on() {
        sequencer.start();
        setBPM(90);
    }

    @Override
    public void off() {
        setBPM(0);
        sequencer.stop();
    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    void beatEvent() {
        notifyBeatObservers();
    }

    @Override
    public void registerObserver(BeatObserver o) {
        beatObserver.add(o);
    }

    @Override
    public void removeObserver(BeatObserver o) {
        int i = beatObserver.indexOf(o);
        if (i >= 0) {
            beatObserver.remove(o);
        }
    }

    @Override
    public void registerObserver(BPMObserver o) {
        int i = bpmObserver.indexOf(o);
        if (i >= 0) {
            bpmObserver.add(o);
        }
    }

    @Override
    public void removeObserver(BPMObserver o) {
        bpmObserver.remove(o);
    }

    private void notifyBPMObservers() {
        beatObserver.forEach((o) -> {
            o.updateBeat();
        });
    }

    private void notifyBeatObservers() {
        bpmObserver.forEach((o) -> {
            o.updateBPM();
        });
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }
    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildTrackAndStart() {
        int[] trackList = {35, 0, 46, 0};

        sequence.deleteTrack(null);
        track = sequence.createTrack();
        makeTracks(trackList);
        track.add(makeEvent(192, 9, 1, 0, 4));
        try {
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeTracks(int[] trackList) {
        for (int i = 0; i < trackList.length; i++) {
            int key = trackList[i];
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

}
