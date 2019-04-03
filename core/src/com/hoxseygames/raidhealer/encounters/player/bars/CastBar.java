package com.hoxseygames.raidhealer.encounters.player.bars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 6/20/2017.
 */
@SuppressWarnings("unused")
public class CastBar extends Group {


    private class Bar extends Group {

        Image blackBar;
        Image whiteBar;
        Image progressionBar;

        public Bar(float x, float y, float width, float height)    {
            blackBar = new Image(assets.getTexture(assets.blackBar));
            whiteBar = new Image(assets.getTexture(assets.whiteBar));
            progressionBar = new Image(assets.getTexture(assets.castBar));

            setBounds(x,y,width,height);

            blackBar.setBounds(getX(), getY(), getWidth(), getHeight());
            whiteBar.setBounds(getX() + 4, getY() + 4, getWidth() - 8, getHeight() - 8);
            progressionBar.setBounds(getX() + 4, getY() + 4, (getWidth() - 8) * getPercentage(), getHeight() - 8);

            addActor(blackBar);
            addActor(whiteBar);
            addActor(progressionBar);
        }

        protected void update() {
            progressionBar.setWidth((blackBar.getWidth() - 8) * getPercentage());
        }
    }

    private Player owner;
    private Assets assets;
    private Bar bar;
    private ImageButton exitButton;

    public CastBar(Player player, Assets assets)    {
        owner = player;
        this.assets = assets;

        bar = new Bar(getX(),getY(),390,40);

        exitButton = new ImageButton(assets.getSkin(), "exit_button");
        exitButton.setPosition(bar.getX()+bar.getWidth()-2, bar.getY()+bar.getHeight()/2-exitButton.getHeight()/2);

        addActor(bar);
        addActor(exitButton);

        setupExitButtonListener();
    }

    private void setupExitButtonListener()  {
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                owner.stopCast();
            }
        });
    }

    private boolean isActive() {
        return owner.isCasting();
    }

    private float getPercentage()    {
        return owner.getSpellCastPercent();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Assets getAssets() {
        return assets;
    }

    private void setAssets(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isActive()) {
            bar.update();
            super.draw(batch,parentAlpha);
        }
    }

}
