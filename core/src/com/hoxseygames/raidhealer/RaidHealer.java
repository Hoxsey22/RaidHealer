package com.hoxseygames.raidhealer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.hoxseygames.raidhealer.states.LoadingState;
import com.hoxseygames.raidhealer.states.StateManager;

public class RaidHealer extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "RAID HEALER";
	public static Music music;
	public static Skin ui;

	private SpriteBatch batch;
	private StateManager sm;

	public RaidHealer()	{
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		sm = new StateManager();
		ui = new Skin(Gdx.files.internal("pocket_healer_ui.json"));
		GameData.loadAudioSettings();
		sm.push(new LoadingState(sm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sm.update(Gdx.graphics.getDeltaTime());
		sm.render(batch);
	}

}
