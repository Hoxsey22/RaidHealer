package com.hoxseygames.raidhealer.encounters.player.bars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;

/**
 * Created by Hoxsey on 6/20/2017.
 */
@SuppressWarnings("unused")
public class CastBar extends Actor {

    private Player owner;
    private Assets assets;

    public CastBar(Player player, Assets assets)    {
        setBounds(20,235,442,40);
        owner = player;
        this.assets = assets;
    }

    public void anchor(Actor actor) {
        setPosition(RaidHealer.WIDTH/2 - this.getWidth()/2, actor.getY()+ actor.getHeight() + 10);
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

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isActive()) {
            batch.draw(assets.getTexture("black_bar.png"), getX(), getY(), getWidth(), getHeight());
            batch.draw(assets.getTexture("white_bar.png"), getX() + 4, getY() + 4, getWidth() - 8, getHeight() - 8);
            batch.draw(assets.getTexture("casting_bar.png"), getX() + 4, getY() + 4, (getWidth() - 8) * getPercentage(), getHeight() - 8);
        }
    }
}
