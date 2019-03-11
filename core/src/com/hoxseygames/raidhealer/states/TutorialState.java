package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.BlinkingOutline;
import com.hoxseygames.raidhealer.GameData;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.TutorialFrame;
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
                raid.getRaidMember(0).getHealthBar().getX() + raid.getRaidMember(0).getHealthBar().getWidth() - 20 * (0) - 20,
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
        outlines.add(new Rectangle(player.getCastBar().getX(), player.getCastBar().getY(), player.getCastBar().getWidth(), player.getCastBar().getHeight()));
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
    protected void handleInput() {

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode)    {
                    case Input.Keys.NUM_0:
                        boss.takeDamage(1000);
                        break;
                    case Input.Keys.NUM_9:
                        for(int i = 0; i < raid.getRaidMembers().size(); i++)   {
                            raid.getRaidMembers().get(i).takeDamage(50);
                        }
                        break;
                    case Input.Keys.NUM_1:
                        if(player.getSpellBar().getSpells().size() > 0)    {
                            if(!player.isCasting())
                                player.getSpellBar().getSpells().get(0).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_2:
                        if(player.getSpellBar().getSpells().size() > 1)    {
                            if(!player.isCasting())
                                player.getSpellBar().getSpells().get(1).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_3:
                        if(player.getSpellBar().getSpells().size() > 2)    {
                            if(!player.isCasting())
                                player.getSpellBar().getSpells().get(2).castSpell();
                        }
                        break;
                    case Input.Keys.NUM_4:
                        if(player.getSpellBar().getSpells().size() > 3)    {
                            if(!player.isCasting())
                                player.getSpellBar().getSpells().get(3).castSpell();
                        }
                        break;
                    case Input.Keys.L:
                        System.out.println("********** Raid Stats **********");
                        System.out.println("id|role|maxhp|hp|damage");
                        for(int i = 0; i < boss.getEnemies().getRaidMembers().size(); i++)   {
                            System.out.println(boss.getEnemies().getRaidMember(i).getId() + "|"+
                                    boss.getEnemies().getRaidMember(i).getRole() + "|"+
                                    boss.getEnemies().getRaidMember(i).getMaxHp() + "|"+
                                    boss.getEnemies().getRaidMember(i).getHp() + "|"+
                                    boss.getEnemies().getRaidMember(i).getDamage());
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
                            System.out.println("found a target");
                            player.setTarget(raidTarget);
                            player.getTarget().selected();
                        }
                    } else {
                        if (!player.isCasting()) {
                            Spell spell = (Spell) player.getSpellBar().hit(coord.x, coord.y, false);
                            if (spell != null) {
                                spell.castSpell();
                            }
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
        });

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

