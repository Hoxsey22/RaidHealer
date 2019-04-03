package com.hoxseygames.raidhealer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.hoxseygames.raidhealer.Player.PlayerData;

/**
 * Created by Hoxsey on 9/28/2017.
 */

public class GameData {

    private static final Preferences prefs = Gdx.app.getPreferences("save");

    public static void clear()  {
        prefs.clear();
    }

    public static boolean load(Player player)   {

        if(prefs.contains("save"))  {
            Json json = new Json();
            String parsedData =  Base64Coder.decodeString(prefs.getString("save"));
            player.setData(json.fromJson(PlayerData.class, parsedData));
            return true;
        }
        else {
            save(player);
            load(player);
            return false;
        }
    }

    public static boolean save(Player player)   {
        Json json = new Json();
        json.toJson(player.getData());

        prefs.putString("save", Base64Coder.encodeString(json.toJson(player.getData())));
        prefs.flush();

        return true;
    }

    public static boolean loadAudioSettings()   {

        if(prefs.contains("audio settings"))  {
            Json json = new Json();
            AudioManager.setData(json.fromJson(AudioManager.AudioData.class, Base64Coder.decodeString(prefs.getString("audio settings"))));
            return true;
        }
        else {
            saveAudioSettings();
            return loadAudioSettings();
        }
    }

    public static boolean saveAudioSettings()   {
        Json json = new Json();
        json.toJson(AudioManager.getData());

        prefs.putString("audio settings", Base64Coder.encodeString(json.toJson(AudioManager.getData())));
        prefs.flush();

        return true;
    }


    public static void remove(String dataName)    {
        prefs.remove(dataName);
    }

    public static boolean doesDataExist(String dataName)    {
        return prefs.contains(dataName);
    }



}
