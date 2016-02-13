package com.etherblood.cardsswingdisplay;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Philipp
 */
public class AudioFactory {

//    private final HashMap<String, AudioInputStream> audios = new HashMap<>();
    private final HashMap<String, File> audioPaths = new HashMap<>();
    private final HashMap<String, URL> audioUrls = new HashMap<>();
    public static final AudioFactory INSTANCE = new AudioFactory();

    public void playSound(String soundId) {
        File path = audioPaths.get(soundId);
        URL url = audioUrls.get(soundId);
//        if (audioInputStream == null) {
//            return;
//        }
        try {
            AudioInputStream audioInputStream;//audios.get(soundId);
            if(path != null) {
                audioInputStream = AudioSystem.getAudioInputStream(path);
            } else if(url != null) {
                audioInputStream = AudioSystem.getAudioInputStream(url);
            } else {
                return;
            }
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerSound(String soundId, File file) {
        audioPaths.put(soundId, file.getAbsoluteFile());
//        try {
//            audios.put(soundId, AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile()));
//        } catch (UnsupportedAudioFileException | IOException ex) {
//            throw new RuntimeException(ex);
//        }
    }

    public void registerSound(String soundId, URL url) {
        audioUrls.put(soundId, url);
//        try {
//            audios.put(soundId, AudioSystem.getAudioInputStream(url));
//        } catch (UnsupportedAudioFileException | IOException ex) {
//            throw new RuntimeException(ex);
//        }
    }
}
