package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;

/**
 * Created by Hoxsey on 6/30/2017.
 */
public class LoadingState extends State {

    private float progress;
    @SuppressWarnings("CanBeFinal")
    private ShapeRenderer shapeRenderer;
    private boolean isReady;
    private final Stage stage;
    private final Assets assets;
    private Label loadingText;
    private Player player;
    private Container<Label> container;

    public LoadingState(StateManager sm) {
        super(sm);

        Texture ls = new Texture(Gdx.files.internal("logo_screen.png"), true);
        ls.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.MipMapLinearNearest);

        Image logo = new Image(ls);

        logo.setBounds(RaidHealer.WIDTH/2 - 512/2,RaidHealer.HEIGHT/2 - 512/2+50,512, 512);

        Texture ll = new Texture(Gdx.files.internal("logo/libgdx_logo.png"), true);
        ll.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        Image libgdxLogo = new Image(ll);
        libgdxLogo.setBounds(RaidHealer.WIDTH/2 - 300/2, logo.getY() - 20, 300, 50 );


        createFont();

        progress = 0f;

        isReady = false;

        assets = new Assets();

        stage = new Stage(viewport);

        stage.addActor(logo);
        stage.addActor(libgdxLogo);
        stage.addActor(loadingText);

        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(2));

        assets.load();

    }

    private void createFont()   {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/game_font_large_border.fnt"));
        textStyle.font = font;

        loadingText = new Label("Loading...", textStyle);
        loadingText.setSize(2,2);
        loadingText.setColor(Color.GOLD);
        loadingText.setPosition((RaidHealer.WIDTH/2)-(loadingText.getWidth()/2),50);
        loadingText.setAlignment(Align.center);
    }

    @Override
    protected void handleInput() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(isReady) {
                    player = new Player(assets);
                    sm.push(new MainMenuState(sm, player));
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });

    }

    @Override
    public void update(float dt) {
        handleInput();
        if(!assets.update())    {
            progress = assets.getProgress();
        }
        else {
            if (!isReady) {
                isReady = true;
                loadingText.setText("Press the screen!");
                startLoadingTextAnimation();
            }

        }

    }

    public void startLoadingTextAnimation() {
        loadingText.addAction(Actions.repeat(100,
                Actions.sequence(
                        Actions.moveBy(0, 10, 0.5f),
                        Actions.moveBy(0,-10,0.5f)

                )
        ));
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClearColor(255,254,219,Color.DARK_GRAY.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sb.setProjectionMatrix(cam.combined);

        viewport.apply();

        cam.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
