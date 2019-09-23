package net.lapismc.shuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PartialShuffle {

    private List<UUID> shuffled = new ArrayList<>();

    /**
     * @param toShuffle       The list of Shuffleables that you wish to process
     * @param buffer          The buffer applied to the distribution, 0-49, higher is better shuffled but less random
     * @param numberToShuffle The number of items to return shuffled
     */
    public List<Shuffleable> shuffle(List<Shuffleable> toShuffle, float buffer, int numberToShuffle) {
        Random r = new Random();
        List<Shuffleable> list = new ArrayList<>();
        while (list.size() < numberToShuffle + 1 && shuffled.size() != toShuffle.size()) {
            Shuffleable s = toShuffle.get(r.nextInt(toShuffle.size()));
            if (shuffled.contains(s.getUuid()))
                continue;
            list.add(toShuffle.get(r.nextInt(toShuffle.size())));
            shuffled.add(s.getUuid());
        }
        Shuffle s = new Shuffle();
        s.shuffle(list, buffer);
        return s.getShuffleables();
    }

}
