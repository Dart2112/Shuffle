import net.lapismc.shuffle.Shuffle;
import net.lapismc.shuffle.Shuffleable;
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
    private List<Shuffleable> shuffleables = new ArrayList<>();

    @Test
    public void runTestShuffle() {
        List<Shuffleable> shuffleables = new ArrayList<>();
        shuffleables.add(new Shuffleable("1", "A", ""));
        shuffleables.add(new Shuffleable("2", "A", ""));
        shuffleables.add(new Shuffleable("3", "B", ""));
        shuffleables.add(new Shuffleable("4", "B", ""));
        shuffleables.add(new Shuffleable("5", "C", ""));
        shuffleables.add(new Shuffleable("6", "C", ""));
        shuffleables.add(new Shuffleable("7", "D", ""));
        shuffleables.add(new Shuffleable("8", "E", ""));
        shuffleables.add(new Shuffleable("9", "F", ""));
        shuffleables.add(new Shuffleable("10", "G", ""));
        shuffleables.add(new Shuffleable("11", "G", ""));
        shuffleables.add(new Shuffleable("12", "H", ""));
        shuffleables.add(new Shuffleable("13", "I", ""));
        shuffleables.add(new Shuffleable("14", "J", ""));
        shuffleables.add(new Shuffleable("15", "K", ""));
        shuffleables.add(new Shuffleable("16", "K", ""));
        shuffleables.add(new Shuffleable("17", "L", ""));
        shuffleables.add(new Shuffleable("18", "M", ""));
        shuffleables.add(new Shuffleable("19", "N", ""));
        shuffleables.add(new Shuffleable("20", "O", ""));
        Shuffle shuffle = new Shuffle(shuffleables);
        Long timeBefore = System.currentTimeMillis();
        shuffle.shuffle();
        shuffleables = shuffle.getShuffleables();
        Long timeAfter = System.currentTimeMillis();
        System.out.println(shuffleables);
        long timeTaken = timeAfter - timeBefore;
        double timePerSong = timeTaken / shuffleables.size();
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
        if (shuffleables.isEmpty()) {
            File musicDir = new File(System.getProperty("user.home") + "/Music");
            for (File f : Objects.requireNonNull(musicDir.listFiles())) {
                if (!f.getName().endsWith(".mp3")) {
                    continue;
                }
                shuffleables.add(new SongFile(f));
            }
        }
        Shuffle shuffle = new Shuffle(shuffleables);
        Long timeBefore = System.nanoTime();
        shuffle.shuffle();
        shuffleables = shuffle.getShuffleables();
        Long timeAfter = System.nanoTime();
        System.out.println("\n\n");
        long timeTaken = timeAfter - timeBefore;
        System.out.println("Shuffled in " + timeTaken + " Nanoseconds");
        int repeats = checkForRepeats(shuffleables);
        this.shuffle += repeats;
        System.out.println(repeats + " songs were found with the same artist before, after shuffle");
        timeBefore = System.nanoTime();
        shuffleables = shuffle.randomiseOrder(shuffleables);
        timeAfter = System.nanoTime();
        long randomTimeTaken = timeAfter - timeBefore;
        System.out.println("Shuffled in " + randomTimeTaken + " Nanoseconds");
        System.out.println(timeTaken / randomTimeTaken + "x difference between shuffle and random");
        repeats = checkForRepeats(shuffleables);
        random += repeats;
        System.out.println(repeats + " songs were found with the same artist before, after a random shuffle");
        trials++;
    }

    private int checkForRepeats(List<Shuffleable> shuffleables) {
        String title = "";
        String artist = "";
        int repeats = 0;
        for (Shuffleable s : shuffleables) {
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
