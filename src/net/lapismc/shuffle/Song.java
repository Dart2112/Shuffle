package net.lapismc.shuffle;

public class Song implements Comparable<Song> {

    private String title, artist, album;
    private double index;

    Song() {
    }

    public Song(String title, String artist, String album) {
        if (title == null || artist == null) {
            throw new IllegalArgumentException("Title and Artist must not be null!");
        }
        if (album == null) {
            album = "";
        }
        this.title = title;
        this.artist = artist.toLowerCase();
        this.album = album.toLowerCase();
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

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setArtist(String artist) {
        this.artist = artist;
    }

    protected void setAlbum(String album) {
        this.album = album;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return title + " : " + artist + " : " + album + " : " + index + "\n";
    }

    @Override
    public int compareTo(Song song) {
        return (int) ((this.getIndex() - song.getIndex()) * 1000);
    }

}
