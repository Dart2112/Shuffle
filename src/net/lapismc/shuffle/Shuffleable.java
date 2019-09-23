package net.lapismc.shuffle;

import java.util.UUID;

public class Shuffleable implements Comparable<Shuffleable> {

    private String title, artist, album;
    private double index;

    /**
     * The default constructor
     *
     * @param title  The title of this track
     * @param artist The artist of this track
     * @param album  The artist of this track
     */
    public Shuffleable(String title, String artist, String album) {
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

    /**
     * Should only be used if you are going to manually set the title, artist and album
     */
    Shuffleable() {
    }

    public UUID getUuid() {
        return UUID.nameUUIDFromBytes((getTitle() + getArtist(true)).getBytes());
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
    public int compareTo(Shuffleable shuffleable) {
        return (int) ((this.getIndex() - shuffleable.getIndex()) * 1000);
    }

}
