package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hoxseygames.raidhealer.RaidHealer;

/**
 * Created by Hoxsey on 5/27/2017.
 */
abstract class State extends ApplicationAdapter {
    public final OrthographicCamera cam;
    final com.hoxseygames.raidhealer.states.StateManager sm;
    final FitViewport viewport;

    State(com.hoxseygames.raidhealer.states.StateManager sm)   {
        this.sm = sm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, RaidHealer.WIDTH, RaidHealer.HEIGHT);
        viewport = new FitViewport(RaidHealer.WIDTH,RaidHealer.HEIGHT,cam);
        Vector3 mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
        viewport.update(RaidHealer.WIDTH, RaidHealer.HEIGHT, true);
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height, true);
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
    }
}
