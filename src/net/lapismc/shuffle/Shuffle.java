package net.lapismc.shuffle;

import java.util.*;

public class Shuffle {

    private List<Song> songs;
    // <Artist, Songs>
    private Map<String, List<Song>> artists = new HashMap<>();
    private Random r;

    public Shuffle(List<Song> songs) {
        this.songs = songs;
        r = new Random();
    }

    public void shuffle() {
        //Classify songs
        classifyByArtist();
        //Distribute albums within artists
        for (String artist : artists.keySet()) {
            List<Song> songs = artists.get(artist);
            //Create lists with each album
            Map<String, List<Song>> albums = new HashMap<>();
            for (Song s : songs) {
                if (albums.containsKey(s.getAlbum(true))) {
                    List<Song> list = albums.get(s.getAlbum(true));
                    list.add(s);
                    albums.put(s.getAlbum(true), list);
                } else {
                    List<Song> list = new ArrayList<>();
                    list.add(s);
                    albums.put(s.getAlbum(true), list);
                }
            }
            //Distribute each album over the length of all artist songs
            for (String album : albums.keySet()) {
                List<Song> albumSongs = albums.get(album);
                albums.put(album, distribute(randomiseOrder(albumSongs), songs.size()));
            }
            List<Song> distributedArtistSongs = new ArrayList<>();
            for (List<Song> albumSongs : albums.values()) {
                distributedArtistSongs.addAll(albumSongs);
            }
            //This is the sorted list for each artist
            Collections.sort(distributedArtistSongs);
            artists.put(artist, distributedArtistSongs);
        }
        //Distribute each artist
        for (String artist : artists.keySet()) {
            List<Song> songs = artists.get(artist);
            artists.put(artist, distribute(songs, this.songs.size()));
        }
        //Collapse the artists distributed songs back to a single playlist
        List<Song> distributedPlaylist = new ArrayList<>();
        for (List<Song> artistSongs : artists.values()) {
            distributedPlaylist.addAll(artistSongs);
        }
        //Sort by the index values from all the songs
        Collections.sort(distributedPlaylist);
        songs = distributedPlaylist;
    }

    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Loads all the songs into the artists map
     */
    private void classifyByArtist() {
        artists.clear();
        for (Song s : songs) {
            String artist = s.getArtist(true);
            if (artists.containsKey(artist)) {
                //If the artist already exists just add this song
                List<Song> list = artists.get(artist);
                list.add(s);
                artists.put(artist, list);
            } else {
                //Add the artist to the map with this song in the list
                List<Song> list = new ArrayList<>();
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
     * @param toDistribute The list of songs to distribute
     * @param length       The length of the overall playlist to distribute along
     * @return Returns a sorted list with indexes distributed
     */
    private List<Song> distribute(List<Song> toDistribute, int length) {
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
            Song s = toDistribute.get(i);
            s.setIndex(index);
            toDistribute.set(i, s);
        }
        Collections.sort(toDistribute);
        return toDistribute;
    }

    /**
     * Randomizes the order of the elements in the list
     * Used for randomizing the order of songs in albums
     *
     * @param songs The songs to order
     * @return Returns the randomized list of songs
     */
    public List<Song> randomiseOrder(List<Song> songs) {
        List<Song> randomized = new ArrayList<>();
        for (Song s : songs) {
            s.setIndex(r.nextInt(songs.size()) + r.nextDouble());
            randomized.add(s);
        }
        Collections.sort(randomized);
        return randomized;
    }

}
