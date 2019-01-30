package com.hoxseygames.raidhealer;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hoxsey on 2/17/2018.
 */

public class AudioManager {

    public static class AudioData implements Serializable {

        public float musicVolume;
        public float sfxVolume;


        public AudioData() {
            musicVolume = 0.5f;
            sfxVolume = 0.5f;
        }

        public void setData(float musicVolume, float sfxVolume){
            this.musicVolume = musicVolume;
            this.sfxVolume = sfxVolume;
        }
    }

    public static float musicVolume = 0.5f;
    public static float sfxVolume = 0.5f;
    private static Music music;
    @SuppressWarnings("CanBeFinal")
    private static ArrayList<Sound> soundEffects = new ArrayList<>();
    @SuppressWarnings("CanBeFinal")
    private static AudioData audioData = new AudioData();


    /**
     * Plays the sfx. If the sound effect list contains the sfx then it will not add the sfx to the list.
     * @param sfx
     * @param looping
     */
    public static void playSFX(Sound sfx, boolean looping)   {
        if(sfx != null) {

            if(soundEffects.contains(sfx))   {
                stopSFX(sfx);
                if(looping)
                    sfx.loop(sfxVolume);
                else    {
                    sfx.play(sfxVolume);
                }
            }
            else {
                addSFX(sfx);
                if (looping)
                    sfx.loop(sfxVolume);
                else
                    sfx.play(sfxVolume);
            }
        }
    }

    /**
     * Stops the sfx.
     * @param sfx
     */
    public static void stopSFX(Sound sfx)    {
        if(sfx != null)    {
            sfx.stop();
        }
    }

    /**
     * Puases the sfx.
     * @param sfx
     */
    @SuppressWarnings("unused")
    public static void pauseSFX(Sound sfx)   {
        if(sfx != null)    {
            sfx.pause();
        }
    }

    public static void updateSFXVolume(float newsfxVolume)   {
        sfxVolume = newsfxVolume;
        /*for(int i = 0; i < soundEffects.size(); i++)   {
            if(soundEffects.get(i) != null){
                soundEffects.get(i).setVolume(sfxVolume,soundEffects.get(i));
            }
        }*/
    }

    /**
     * Sets music to the new music object given and then plays music based on param values.
     * @param newMusic
     *
     */
    public static void playMusic(Music newMusic) {
        if (music == null) {
            setMusic(newMusic);
        }
        else {
            if(music.equals(newMusic))
                return;
            clearMusic();
            setMusic(newMusic);
        }
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
    }

    /**
     * Plays the music object if not null.
     */
    @SuppressWarnings("unused")
    public static void playMusic() {
        if(music != null) {
            music.setLooping(true);
            music.setVolume(musicVolume);
            music.play();
        }
    }

    /**
     * Stops the music object if not null.
     */
    @SuppressWarnings("unused")
    public static Music stopMusic() {
        if(music != null)
            music.stop();
        return music;
    }

    /**
     * Pauses music object if not null.
     */
    @SuppressWarnings("unused")
    public static void pauseMusic()    {
        if(music != null)
            music.pause();
    }

    public static void updateMusicVolume(float newVolume) {
        musicVolume = newVolume;
        if(music != null && music.isPlaying()) {
            music.setVolume(newVolume);
        }
    }




    /**
     * Disposes the music object.
     */
    private static void clearMusic()  {
        if(music != null) {
            music.stop();
            //music = null;
        }
        System.out.println("Music has been cleared.");
    }

    /**
     * Disposes all Sound objects.
     */
    private static void clearSFX()    {
        for(int i = 0; i < soundEffects.size(); i++)   {
            if(soundEffects.get(i) != null) {
                soundEffects.get(i).stop();
                soundEffects.remove(i);
            }
        }
        System.out.println("All SFX have been cleared.");
    }

    /*
        Getters and Setters
     */

    /**
     * Sets Music objects.
     * @param newMusic
     */
    private static void setMusic(Music newMusic)  {
        music = newMusic;
    }

    /**
     * Gets current Music object.
     * @return
     */
    @SuppressWarnings("unused")
    public static Music getMusic() {
        return music;
    }

    /**
     * Gets current list of SFX objects.
     * @return
     */
    @SuppressWarnings("unused")
    public static ArrayList<Sound> getSoundEffects() {
        return soundEffects;
    }

    /**
     * Adds new Sound Object to SFX list.
     * @param sound
     */
    private static void addSFX(Sound sound) {
        soundEffects.add(sound);
    }

    /**
     * Gets music volume.
     * @return musicVolume
     */
    @SuppressWarnings("unused")
    public static float getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the music volume.
     * @param volume range 0.0f - 1.0f
     */
    @SuppressWarnings("unused")
    public static void setMusicVolume(float volume) {
        musicVolume = volume;
    }

    /**
     * Gets the Sound effects volume.
     * @return sfxVolume
     */
    @SuppressWarnings("unused")
    public static float getSfxVolume() {
        return sfxVolume;
    }

    /**
     * Sets the Sound effect's volume.
     * @param volume range 0.0f - 1.0f
     */
    @SuppressWarnings("unused")
    public static void setSfxVolume(float volume) {
        sfxVolume = volume;
    }


    public static void setData(AudioData data)   {
        audioData.setData(data.musicVolume, data.sfxVolume);
        musicVolume = audioData.musicVolume;
        sfxVolume = audioData.sfxVolume;
    }

    public static AudioData getData() {
        audioData.setData(musicVolume, sfxVolume);
        return audioData;
    }

    /**
     * Disposes all the music and sound effects.
     */
    public static void clearAll()    {
        clearMusic();
        clearSFX();
    }
}
