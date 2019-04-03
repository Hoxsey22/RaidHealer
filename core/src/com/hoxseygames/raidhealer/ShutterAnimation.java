package com.hoxseygames.raidhealer;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Hoxsey on 1/6/2019.
 */



public class ShutterAnimation {

    private Stage parent;
    private Assets assets;
    private Image blackBar1;
    private Image blackBar2;
    private Runnable runnable;
    private Runnable endRunnable;
    private boolean closing;

    public ShutterAnimation(Stage parent, Assets assets, boolean closing)   {
        this.parent = parent;
        this.assets = assets;
        this.closing = closing;
        endRunnable = new Runnable() {
            @Override
            public void run() {unloadBlackBars();}
        };
    }

    public ShutterAnimation(Stage parent, Assets assets, boolean closing, Runnable runnable)   {
        this.parent = parent;
        this.assets = assets;
        this.closing = closing;
        addRunnable(runnable);
        endRunnable = new Runnable() {
            @Override
            public void run() {unloadBlackBars();}
        };
    }

    private void setClosingPosition()  {
        blackBar1 = new Image(assets.getTexture(assets.blackBar));
        blackBar1.setBounds(-parent.getWidth()/2,0, parent.getWidth()/2,parent.getHeight());

        blackBar2 = new Image(assets.getTexture(assets.blackBar));
        blackBar2.setBounds(parent.getWidth(),0, parent.getWidth()/2,parent.getHeight());
    }

    private void setOpeningPosition()  {
        blackBar1 = new Image(assets.getTexture(assets.blackBar));
        blackBar1.setBounds(0,0, parent.getWidth()/2,parent.getHeight());

        blackBar2 = new Image(assets.getTexture(assets.blackBar));
        blackBar2.setBounds(parent.getWidth()/2,0, parent.getWidth()/2,parent.getHeight());
    }

    public void addRunnable(Runnable runnable)   {
        this.runnable = runnable;
    }

    public void start() {
        if(closing) {
            setClosingPosition();
            loadBlackBars();
            if (runnable != null) {
                blackBar1.addAction(Actions.sequence(Actions.moveTo(0, 0, 1), Actions.delay(0.5f), Actions.run(runnable), Actions.run(endRunnable)));
            } else {
                blackBar1.addAction(Actions.sequence(Actions.moveTo(0, 0, 1), Actions.run(endRunnable)));
            }
            blackBar2.addAction(Actions.moveTo(parent.getWidth() / 2, 0, 1));
        }
        else    {
            setOpeningPosition();
            loadBlackBars();
            if(runnable != null) {
                blackBar1.addAction(Actions.sequence(Actions.moveTo(-parent.getWidth()/2, 0, 1),Actions.delay(0.5f), Actions.run(runnable), Actions.run(endRunnable)));
            }
            else    {
                blackBar1.addAction(Actions.sequence(Actions.moveTo(-parent.getWidth()/2, 0, 1), Actions.run(endRunnable)));
            }
            blackBar2.addAction(Actions.moveTo(parent.getWidth(),0,1));
        }
    }

    private void loadBlackBars()    {
        parent.addActor(blackBar1);
        parent.addActor(blackBar2);
    }

    private void unloadBlackBars()  {
        blackBar1.remove();
        blackBar2.remove();
    }



}
