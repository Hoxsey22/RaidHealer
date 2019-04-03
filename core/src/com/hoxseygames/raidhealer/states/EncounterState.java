package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.EncounterCountDown;
import com.hoxseygames.raidhealer.GameOverFrame;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.WindowFrame;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.player.bars.CastBar;
import com.hoxseygames.raidhealer.encounters.player.bars.ManaBar;
import com.hoxseygames.raidhealer.encounters.spells.Spell;

/**
 * Created by Hoxsey on 5/28/2017.
 */
public class EncounterState extends State {
    protected final Player player;
    protected ManaBar manaBar;
    protected CastBar castBar;
    protected Stage stage;
    protected final Raid raid;
    protected final Boss boss;
    protected Image bgImage;
    protected Assets assets;
    protected GameOverFrame gameOverFrame;
    protected boolean isDone;
    protected boolean isReady;
    protected int page;
    protected ShutterAnimation shutterAnimation;
    protected EncounterCountDown encounterCountDown;
    protected ImageButton exitButton;
    protected WindowFrame ngConfirmationWindow;
    protected Label ngConfirmationText;
    protected TextButton confirmButton;
    protected TextButton backButton;


    public EncounterState(StateManager sm, Player player, final Boss boss) {
        super(sm);
        this.player = player;
        this.player.loadTalents();
        this.player.reset();

        this.boss = boss;
        this.boss.reset();

        boss.setPlayer(player);

        raid = this.boss.getEnemies();
        raid.setPlayer(player);

        player.setBoss(this.boss);

        create();
    }

    public void createCountDown()   {
        encounterCountDown = new EncounterCountDown(player.getAssets(), new Runnable() {
            @Override
            public void run() {
                isReady = true;
                boss.start();
            }
        });

        stage.addActor(encounterCountDown);

        encounterCountDown.start();

    }




    @Override
    public void create() {
        stage = new Stage(viewport);

        isDone = false;

        assets = player.getAssets();

        manaBar = new ManaBar(player, assets);

        castBar = new CastBar(player, assets);
        castBar.setPosition(20, manaBar.getY()+manaBar.getHeight()+10);

        player.setRaid(boss.getEnemies());

        if(boss.getId() < 7)    {
            bgImage = new Image(assets.getTexture(assets.battleBg1));
            AudioManager.playMusic(assets.getMusic(assets.stage1BattleMusic));
        }
        else if(boss.getId() < 12 )   {
            bgImage = new Image(assets.getTexture(assets.battleBg2));
            AudioManager.playMusic(assets.getMusic(assets.stage3BattleMusic));
        }
        else if(boss.getId() < 17)   {
            bgImage = new Image(assets.getTexture(assets.battleBg3));
            AudioManager.playMusic(assets.getMusic(assets.stage2BattleMusic));
        }

        bgImage.setX(bgImage.getWidth()/2-RaidHealer.WIDTH/2);
        bgImage.setBounds(0,0, RaidHealer.WIDTH, RaidHealer.HEIGHT);

        // add all actors to the stageNumber
        stage.addActor(bgImage);
        stage.addActor(boss);
        stage.addActor(raid);
        stage.addActor(player.getSpellBar());
        stage.addActor(manaBar);
        stage.addActor(castBar);
        // Load Ad
        sm.loadAd(3);

        isReady = false;

        initExitButton();

        exitButton = new ImageButton(assets.getSkin(), "exit_button");
        exitButton.setPosition(boss.getX() + boss.getWidth() + 5, boss.getY());
        exitButton.setTouchable(Touchable.enabled);
        exitButton.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                ngConfirmationWindow.show(stage);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addActor(exitButton);

        shutterAnimation = new ShutterAnimation(stage, player.getAssets(), false);

        shutterAnimation.start();

        createCountDown();
        initExitConfirmationWindow();
        raid.setupListener();
        setupSpellListener();

    }

    protected void initExitConfirmationWindow() {
        ngConfirmationWindow = new WindowFrame(RaidHealer.ui);

        ngConfirmationText = new Label("Are you sure you want to quit?", RaidHealer.ui);
        ngConfirmationText.setWrap(true);
        ngConfirmationText.setAlignment(Align.center);

        confirmButton = new TextButton("Quit",RaidHealer.ui, "small_button");
        confirmButton.setTouchable(Touchable.enabled);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stop();
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new MapState(sm, player, 1));
                    }
                });
                sm.showAd(1);
                shutterAnimation.start();
            }
        });

        backButton = new TextButton("Back",RaidHealer.ui, "small_button");
        backButton.setTouchable(Touchable.enabled);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ngConfirmationWindow.hide();
            }
        });

        ngConfirmationWindow.center();
        ngConfirmationWindow.add(ngConfirmationText).width(ngConfirmationWindow.getWidth()).center().colspan(2).pad(10).row();
        ngConfirmationWindow.add(confirmButton).center();
        ngConfirmationWindow.add(backButton).center();
    }

    protected void initExitButton() {

    }

    @Override
    protected void handleInput() {

        Gdx.input.setInputProcessor(new InputMultiplexer(stage,new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    /*case Input.Keys.NUM_0:
                        boss.takeDamage(1000);
                        break;
                    case Input.Keys.NUM_9:
                        for (int i = 0; i < raid.getRaidMembers().size(); i++) {
                            raid.getRaidMembers().get(i).takeDamage(50);
                        }
                        break;*/
                    case Input.Keys.NUM_1:
                        if (player.getSpellBar().getSpells().size() > 0) {
                            player.getSpellBar().getSpells().get(0).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_2:
                        if (player.getSpellBar().getSpells().size() > 1) {
                            player.getSpellBar().getSpells().get(1).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_3:
                        if (player.getSpellBar().getSpells().size() > 2) {
                            player.getSpellBar().getSpells().get(2).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_4:
                        if (player.getSpellBar().getSpells().size() > 3) {
                            player.getSpellBar().getSpells().get(3).castSpell();
                        }
                        break;
                    case Input.Keys.BACK:
                        break;
                    case Input.Keys.BACKSPACE:
                        break;
                }
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

    @Override
    public void update(float dt) {
        if(isReady) {
            if (!isDone) {
                handleInput();
                boss.update();
                if (boss.isDead())
                    victory();
                else if (raid.isRaidDead())
                    defeat();
            }
        }
    }

    /**
     * This is a end game sub-state if the player wins.
     */
    protected void victory()  {
        AudioManager.playMusic(assets.getMusic(assets.victoryMusic));

        page = 2;

        if(!boss.isDefeated())    {
            boss.reward();
            boss.setDefeated(true);
            player.newLevel(boss.getId()+1);
            player.save();
            page = 1;
        }
        raid.loadHealingStats();

        gameOverFrame = new GameOverFrame(true, boss, assets);
        gameOverFrame.displayHealingStats();
        startOkButtonListener();
        gameOverFrame.show(stage);

        stop();

        isDone = true;
    }

    /**
     * This is a end game sub-state if the player loses.
     */
    protected void defeat() {
        AudioManager.playMusic(assets.getMusic(assets.defeatMusic));

        gameOverFrame = new GameOverFrame(false, boss, assets);
        gameOverFrame.displayDefeat();
        stateResetButtonListener();
        stateLeaveButtonListener();
        gameOverFrame.show(stage);

        stop();

        isDone = true;
    }

    /**
     * This method creates the ok button listener and starts. This is needed to be called if
     * the okButton wants to be used.
     */
    protected void startOkButtonListener()   {
        gameOverFrame.getOkButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (page)   {
                    case 1:
                        gameOverFrame.displayReward();
                        page = 2;
                        break;
                    case 2:
                        boss.getPlayer().newLevel(boss.getLevel());
                        shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                            @Override
                            public void run() {
                                switch (boss.getId()) {
                                    case 1:
                                        sm.set(new MapState(sm, player, 1));
                                        break;
                                    case 6:
                                        sm.set(new MapState(sm, player, 2));
                                        break;
                                    case 11:
                                        sm.set(new MapState(sm, player, 3));
                                        break;
                                    case 16:
                                        sm.set(new MapState(sm, player, 4));
                                        break;
                                    default:
                                        sm.set(new MapState(sm, player));
                                        break;
                                }
                            }
                        });
                        sm.showAd(1);
                        shutterAnimation.start();
                        break;
                }

                }
            });
        }
    /**
     * This method creates the leaveButton listener and starts. This is needed to be called if
     * the leaveButton wants to be used. This also tells the StateManager to go to the MapState.
     */
    protected void stateLeaveButtonListener()   {
        gameOverFrame.getLeaveButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new MapState(sm, player));
                        sm.showAd(1);
                    }
                });
                shutterAnimation.start();
            }
        });
    }

    /**
     * This method creates the resetButton listener and starts. This is needed to be called if
     * the leaveButton wants to be used. This also tells the StateManager to go to the EncounterState.
     */
    protected void stateResetButtonListener()   {
        gameOverFrame.getResetButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new EncounterState(sm, player, boss));
                    }
                });
                shutterAnimation.start();
            }
        });
    }

    protected void setupSpellListener()   {

        for(int i = 0; i < player.getSpellBar().getSpells().size(); i++)   {
            player.getSpellBar().getSpells().get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Spell spell = (Spell) actor;
                    spell.castSpell();
                }
            });
        }
    }


    protected void setupRaidListener()   {
        for(int i = 0; i < player.getRaid().getRaidMembers().size(); i++)   {
            player.getRaid().getRaidMember(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    player.setTarget((RaidMember)actor);
                    player.getTarget().selected();
                }
            });
        }
    }

    /**
     * Stops all timers in boss raid and player.
     */
    protected void stop()  {
        boss.stop();
        raid.stop();
        raid.getHealingTracker().printHealingDone();
        player.stop();
    }

    /**
     * Starts the boss and player can now use actions.
     */
    protected void start()  {
        boss.start();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(Color.BLACK.r,Color.BLACK.g,Color.BLACK.b,Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        sb.setProjectionMatrix(cam.combined);

        viewport.apply();

        cam.update();

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

