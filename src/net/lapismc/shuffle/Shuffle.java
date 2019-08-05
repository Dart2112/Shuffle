package net.lapismc.shuffle;

import java.util.*;

public class Shuffle {

    private List<Shuffleable> shuffleables;
    // <Artist, Track>
    private Map<String, List<Shuffleable>> artists = new HashMap<>();
    private Random r;

    public Shuffle(List<Shuffleable> shuffleables) {
        this.shuffleables = shuffleables;
        r = new Random();
    }

    public void shuffle() {
        //Classify Tracks
        classifyByArtist();
        //Distribute albums within artists
        for (String artist : artists.keySet()) {
            List<Shuffleable> shuffleables = artists.get(artist);
            //Create lists with each album
            Map<String, List<Shuffleable>> albums = new HashMap<>();
            for (Shuffleable s : shuffleables) {
                if (albums.containsKey(s.getAlbum(true))) {
                    List<Shuffleable> list = albums.get(s.getAlbum(true));
                    list.add(s);
                    albums.put(s.getAlbum(true), list);
                } else {
                    List<Shuffleable> list = new ArrayList<>();
                    list.add(s);
                    albums.put(s.getAlbum(true), list);
                }
            }
            //Distribute each album over the length of all artist tracks
            for (String album : albums.keySet()) {
                List<Shuffleable> albumTracks = albums.get(album);
                albums.put(album, distribute(randomiseOrder(albumTracks), shuffleables.size()));
            }
            List<Shuffleable> distributedArtistTracks = new ArrayList<>();
            for (List<Shuffleable> albumTracks : albums.values()) {
                distributedArtistTracks.addAll(albumTracks);
            }
            //This is the sorted list for each artist
            Collections.sort(distributedArtistTracks);
            artists.put(artist, distributedArtistTracks);
        }
        //Distribute each artist
        for (String artist : artists.keySet()) {
            List<Shuffleable> shuffleables = artists.get(artist);
            artists.put(artist, distribute(shuffleables, this.shuffleables.size()));
        }
        //Collapse the artists distributed tracks back to a single playlist
        List<Shuffleable> distributedPlaylist = new ArrayList<>();
        for (List<Shuffleable> artistTracks : artists.values()) {
            distributedPlaylist.addAll(artistTracks);
        }
        //Sort by the index values from all the tracks
        Collections.sort(distributedPlaylist);
        shuffleables = distributedPlaylist;
    }

    public List<Shuffleable> getShuffleables() {
        return shuffleables;
    }

    /**
     * Loads all the tracks into the artists map
     */
    private void classifyByArtist() {
        artists.clear();
        for (Shuffleable s : shuffleables) {
            String artist = s.getArtist(true);
            if (artists.containsKey(artist)) {
                //If the artist already exists just add this track
                List<Shuffleable> list = artists.get(artist);
                list.add(s);
                artists.put(artist, list);
            } else {
                //Add the artist to the map with this track in the list
                List<Shuffleable> list = new ArrayList<>();
                list.add(s);
                artists.put(artist, list);
            }
        }
    }

    /**
     * Evenly and somewhat randomly distribute the list over the length
     * Should be used for each smaller section
     * Items order in the list should also be randomised before they are distributed
     *
     * @param toDistribute The list of tracks to distribute
     * @param length       The length of the overall playlist to distribute along
     * @return Returns a sorted list with indexes distributed
     */
    private List<Shuffleable> distribute(List<Shuffleable> toDistribute, int length) {
        int range = length / toDistribute.size();
        //The amount of buffer on each side that should be applied to the randomness, max 49.00
        float x = 20.0f;
        //This value is x% of range
        int p = (int) (range * (x / 100.0f));
        for (int i = 0; i < toDistribute.size(); i++) {
            //This gets a random in the lower x*2% of the range to be shifted later to the middle
            int randomMax = range - (p * 2);
            //The latter half of this does the main displacement and the x% shift to the middle
            double index = r.nextInt(randomMax) + (range * i) + p + r.nextDouble();
            Shuffleable s = toDistribute.get(i);
            s.setIndex(index);
            toDistribute.set(i, s);
        }
        Collections.sort(toDistribute);
        return toDistribute;
    }

    /**
     * Randomizes the order of the elements in the list
     * Used for randomizing the order of tracks in albums
     *
     * @param tracks The tracks to order
     * @return Returns the randomized list of tracks
     */
    public List<Shuffleable> randomiseOrder(List<Shuffleable> tracks) {
        List<Shuffleable> randomized = new ArrayList<>();
        for (Shuffleable s : tracks) {
            s.setIndex(r.nextInt(tracks.size()) + r.nextDouble());
            randomized.add(s);
        }
        Collections.sort(randomized);
        return randomized;
    }

}
