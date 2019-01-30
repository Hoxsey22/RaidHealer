package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.ShutterAnimation;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 12/21/2018
 */
public class LastBossEncounterState extends EncounterState {
    private int progression;

    //commit
    public LastBossEncounterState(StateManager sm, Player player, Boss boss) {
        super(sm, player, boss);
    }

    @Override
    public void create() {
        super.create();

        AudioManager.playMusic(assets.getMusic(assets.lastBossBattle1Music));

        progression = 1;
    }

    @Override
    public void update(float dt) {
        if(!isDone) {
            handleInput();
            boss.update();
            updateMusic();
            if (boss.isDead())
                victory();
            else if (raid.isRaidDead())
                defeat();
        }

    }



    /**
     * This method creates the ok button listener and starts. This is needed to be called if
     * the okButton wants to be used.
     */
    @Override
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
                                sm.set(new CreditsState(sm,player,1));
                            }
                        });
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
    @Override
    protected void stateLeaveButtonListener()   {
        gameOverFrame.getLeaveButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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

    /**
     * This method creates the resetButton listener and starts. This is needed to be called if
     * the leaveButton wants to be used. This also tells the StateManager to go to the EncounterState.
     */
    @Override
    protected void stateResetButtonListener()   {
        gameOverFrame.getResetButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shutterAnimation = new ShutterAnimation(stage, assets, true, new Runnable() {
                    @Override
                    public void run() {
                        sm.set(new LastBossEncounterState(sm, player, boss));
                    }
                });
                shutterAnimation.start();
            }
        });
    }

    private void updateMusic()  {
        if(boss.getPhaseManager().getIndex() + 1 == 2 && progression != 2)    {
            AudioManager.playMusic(assets.getMusic(assets.lastBossBattle2Music));
            progression = 2;
        }
        else if(boss.getPhaseManager().getIndex() + 1 == 3 && progression != 3)    {
            AudioManager.playMusic(assets.getMusic(assets.lastBossBattle3Music));
            progression = 3;
        }
    }




}

