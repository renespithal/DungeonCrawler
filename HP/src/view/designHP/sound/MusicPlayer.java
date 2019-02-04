package view.designHP.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Created by Jenny on 10.01.2016.
 */
public class MusicPlayer {

    private URL source;
    private Media media;
    private MediaPlayer mediaPlayer;

    public MusicPlayer() {
        setMusic();
        play();
        repeat();
    }

    private void setMusic() {
        String source = ("hp\\media\\Harry Potter - Intro.mp3");
        media = new Media(source);
        mediaPlayer = new MediaPlayer(media);
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void repeat() {
        mediaPlayer.getOnRepeat();
    }

    /* Getter & Setter */
    public URL getSource() {
        return source;
    }

    public void setSource(URL source) {
        this.source = source;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
