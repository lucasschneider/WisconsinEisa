package org.anagumaeisa.wisconsineisa.musicservice.contentcatalogs;

public class Song {
    private String mediaId;
    private String title;
    private String titleJp;
    private String artist;
    private String artistJp;
    private String album;
    private String albumJp;
    private String genre;
    private int sec;
    private String mp3;
    private int drawable;
    private String albumArtResName;


    Song(String mediaId, String title, String titleJp, String artist, String artistJp, String album, String albumJp, String genre, int sec, String mp3, int drawable, String albumArtResName) {
        this.mediaId = mediaId;
        this.title = title;
        this.titleJp = titleJp;
        this.artist = artist;
        this.artistJp = artistJp;
        this.album = album;
        this.albumJp = albumJp;
        this.genre = genre;
        this.sec = sec;
        this.mp3 = mp3;
        this.drawable = drawable;
        this.albumArtResName = albumArtResName;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleJp() {
        return titleJp;
    }

    public String getArtist() {
        return artist;
    }

    public String getArtistJp() {
        return artistJp;
    }

    public String getAlbum() {
        return album;
    }

    public String getAlbumJp() {
        return albumJp;
    }

    public String getGenre() {
        return genre;
    }

    public int getSec() {
        return sec;
    }

    public String getMp3() {
        return mp3;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getAlbumArtResName() {
        return albumArtResName;
    }
}
