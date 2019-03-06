package com.hoxseygames.raidhealer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hoxseygames.raidhealer.AdHandler;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = RaidHealer.WIDTH;
		config.height = RaidHealer.HEIGHT;
		config.title = RaidHealer.TITLE;
		config.resizable = false;
		new LwjglApplication(new RaidHealer(new AdHandler() {
			@Override
			public void showAds(int controller) {

			}

			@Override
			public void importPlayer(Player player) {

			}
		}), config);
	}
}
