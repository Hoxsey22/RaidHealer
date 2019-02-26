package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.BossIcon;
import com.hoxseygames.raidhealer.InfoFrame;
import com.hoxseygames.raidhealer.MapFrame;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.Strings;

/**
 * Created by Hoxsey on 7/11/2017.
 */
public class MapState extends State {

    private final Player player;
    private final Stage stage;
    private int page;
    private final Assets assets;
    private MapFrame mapFrame;
    private BossIcon selectedLevel;

    private final ImageButton pageLeft;
    private final ImageButton pageRight;
    private TextButton talentButton;
    private TextButton startButton;
    private TextButton spellButton;
    private Table buttonTable;
    private int previousState;
    private ShutterAnimation shutterAnimation;


    public MapState(StateManager sm, Player player) {
        super(sm);

        if(player.getLevel() < 2)    {
            player.setLevel(2);
        }

        stage = new Stage(viewport);

        previousState = 0;

        Gdx.input.setInputProcessor(stage);

        assets = player.getAssets();

        AudioManager.playMusic(assets.getMusic(assets.mainMusic));

        pageLeft = new ImageButton(assets.getSkin(), "page_left");
        pageLeft.setBounds(40, 550, 30,30);

        pageRight = new ImageButton(assets.getSkin(), "page_right");
        pageRight.setBounds(410, 550, 30,30);

        page = 1;

        this.player = player;

        startPage();
        createButtons();
        createButtonListeners();

        shutterAnimation = new ShutterAnimation(stage, assets, false);
        shutterAnimation.start();
    }

    public MapState(StateManager sm, Player player, int previousState) {
        super(sm);

        stage = new Stage(viewport);

        this.previousState = previousState;

        Gdx.input.setInputProcessor(stage);

        assets = player.getAssets();

        AudioManager.playMusic(assets.getMusic(assets.mainMusic));

        pageLeft = new ImageButton(assets.getSkin(), "page_left");
        pageLeft.setBounds(40, 550, 30,30);

        pageRight = new ImageButton(assets.getSkin(), "page_right");
        pageRight.setBounds(410, 550, 30,30);

        page = 1;

        this.player = player;

        startPage();
        createButtons();
        createButtonListeners();

        shutterAnimation = new ShutterAnimation(stage, assets, false);
        shutterAnimation.start();
    }

    private void turnPage()  {

        pageLeft.remove();
        pageRight.remove();

        mapFrame.remove();

        mapFrame.changePage(page);

        switch (page)   {
            case 1:
                stage.addActor(mapFrame);

                if(player.getLevel() > 6) {
                    stage.addActor(pageRight);
                }
                break;
            case 2:
                stage.addActor(mapFrame);

                if(player.getLevel() > 11) {
                    stage.addActor(pageRight);
                }
                stage.addActor(pageLeft);
                break;
            case 3:
                stage.addActor(mapFrame);
                stage.addActor(pageLeft);
                break;
        }
        loadPage();
        createBossIconListeners();
        createButtons();

    }

    private void startPage()  {

        if(player.getLevel() <= 6)
            mapFrame = new MapFrame(player, 1, assets);
        else if(player.getLevel() <= 11 && player.getLevel() > 6)
            mapFrame = new MapFrame(player, 2, assets);
        else
            mapFrame = new MapFrame(player, 3, assets);


        pageLeft.remove();
        pageRight.remove();

        page = mapFrame.getPage();
        mapFrame.remove();

        switch (mapFrame.getPage())   {
            case 1:
                stage.addActor(mapFrame);

                if(player.getLevel() > 6) {
                    stage.addActor(pageRight);
                }
                break;
            case 2:
                stage.addActor(mapFrame);

                if(player.getLevel() > 11) {
                    stage.addActor(pageRight);
                }
                stage.addActor(pageLeft);
                break;
            case 3:
                stage.addActor(mapFrame);
                stage.addActor(pageLeft);
                break;
        }
        loadPage();
        createBossIconListeners();

        System.out.println("Player Level:"+player.getLevel());
        InfoFrame infoFrame;
        switch (previousState) {
            case 1:
                if(player.getLevel() == 2) {
                    infoFrame = new InfoFrame("", Strings.STAGE_1_INTRO, assets);
                    stage.addActor(infoFrame);
                }
                break;
            case 2:
                if(player.getLevel() == 7) {
                    infoFrame = new InfoFrame("", Strings.STAGE_2_INTRO, assets);
                    stage.addActor(infoFrame);
                }
                break;
            case 3:
                if(player.getLevel() == 12) {
                    infoFrame = new InfoFrame("", Strings.STAGE_3_INTRO, assets);
                    stage.addActor(infoFrame);
                }
                break;
            case 4:
                if(player.getLevel() == 17) {
                    infoFrame = new InfoFrame("", Strings.STAGE_END, assets);
                    stage.addActor(infoFrame);
                }
                break;
        }
    }

    private void loadPage()  {
        for(int i = 2 + (page-1)*5; i <= page*5+1; i++)   {
            if(player.getLevel() < i)
                return;
            mapFrame.setBoss(i);
        }
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
                if(Input.Keys.BACK == keycode || Input.Keys.BACKSPACE == keycode)    {
                    sm.set(new MainMenuState(sm, player));
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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

    private void createButtons() {
        if(buttonTable != null) {
            buttonTable.remove();
        }
        else {
            buttonTable = new Table();
            buttonTable.setBounds(mapFrame.getInnerFrame().getX(), 20, mapFrame.getInnerFrame().getWidth(), mapFrame.getInnerFrame().getY()-15);

            talentButton = new TextButton("TALENT", assets.getSkin());

            startButton = new TextButton("START", assets.getSkin());

            spellButton = new TextButton("SPELL", assets.getSkin());

            buttonTable.add(talentButton).padRight(5).padTop(5);
            buttonTable.add(startButton).padRight(5).padLeft(5).padTop(5);
            buttonTable.add(spellButton).padLeft(5).padTop(5);
        }

        if(player.getTalentTree().getUnusedPoints() > 0)    {
            talentButton.setChecked(true);
        } else {
            talentButton.setChecked(false);
        }



        stage.addActor(buttonTable);
    }

    private void createButtonListeners()   {

        talentButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        talentButton.toggle();
                        sm.set(new TalentSelectionState(sm, player));
                    }
                });
                shutterAnimation.start();

            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(selectedLevel != null) {
                    shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("++++Boss: " + selectedLevel.getBoss().getId());

                            if (selectedLevel.getBoss().getId() == 16)
                                sm.set(new LastBossEncounterState(sm, player, selectedLevel.boss));
                            else
                                sm.set(new EncounterState(sm, player, selectedLevel.boss));

                        }
                    });
                    shutterAnimation.start();
                }

            }
        });

        spellButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new SpellSelectionState(sm, player));
                    }
                });
                shutterAnimation.start();

            }
        });

        pageLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page--;
                turnPage();
                System.out.println("Page Left");
            }
        });

        pageRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page++;
                turnPage();
                System.out.println("Page Right");
            }
        });
    }

    private void createBossIconListeners()   {
        for(int i = 0; i < mapFrame.getBossIconsList().getButtons().size; i++)   {
            mapFrame.getBossIconsList().getButtons().get(i).addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(selectedLevel != null)    {
                        selectedLevel.setDisabled(false);
                    }
                    selectedLevel = (BossIcon) actor;
                    selectedLevel.setDisabled(true);

                    mapFrame.setTitle(selectedLevel.getName());
                    mapFrame.setBody(selectedLevel.getDescription());
                    System.out.println("map body size: "+mapFrame.getBody().getFontSize());
                    //mapFrame.getInfoFrame().addInfo(selectedLevel.boss);
                    mapFrame.addBossSelection(selectedLevel.boss);
                    mapFrame.disableInfoButton();
                    mapFrame.showInfoButton();
                }
            });
        }
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(Color.BLACK.r,Color.BLACK.g,Color.BLACK.b,Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.gl.glEnable(GL20.GL_BLEND);
        update(Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        System.out.println("Map Disposed!");
    }
}
