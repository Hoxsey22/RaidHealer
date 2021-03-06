package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.BlinkingOutline;
import com.hoxseygames.raidhealer.GameData;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.TutorialFrame;
import com.hoxseygames.raidhealer.WindowFrame;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage1.Monster;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Spell;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.TestEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 5/28/2017.
 */
public class TutorialState extends EncounterState {
    private final TutorialFrame tutorialFrame;
    private BlinkingOutline blinkingOutline;
    private ArrayList<Rectangle> outlines;
    private boolean isTutorialOver;


    public TutorialState(StateManager sm, Player player) {
        super(sm, player, new Monster(player.getAssets()));

        tutorialFrame = new TutorialFrame(player, boss, player.getAssets());

        raid.getRaidMember(0).addStatusEffect(new TestEffect(boss));

        boss.getAnnouncement().setText("Monster is about to Bite!");

        player.setCasting(true);
        player.setTarget(raid.getRaidMember(0));
        shutterAnimation = new ShutterAnimation(stage, player.getAssets(), false, new Runnable() {
            @Override
            public void run() {
                isTutorialOver = true;
                stage.addActor(tutorialFrame);
                stage.addActor(blinkingOutline);
            }
        });
        shutterAnimation.start();


    }

    @Override
    public void create() {
        super.create();

        blinkingOutline = new BlinkingOutline();
        blinkingOutline.start();

        outlines = new ArrayList<>();
        // intro
        outlines.add(new Rectangle());
        // boss frame
        outlines.add(new Rectangle(boss.getX(), boss.getY(), boss.getWidth(), boss.getHeight()));
        // announcement
        outlines.add(new Rectangle(
                boss.getX(),
                raid.getRaidMember(0).getY()+raid.getRaidMember(0).getHeight()+2,
                boss.getWidth(),
                15));
        // raid frames
        outlines.add(new Rectangle(raid.getRaidMember(0).getX(), raid.getRaidMember(0).getY(), -raid.getRaidMember(0).getX()+(raid.getRaidMember(2).getX()+raid.getRaidMember(2).getWidth()), raid.getRaidMember(0).getHeight()));
        // Tank frame
        outlines.add(new Rectangle(raid.getRaidMember(0).getX(), raid.getRaidMember(0).getY(), raid.getRaidMember(0).getWidth(), raid.getRaidMember(0).getHeight()));
        // dps frame
        outlines.add(new Rectangle(raid.getRaidMember(1).getX(), raid.getRaidMember(1).getY(), raid.getRaidMember(1).getWidth(), raid.getRaidMember(1).getHeight()));
        // healer frame
        outlines.add(new Rectangle(raid.getRaidMember(2).getX(), raid.getRaidMember(2).getY(), raid.getRaidMember(2).getWidth(), raid.getRaidMember(2).getHeight()));
        // debuff and buff
        outlines.add(new Rectangle(
                raid.getRaidMember(0).getHealthBar().getX() + raid.getRaidMember(0).getHealthBar().getWidth()- 20,
                raid.getRaidMember(0).getHealthBar().getY() + raid.getRaidMember(0).getHealthBar().getHeight() + 5,
                20,
                20));
        // targeted unit
        outlines.add(new Rectangle(raid.getRaidMember(0).getX(), raid.getRaidMember(0).getY(), raid.getRaidMember(0).getWidth(), raid.getRaidMember(0).getHeight()));
        // targeting a unit
        outlines.add(new Rectangle(raid.getRaidMember(0).getX(), raid.getRaidMember(0).getY(), raid.getRaidMember(0).getWidth(), raid.getRaidMember(0).getHeight()));
        // spell bar
        outlines.add(new Rectangle(player.getSpellBar().getX(), player.getSpellBar().getY(), player.getSpellBar().getWidth(), player.getSpellBar().getHeight()));
        // mana bar
        outlines.add(new Rectangle(player.getManaBar().getX(), player.getManaBar().getY(), player.getManaBar().getWidth(), player.getManaBar().getHeight()));
        // cast bar
        outlines.add(new Rectangle(castBar.getX(), castBar.getY(), castBar.getWidth(),castBar.getHeight()));
        // conclusion
        outlines.add(new Rectangle());
        // last line
        outlines.add(new Rectangle());


        blinkingOutline.setOutline(outlines.get(0));

        boss.stop();
        encounterCountDown.stop();
        isReady = true;
    }

    @Override
    protected void initExitConfirmationWindow() {
        ngConfirmationWindow = new WindowFrame(RaidHealer.ui);

        ngConfirmationText = new Label("Are you sure you want to quit the tutorial?", RaidHealer.ui);
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
                        player.setLevel(2);
                        sm.set(new MapState(sm, player, 1));
                    }
                });
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

    @Override
    protected void handleInput() {

        Gdx.input.setInputProcessor(new InputMultiplexer(stage,new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode)    {
                    /*case Input.Keys.NUM_0:
                        boss.takeDamage(1000);
                        break;
                    case Input.Keys.NUM_9:
                        for(int i = 0; i < raid.getRaidMembers().size(); i++)   {
                            raid.getRaidMembers().get(i).takeDamage(50);
                        }
                        break;*/
                    case Input.Keys.NUM_1:
                        if(player.getSpellBar().getSpells().size() > 0)    {
                            player.getSpellBar().getSpells().get(0).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_2:
                        if(player.getSpellBar().getSpells().size() > 1)    {
                            player.getSpellBar().getSpells().get(1).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_3:
                        if(player.getSpellBar().getSpells().size() > 2)    {
                            player.getSpellBar().getSpells().get(2).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_4:
                        if(player.getSpellBar().getSpells().size() > 3)    {
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
                Vector2 coord = stage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
                if (tutorialFrame.isComplete()) {
                    if (coord.y > 300) {
                        RaidMember raidTarget = (RaidMember) raid.hit(coord.x, coord.y, false);
                        if (raidTarget != null) {
                            player.setTarget(raidTarget);
                            player.getTarget().selected();
                        }
                    } else {
                            Spell spell = (Spell) player.getSpellBar().hit(coord.x, coord.y, false);
                            if (spell != null) {
                                spell.castSpell();
                            }
                        }
                }
                else{
                    if(isTutorialOver) {
                        tutorialFrame.nextStage();
                        if (!tutorialFrame.isComplete())
                            blinkingOutline.setOutline(outlines.get(tutorialFrame.stageNumber - 1));
                        else {
                            blinkingOutline.stop();
                            blinkingOutline.remove();
                            tutorialFrame.remove();
                            player.setCasting(false);
                            boss.getAnnouncement().setText("");
                            raid.getRaidMember(0).getStatusEffectList().dispel();
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
        }));

    }

    @Override
    protected void stateLeaveButtonListener()   {
        gameOverFrame.getLeaveButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        GameData.clear();
                        sm.set(new MainMenuState(sm, player));
                    }
                });
                shutterAnimation.start();
            }
        });
    }

    @Override
    protected void stateResetButtonListener()   {
        gameOverFrame.getResetButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new TutorialState(sm, player));
                    }
                });
                shutterAnimation.start();
            }
        });
    }

}

