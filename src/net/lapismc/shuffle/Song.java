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
        this.artist = artist;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist(boolean lowerCase) {
        if (lowerCase)
            return artist.toLowerCase();
        else
            return artist;
    }

    String getAlbum(boolean lowerCase) {
        if (lowerCase)
            return album.toLowerCase();
        else
            return album;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setArtist(String artist) {
        this.artist = artist;
    }

    void setAlbum(String album) {
        this.album = album;
    }

    private double getIndex() {
        return index;
    }

    void setIndex(double index) {
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
