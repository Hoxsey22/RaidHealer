package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.Text;
import com.hoxseygames.raidhealer.WindowFrame;
import com.hoxseygames.raidhealer.encounters.spells.Talents.Talent;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.top;

/**
 * Created by Hoxsey on 7/2/2017.
 */
public class TalentSelectionState extends State {

    private final Stage stage;
    private final Player player;
    private final Assets assets;
    private final Text talentTreeTitle;
    private Text title;
    private Text body;
    private final TalentTree talentTree;
    private TextButton resetButton;
    private TextButton doneButton;
    private Talent selectedTalent;
    private final Text pointTracker;
    private ShutterAnimation shutterAnimation;
    private boolean hasReset;
    protected WindowFrame ngConfirmationWindow;
    protected Label ngConfirmationText;

    public TalentSelectionState(StateManager sm, Player player) {
        super(sm);
        assets = player.getAssets();

        this.player = player;

        hasReset = false;

        talentTree = player.getTalentTree();
        talentTree.setName("Talent Tree");

        pointTracker = new Text("POINTS:",24,Color.WHITE,false, assets);
        pointTracker.setName("Point tracker");

        resetButton = new TextButton("RESET", assets.getSkin());
        resetButton.setPosition(talentTree.getLeft(), 25);

        doneButton = new TextButton("DONE", assets.getSkin());
        doneButton.setPosition(talentTree.getRight() - doneButton.getWidth(), 25);

        setupTextButtonListners();

        talentTreeTitle = new Text("Talent Tree", 45, Color.SKY, true, assets);
        talentTreeTitle.setName("Talent Tree Title");

        Image background = new Image(assets.getTexture(assets.talentBg));
        background.setBounds(0,0,RaidHealer.WIDTH, RaidHealer.HEIGHT);
        background.setName("bg");

        stage = new Stage(viewport);
        stage.addActor(background);
        stage.addActor(talentTree);
        stage.addActor(resetButton);
        stage.addActor(doneButton);

        createText();

        shutterAnimation = new ShutterAnimation(stage, assets, false);
        shutterAnimation.start();
    }

    private void createText()    {
        Table lowerTable = new Table();
        lowerTable.setName("Lower Table");

        title  = new Text("", 24, Color.BLACK, false, assets);
        body  = new Text("Double tab to select.", 16, Color.BLACK, false, assets);
        body.setWrap();

        title.setWrap();
        title.setAlignment(top);

        lowerTable.setBounds(talentTree.getLeft(), talentTree.getBottom() - 20 - 100, talentTree.getRight() - talentTree.getLeft(), 100);
        lowerTable.top();
        lowerTable.add(title.getLabel());
        lowerTable.row();
        lowerTable.add(body.getLabel()).width(lowerTable.getWidth());
        stage.addActor(lowerTable);

        Table topTable = new Table();
        topTable.setName("Top Table");

        talentTreeTitle.setWrap();
        talentTreeTitle.setAlignment(center);

        pointTracker.setAlignment(center);
        topTable.setBounds(talentTree.getLeft(), talentTree.getTop(),talentTree.getRight()-talentTree.getLeft(), 90);
        topTable.top();
        topTable.add(talentTreeTitle.getLabel());
        topTable.row();
        topTable.add(pointTracker.getLabel()).width(topTable.getWidth());
        stage.addActor(topTable);

        initResetConfirmationWindow();

    }

    @Override
    protected void handleInput() {
        Gdx.input.setInputProcessor( new InputMultiplexer(stage,new InputProcessor() {
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
                Vector2 coord = stage.screenToStageCoordinates(new Vector2((float)screenX,(float)screenY));
                if(coord.y > talentTree.getBottom() - 10) {
                    Talent hit = talentTree.hit(coord.x, coord.y);
                    if (hit != null) {
                        if(selectedTalent != null && selectedTalent.getName().equalsIgnoreCase(hit.getName()) && !selectedTalent.isSelected())    {
                            talentTree.usePoint(selectedTalent);
                        }
                        selectedTalent = hit;
                        title.setText(hit.getName());
                        body.setText(hit.getDescription());
                    }
                    return false;
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
        }));


    }

    public void setupTextButtonListners()   {

        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if(player.getLevel() > 4) {
                    if (player.getTalentTree().getTotalPoints() != player.getTalentTree().getUnusedPoints() && !hasReset) {
                        ngConfirmationWindow.show(stage);
                    } else {
                        player.getTalentTree().reset();
                    }
                }
            }
        });

        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.save();
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new MapState(sm, player));
                    }
                });
                shutterAnimation.start();
            }
        });



    }


    protected void initResetConfirmationWindow() {
        ngConfirmationWindow = new WindowFrame(RaidHealer.ui);

        ngConfirmationText = new Label("This is will cost 1 level.\n Are you sure?", RaidHealer.ui);
        ngConfirmationText.getStyle().fontColor = Color.YELLOW;
        ngConfirmationText.setFontScale(0.8f);
        ngConfirmationText.setWrap(true);
        ngConfirmationText.setAlignment(Align.center);

        TextButton confirmButton = new TextButton("Reset",RaidHealer.ui, "small_button");
        confirmButton.setTouchable(Touchable.enabled);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hasReset = true;
                player.setLevel(player.getLevel()-1);
                player.getTalentTree().reset();
                ngConfirmationWindow.hide();
            }
        });

        TextButton backButton = new TextButton("Back",RaidHealer.ui, "small_button");
        backButton.setTouchable(Touchable.enabled);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ngConfirmationWindow.hide();
            }
        });

        ngConfirmationWindow.center();
        ngConfirmationWindow.add(ngConfirmationText).width(ngConfirmationWindow.getWidth()).center().colspan(2).pad(20).row();
        ngConfirmationWindow.add(confirmButton).center();
        ngConfirmationWindow.add(backButton).center();
    }

    @Override
    public void update(float dt) {
        handleInput();
        pointTracker.setText("POINTS: "+talentTree.getUnusedPoints());
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(Color.BLACK.r,Color.BLACK.g,Color.BLACK.b,Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sb.setProjectionMatrix(cam.combined);

        viewport.apply();

        cam.update();

        update(Gdx.graphics.getDeltaTime());

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height,true);
    }

    @Override
    public void dispose() {

    }
}
