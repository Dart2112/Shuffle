package net.lapismc.shuffle;

public class Song implements Comparable<Song> {

    private String title, artist, album;
    private double index;

    public Song(String title, String artist, String album) {
        if (title == null || artist == null) {
            throw new IllegalArgumentException("Title and Artist must not be null!");
        }
        if (album == null) {
            album = "";
        }
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    @Override
    public int compareTo(Song song) {
        return (int) (song.getIndex() - this.getIndex());
    }
}
