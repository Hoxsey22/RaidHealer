package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hoxseygames.raidhealer.Assets;

/**
 * Created by Hoxsey on 7/4/2017.
 */
public class TestState extends State {

    private final Assets assets;
    private final Button frame;
    private final Stage stage;

    protected TestState(StateManager sm, Assets assets) {
        super(sm);
        this.assets = assets;
        frame = new Button(new Skin(Gdx.files.internal("frame_skin.json")));
        stage = new Stage(new FitViewport(800,480));
        frame.setBounds(100,100, 200, 80);
        frame.setBounds(150,0,200,100);
        stage.addActor(frame);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(Color.TAN.r,Color.TAN.g,Color.TAN.b,Color.TAN.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        update(Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
