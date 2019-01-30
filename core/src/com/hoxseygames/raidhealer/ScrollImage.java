package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Hoxsey on 8/1/2017.
 */

public class ScrollImage {

    private Assets assets;
    private Image image1;
    private Image image2;
    private float velocity;
    private float originX;
    private float originY;
    private float endX;
    private float endY;
    private boolean isVertical;

    public ScrollImage(Texture texture, boolean isVertical, Vector2 frame, float velocity, Assets assets)    {
        this.setAssets(assets);

        this.setVertical(isVertical);

        setImage1(new Image(texture));
        getImage1().setBounds(frame.x,frame.y,texture.getWidth(),texture.getHeight());
        setImage2(new Image(texture));
        getImage2().setBounds(frame.x,frame.y,texture.getWidth(),texture.getHeight());

        setPosition(frame.x,frame.y);
        matchPartner();

        this.setVelocity(velocity);

        setOriginX(frame.x);
        setOriginY(frame.y);

        setEndX(frame.x);
        setEndY(frame.y);
    }

    private void setPosition(float x, float y)   {
        getImage1().setPosition(x,y);
        matchPartner();
    }

    private void matchPartner() {
        if(isVertical())
            getImage2().setPosition(getImage1().getX(), getImage1().getY()+ getImage1().getHeight());
        else {
            if(velocity > 0) {
                getImage2().setPosition(getImage1().getX() + getImage1().getWidth(), getImage1().getY());
            }
            else  {
                getImage2().setPosition(getImage1().getX() - getImage1().getWidth(), getImage1().getY());
            }
        }
    }

    public void move()    {
        if(isVertical())    {
            outOfRange();
            getImage1().setY(getImage1().getY() - getVelocity());
            matchPartner();
        }
        else    {
            outOfRange();
            getImage1().setX(getImage1().getX() - getVelocity());
            matchPartner();

        }
    }

    private void outOfRange()   {
        if(isVertical())    {
            if(getImage2().getY()- getVelocity() < getEndY())   {
                reposition();
            }
        }
        else    {
            if(velocity > 0) {
                if (getImage2().getX() - getVelocity() <= getEndX()) {
                    reposition();
                }
            }
            else {
                if (getImage2().getX() - getVelocity() >= getEndX()) {
                    reposition();
                }
            }
        }

    }

    private void reposition()   {
        setPosition(getOriginX(), getOriginY());
    }

    public void draw(Batch batch, float parent)  {
        getImage1().draw(batch, parent);
        getImage2().draw(batch, parent);
    }

    public Assets getAssets() {
        return assets;
    }

    private void setAssets(Assets assets) {
        this.assets = assets;
    }

    private Image getImage1() {
        return image1;
    }

    private void setImage1(Image image1) {
        this.image1 = image1;
    }

    private Image getImage2() {
        return image2;
    }

    private void setImage2(Image image2) {
        this.image2 = image2;
    }

    private float getVelocity() {
        return velocity;
    }

    private void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    private float getOriginX() {
        return originX;
    }

    private void setOriginX(float originX) {
        this.originX = originX;
    }

    private float getOriginY() {
        return originY;
    }

    private void setOriginY(float originY) {
        this.originY = originY;
    }

    private float getEndX() {
        return endX;
    }

    private void setEndX(float endX) {
        this.endX = endX;
    }

    private float getEndY() {
        return endY;
    }

    private void setEndY(float endY) {
        this.endY = endY;
    }

    private boolean isVertical() {
        return isVertical;
    }

    private void setVertical(boolean vertical) {
        isVertical = vertical;
    }
}
