package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


/**
 * Created by Hoxsey on 7/30/2017.
 */

@SuppressWarnings({"CanBeFinal", "unused"})
public class Button extends Actor {

    public Image image;
    public Image highlightImage;
    public Text text;
    public Assets assets;
    public boolean isHighlight;
    public boolean isHit;
    public Table parent;

    public Button(String name, boolean isSmall, Assets assets) {
        setName(name);

        this.assets = assets;

        if(!isSmall)
            image = new Image(assets.getTexture(assets.button));
        else
            image = new Image(assets.getTexture(assets.smallButton));

        highlightImage = new Image(assets.getTexture(assets.buttonHighlight));

        setWidth(image.getWidth());
        setHeight(image.getHeight());

        //setBounds(0,0,image.getWidth(), image.getHeight());

        isHit = false;
        isHighlight = false;

        setupText(isSmall);
    }

    public Button(String name, boolean isSmall, Table parent, Assets assets) {
        setName(name);
        setParent(parent);

        this.assets = assets;

        if(!isSmall)
            image = new Image(assets.getTexture(assets.button));
        else
            image = new Image(assets.getTexture(assets.smallButton));

        highlightImage = new Image(assets.getTexture(assets.buttonHighlight));

        setWidth(image.getWidth());
        setHeight(image.getHeight());

        //setBounds(0,0,image.getWidth(), image.getHeight());

        isHit = false;
        isHighlight = false;

        setupText(isSmall);
    }

    public Button(String name, Image image, int x, int y, int width, int height, Assets assets) {
        setName(name);

        this.assets = assets;
        setBounds(x,y,width,height);
        this.image = image;
        image.setBounds(x,y,width,height);
        isHighlight = false;
        isHit = false;
    }

    private void setupText(boolean isSmall)   {
        if(!isSmall)
            text = new Text(getName(),32, Color.BLACK, false, assets);
        else
            text = new Text(getName(),24, Color.BLACK, false, assets);
        //title.setFontSize(32);
        //title.setColor(Color.BLACK);
        text.setWrap();

        text.setPosition(getX()+getWidth()/2-text.getWidth()/2,getY()+ getHeight()/2 - text.getHeight()/2);
    }

    public boolean pressed(float x, float y) {

        Rectangle bounds = new Rectangle((int)getX(), (int)getY(),(int)getWidth(), (int)getHeight());

        return bounds.contains((int) x, (int) y);
    }

    public void hit()   {
        isHit = true;
    }

    public boolean isHit()  {
        return isHit;
    }

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if(parent == null) {
            image.setPosition(x, y);
            highlightImage.setPosition(x, y);
            text.setPosition(x + getWidth() / 2 - text.getXCenter(), y + getHeight() / 2 - text.getYCenter());
        }
        else{
            image.setPosition(parent.getX() + x, parent.getY()+ y);
            highlightImage.setPosition(parent.getX() + x, parent.getY()+ y);
            text.setPosition(parent.getX() + x + getWidth() / 2 - text.getXCenter(), parent.getY() + y + getHeight() / 2 - text.getYCenter());
        }
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        if (parent == null) {
            super.setBounds(x, y, width, height);
            image.setBounds(x, y, width, height);
            highlightImage.setBounds(x, y, width, height);
            text.setPosition(x + getWidth() / 2 - text.getXCenter(), y + getHeight() / 2 - text.getYCenter());
        }
        else {
            super.setBounds(parent.getX() + x, parent.getY() + y, width, height);
            image.setBounds(parent.getX() + x, parent.getY() + y, width, height);
            highlightImage.setBounds(parent.getX() + x, parent.getY() + y, width, height);
            text.setPosition(parent.getX() + x + getWidth() / 2 - text.getXCenter(), parent.getY() + y + getHeight() / 2 - text.getYCenter());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!isHighlight)
            image.draw(batch, parentAlpha);
        else
            highlightImage.draw(batch, parentAlpha);
        text.draw(batch, parentAlpha);
    }

    public void setParent(Table parent) {
        this.parent = parent;
    }

}
