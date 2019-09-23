package net.lapismc.shuffle;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SongFile extends Shuffleable {

    private File file;

    public SongFile(File f) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        super();
        this.file = f;
        AudioFile audioFile = AudioFileIO.read(f);
        Tag tag = audioFile.getTag();
        String title = tag.getFirst(FieldKey.TITLE);
        String artist = tag.getFirst(FieldKey.ARTIST);
        String album = tag.getFirst(FieldKey.ALBUM);
        setTitle(title);
        setArtist(artist);
        setAlbum(album);
    }

    @Override
    public UUID getUuid() {
        return UUID.nameUUIDFromBytes((getTitle() + getFile().length()).getBytes());
    }

    public File getFile() {
        return file;
    }
}
