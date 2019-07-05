import net.lapismc.shuffle.Shuffle;
import net.lapismc.shuffle.Song;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestShuffle {

    @Test
    public void runShuffle() {
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
        Shuffle shuffle = new Shuffle(songs);
        shuffle.shuffle();
        songs = shuffle.getSongs();
        System.out.println(songs);
    }

}
