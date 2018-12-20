package part16;

import java.io.*;
import java.util.*;

public class Jukebkox1 {
    ArrayList<String> songList = new ArrayList<>();

    public static void main(String[] args) {
        new Jukebkox1().go();
    }

    private void go() {
        getSongs();
        System.out.println(songList);
        Collections.sort(songList);
        System.out.println(songList);
    }

    private void getSongs () {
        try {
            File file = new File("src/part16/SongList.txt");
            System.out.println(file.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;

            while ((str = reader.readLine()) != null) {
                addSong(str);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }  // close gerSont()

    private void addSong(String song) {
        songList.add(song.split("/")[0]);
    }
}
