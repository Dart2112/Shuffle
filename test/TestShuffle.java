import net.lapismc.shuffle.Shuffle;
import net.lapismc.shuffle.Song;
import net.lapismc.shuffle.SongFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestShuffle {

    private int random, shuffle, trials = 0;
    private List<Song> songs = new ArrayList<>();

    @Test
    public void runTestShuffle() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("1", "A", ""));
        songs.add(new Song("2", "A", ""));
        songs.add(new Song("3", "B", ""));
        songs.add(new Song("4", "B", ""));
        songs.add(new Song("5", "C", ""));
        songs.add(new Song("6", "C", ""));
        songs.add(new Song("7", "D", ""));
        songs.add(new Song("8", "E", ""));
        songs.add(new Song("9", "F", ""));
        songs.add(new Song("10", "G", ""));
        songs.add(new Song("11", "G", ""));
        songs.add(new Song("12", "H", ""));
        songs.add(new Song("13", "I", ""));
        songs.add(new Song("14", "J", ""));
        songs.add(new Song("15", "K", ""));
        songs.add(new Song("16", "K", ""));
        songs.add(new Song("17", "L", ""));
        songs.add(new Song("18", "M", ""));
        songs.add(new Song("19", "N", ""));
        songs.add(new Song("20", "O", ""));
        Shuffle shuffle = new Shuffle(songs);
        Long timeBefore = System.currentTimeMillis();
        shuffle.shuffle();
        songs = shuffle.getSongs();
        Long timeAfter = System.currentTimeMillis();
        System.out.println(songs);
        long timeTaken = timeAfter - timeBefore;
        double timePerSong = timeTaken / songs.size();
        System.out.println("Shuffled in " + timeTaken + " Milliseconds at " + timePerSong + " Milliseconds per song");
    }

    @Test
    public void runFileShuffleTest() throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        runFileShuffle();
    }

    @Test
    public void runLongTermComparisonShuffle() throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        for (int i = 0; i < 100; i++) {
            runFileShuffle();
        }
        System.out.println("\n\n");
        System.out.println("For " + trials + " trials,");
        System.out.println("Random shuffle had a total of " + random + " repeats");
        System.out.println("DNAblue shuffle had a total of " + shuffle + " repeats");
    }

    private void runFileShuffle() throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        if (songs.isEmpty()) {
            File musicDir = new File(System.getProperty("user.home") + "/Music");
            for (File f : Objects.requireNonNull(musicDir.listFiles())) {
                if (!f.getName().endsWith(".mp3")) {
                    continue;
                }
                songs.add(new SongFile(f));
            }
        }
        Shuffle shuffle = new Shuffle(songs);
        Long timeBefore = System.nanoTime();
        shuffle.shuffle();
        songs = shuffle.getSongs();
        Long timeAfter = System.nanoTime();
        System.out.println("\n\n");
        long timeTaken = timeAfter - timeBefore;
        System.out.println("Shuffled in " + timeTaken + " Nanoseconds");
        int repeats = checkForRepeats(songs);
        this.shuffle += repeats;
        System.out.println(repeats + " songs were found with the same artist before, after shuffle");
        timeBefore = System.nanoTime();
        songs = shuffle.randomiseOrder(songs);
        timeAfter = System.nanoTime();
        long randomTimeTaken = timeAfter - timeBefore;
        System.out.println("Shuffled in " + randomTimeTaken + " Nanoseconds");
        System.out.println(timeTaken / randomTimeTaken + "x difference between shuffle and random");
        repeats = checkForRepeats(songs);
        random += repeats;
        System.out.println(repeats + " songs were found with the same artist before, after a random shuffle");
        trials++;
    }

    private int checkForRepeats(List<Song> songs) {
        String title = "";
        String artist = "";
        int repeats = 0;
        for (Song s : songs) {
            if (s.getArtist(false).equalsIgnoreCase(artist)) {
                repeats++;
                System.out.println(artist + ": " + title + " & " + s.getTitle());
            } else {
                title = s.getTitle();
                artist = s.getArtist(false);
            }
        }
        return repeats;
    }

}
