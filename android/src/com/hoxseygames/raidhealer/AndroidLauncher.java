package com.hoxseygames.raidhealer;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication{

	private static final String TAG = "AndroidLauncher";
	SharedPreferences sharedPreferences = null;
	private Player player;
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new RaidHealer(), config);

	}



}
