package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Button;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.Text;
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
    public Button reset;
    private Talent selectedTalent;
    private final Text pointTracker;
    private ShutterAnimation shutterAnimation;

    public TalentSelectionState(StateManager sm, Player player) {
        super(sm);
        assets = player.getAssets();

        this.player = player;

        talentTree = player.getTalentTree();
        talentTree.setName("Talent Tree");

        pointTracker = new Text("POINTS:",24,Color.WHITE,false, assets);
        pointTracker.setName("Point tracker");

        Button select = new Button("RESET", false, assets);
        select.setPosition(talentTree.getLeft(), 25);

        Button done = new Button("DONE", false, assets);
        done.setPosition(talentTree.getRight() - done.getWidth(), 25);

        talentTreeTitle = new Text("Talent Tree", 45, Color.SKY, true, assets);
        talentTreeTitle.setName("Talent Tree Title");

        Image background = new Image(assets.getTexture(assets.talentBg));
        background.setBounds(0,0,RaidHealer.WIDTH, RaidHealer.HEIGHT);
        background.setName("bg");

        stage = new Stage(viewport);
        stage.addActor(background);
        stage.addActor(talentTree);
        stage.addActor(select);
        stage.addActor(done);

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

    }

    @Override
    protected void handleInput() {
        Gdx.input.setInputProcessor( new InputProcessor() {
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
                else    {
                    Actor hit = stage.hit(coord.x, coord.y, false);
                    if(hit != null) {
                        switch (hit.getName())  {
                            case "RESET":
                                if(player.getTalentTree().getTotalPoints() != player.getTalentTree().getUnusedPoints()) {
                                    sm.referencePlayer(player);
                                    if(!sm.showAd(2))    {
                                        player.getTalentTree().reset();
                                    }
                                }
                                break;
                            case "DONE":
                                player.save();
                                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                                    @Override
                                    public void run() {
                                        sm.set(new MapState(sm, player));
                                    }
                                });
                                shutterAnimation.start();
                                break;
                        }
                    }


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
