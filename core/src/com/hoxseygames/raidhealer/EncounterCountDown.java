package com.hoxseygames.raidhealer;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Hoxsey on 3/9/2019.
 */

public class EncounterCountDown extends Group {

    private Assets assets;
    private Image disableBG;
    private Table infoTable;
    private int index;
    private Text displayCount;
    private boolean isFinished = false;
    private Runnable finishedRunnable;

    public EncounterCountDown(Assets assets, Runnable finishedRunnable)  {
        this.assets = assets;

        this.finishedRunnable = finishedRunnable;

        create();

        infoTable = new Table();
        infoTable.setBounds(RaidHealer.WIDTH/2-infoTable.getWidth()/2,RaidHealer.HEIGHT/2-infoTable.getHeight()/2,50, 50);
        infoTable.center();
        infoTable.add(displayCount.getLabel()).center();

        addActor(infoTable);
    }

    private void create()   {
        // create disable bg
        disableBG = new Image(assets.getTexture(assets.disableBG));
        addActor(disableBG);

        //set index
        index = 5;

        // setup displayCount
        displayCount = new Text(""+index, Color.RED, assets);

    }


    public void start()    {

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                index--;
                if(index == -1)  {
                    isFinished = true;
                    finished();
                }   else {
                    AudioManager.playSFX(assets.getSound(assets.cdSFX), false);
                    displayCount.setText("" + index);
                }

            }
        },1f,1f, 4);
    }

    private void finished() {
        finishedRunnable.run();
        remove();
    }
}
