package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * Created by Hoxsey on 7/20/2017.
 */

@SuppressWarnings("CanBeFinal")
public class ImageButton extends Actor {

    public Texture texture;
    public boolean isHit;
    private boolean flipX;
    public boolean isHidden;

    @Override
    public boolean remove() {
        isHidden = true;
        return super.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight(), 0, 0, texture.getWidth(), texture.getWidth(), flipX, false);
    }
}
