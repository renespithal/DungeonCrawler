package view.design.scenes;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

/**
 * Created by Rene on 22.01.2016.
 */
public class MusicPlayer {

    private URL source;
    private Media media;
    private MediaPlayer mediaPlayer;



    // DUNGEON CRAWLER MUSIC

    private AudioClip intro = new AudioClip(getClass().getResource("/sounds/Menu2BGM.mp3").toExternalForm());
    private AudioClip level1 = new AudioClip(getClass().getResource("/sounds/Level1BGM.mp3").toExternalForm());
    private AudioClip level2 = new AudioClip(getClass().getResource("/sounds/Level2BGM.mp3").toExternalForm());
    private AudioClip level3 = new AudioClip(getClass().getResource("/sounds/Level3BGM.mp3").toExternalForm());
    private AudioClip level4 = new AudioClip(getClass().getResource("/sounds/Level4BGM.mp3").toExternalForm());
    private AudioClip beforeFinalBossEncounter = new AudioClip(getClass().getResource("/sounds/LevelBeforeEndbossBGM.mp3").toExternalForm());
    private AudioClip finalBoss = new AudioClip(getClass().getResource("/sounds/LevelEndbossBGM.mp3").toExternalForm());
    private AudioClip victory = new AudioClip(getClass().getResource("/sounds/WinBGM.mp3").toExternalForm());
    private AudioClip dead = new AudioClip(getClass().getResource("/sounds/DeadSE.mp3").toExternalForm());
    private AudioClip gameOver = new AudioClip(getClass().getResource("/sounds/GameOverBGM.mp3").toExternalForm());
    private AudioClip attackMa = new AudioClip(getClass().getResource("/sounds/attackMa.wav").toExternalForm());
    private AudioClip attackRa = new AudioClip(getClass().getResource("/sounds/attackRa.wav").toExternalForm());
    private AudioClip attackMe = new AudioClip(getClass().getResource("/sounds/attackMe.wav").toExternalForm());
    private AudioClip click = new AudioClip(getClass().getResource("/sounds/ClickSound.wav").toExternalForm());
    private AudioClip buzzer = new AudioClip(getClass().getResource("/sounds/Buzzer.wav").toExternalForm());
    private AudioClip walk = new AudioClip(getClass().getResource("/sounds/Walk.wav").toExternalForm());
    private AudioClip potion = new AudioClip(getClass().getResource("/sounds/itemSound.wav").toExternalForm());
    private AudioClip attackMonster = new AudioClip(getClass().getResource("/sounds/AttackMonster.wav").toExternalForm());
    private AudioClip attackFinalBoss = new AudioClip(getClass().getResource("/sounds/AttackFinalBoss.wav").toExternalForm());
    private AudioClip iceMagicSound = new AudioClip(getClass().getResource("/sounds/IceMagicSound.wav").toExternalForm());
    private AudioClip windMagicSound = new AudioClip(getClass().getResource("/sounds/WindMagicSound.wav").toExternalForm());
    private AudioClip healMagicSoound = new AudioClip(getClass().getResource("/sounds/HealMagicSound.wav").toExternalForm());
    private AudioClip darkMagicSound = new AudioClip(getClass().getResource("/sounds/DarkMagicSound.wav").toExternalForm());





    // HP MUSIC

    private AudioClip HarryPotterIntro = new AudioClip(getClass().getResource("/sounds/HarryPotterIntro.mp3").toExternalForm());
    private AudioClip HPMagic1 = new AudioClip(getClass().getResource("/sounds/MagicHP.wav").toExternalForm());
    private AudioClip HPMagic2 = new AudioClip(getClass().getResource("/sounds/MagicHP2.wav").toExternalForm());
    private AudioClip HPMagic3 = new AudioClip(getClass().getResource("/sounds/MagicHP3.wav").toExternalForm());


    public MusicPlayer() {


    }



    // DUNGEON CRAWLER

    public void playIntro(){
        intro.setVolume(0.03);
        intro.play();

    }

    public void stopIntro(){intro.stop();}


    public void playLevel1(){
        level1.setVolume(0.03);
        level1.play();
    }

    public void stopLevel1(){level1.stop();}


    public void playLevel2(){
        level2.setVolume(0.03);
        level2.play();}

    public void stopLevel2(){level2.stop();}


    public void playLevel3(){
        level3.setVolume(0.03);
        level3.play();
    }

    public void stopLevel3(){level3.stop();}


    public void playLevel4(){
        level4.setVolume(0.03);
        level4.play();
    }

    public void stopLevel4(){level4.stop();}


    public void playLevelBeforeFinalBoss(){
        beforeFinalBossEncounter.setVolume(0.03);
        beforeFinalBossEncounter.play();
    }

    public void stopLevelBeforeFinalBoss(){beforeFinalBossEncounter.stop();}


    public void playLevelFinalBoss(){
        finalBoss.setVolume(0.03);
        finalBoss.play();
    }

    public void stopLevelFinalBoss(){finalBoss.stop();}


    public void playVictory(){
        victory.setVolume(0.07);
        victory.play();}

    public void stopVictory(){victory.stop();}


    public void playDeadSE(){
        dead.setVolume(0.07);
        dead.play();}

    public void stopDeadSE(){dead.stop();}


    public void playGameOver(){gameOver.play();}

    public void stopGameOver(){gameOver.stop();}


    public void playAttackMe(){
        attackMe.setVolume(0.07);
        attackMe.play();}

    public void playAttackRa(){
        attackRa.setVolume(0.09);
        attackRa.play();}

    public void playAttackMa(){
        attackMa.setVolume(0.09);
        attackMa.play();}

    public void playIceMagic(){
        iceMagicSound.setVolume(0.09);
        iceMagicSound.play();
    }

    public void playWindMagic(){
        windMagicSound.setVolume(0.09);
        windMagicSound.play();
    }

    public void playHealMagicSound(){
        healMagicSoound.setVolume(0.09);
        healMagicSoound.play();
    }

    public void playDarkMagicSound(){
        darkMagicSound.setVolume(0.09);
        darkMagicSound.play();
    }

    public void playAttackMonster(){
        attackMonster.setVolume(0.09);
        attackMonster.play();
    }

    public void playAttackFinalBoss(){
        attackFinalBoss.setVolume(0.09);
        attackFinalBoss.play();
    }

    public void playClick(){
        click.setVolume(0.1);
        click.play();}

    public void playBuzzer(){buzzer.play();}

    public void playWalk(){
        walk.setVolume(0.09);
        walk.play();}

    public void playPotionSound(){
        potion.setVolume(0.09);
        potion.play();

    }




    // HARRY POTTER

    public void playHarryPotterIntro(){

        HarryPotterIntro.play();
    }

    public void playHPMagic1(){
        HPMagic1.setVolume(0.09);
        HPMagic1.play();
    }

    public void playHPMagic2(){
        HPMagic2.setVolume(0.09);
        HPMagic2.play();
    }

    public void playHPMagic3(){
        HPMagic3.setVolume(0.09);
        HPMagic3.play();
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