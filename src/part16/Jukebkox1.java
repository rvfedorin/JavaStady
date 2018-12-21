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

        AuthorCompare aC = new AuthorCompare();
        Collections.sort(songList, aC);

        System.out.println(songList);

        HashSet<Song> uniqSongs = new HashSet<Song>();
        uniqSongs.addAll(songList);

        System.out.println(uniqSongs);
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

    class AuthorCompare implements Comparator<Song> {
        @Override
        public int compare(Song o1, Song o2) {
            return o1.getAuthor().compareTo(o2.getAuthor());
        }
    }
}

class Song implements Comparable<Song> {
    private String name;
    private String author;

    public Song(String name_s, String author_s) {
        name = name_s;
        author = author_s;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ":" + author;
    }

    @Override
    public int compareTo(Song o) {
        return name.compareTo(o.getName());
    }

    @Override
    public int hashCode(){
        return (name + ":" + author).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        Song s = (Song) o;
        return (s.getName().equals(name) && s.getAuthor().equals(author));
    }
}
