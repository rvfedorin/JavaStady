package part16;

import java.io.*;
import java.util.*;

public class Jukebkox1 {
    private ArrayList<Song> songList = new ArrayList<>();

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
    }  // close getSongs()

    private void addSong(String song) {
        String[] songInformation = song.split("/");
        songList.add(new Song(songInformation[0], songInformation[1]));
    }
}

class Song implements Comparable<Song> {
    private String name;
    private String author;

    public Song(String name_s, String author_s) {
        name = name_s;
        author = author_s;
    }

    private String getAuthor() {
        return author;
    }

    private String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Song o) {
        return name.compareTo(o.getName());
    }
}
