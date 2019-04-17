package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class Text extends Actor{

    public static final int FONT_SIZE_16 = 16;
    public static final int FONT_SIZE_18 = 18;
    public static final int FONT_SIZE_24 = 24;
    public static final int FONT_SIZE_32 = 32;
    public static final int FONT_SIZE_45 = 45;
    private Assets assets;
    private Label label;
    private Label.LabelStyle labelStyle;
    private Color textColor;
    private int fontSize;

    public Text(String text, boolean isBorder, Assets assets)    {
        this.assets = assets;

        labelStyle = new Label.LabelStyle(assets.getFont(isBorder), Color.BLACK);
        label = new Label(text,labelStyle);

        label.setAlignment(Align.left);

    }

    public Text(String text, Assets assets)    {
        this.assets = assets;

        labelStyle = new Label.LabelStyle(assets.getFontTitle(), Color.WHITE);
        label = new Label(text,labelStyle);

        label.setAlignment(Align.left);

    }

    public Text(String text, int fontSize, Color color, boolean isBordered, Assets assets)    {
        this.assets = assets;
        labelStyle = new Label.LabelStyle(loadFont(fontSize,isBordered), color);
        label = new Label(text,labelStyle);

        label.setAlignment(Align.left);
    }

    public Text(String text, Color color, Assets assets)    {
        this.assets = assets;
        labelStyle = new Label.LabelStyle(loadFont(160,false), color);
        label = new Label(text,labelStyle);

        label.setAlignment(Align.left);
    }

    public void setText(String string)   {
        label.setText(string);
    }

    public float getWidth() {
        return label.getWidth();
    }

    public float getHeight() {
        return label.getHeight();
    }

    public void setWrap()   {
        label.setWrap(true);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        label.setPosition(x,y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
        label.setPosition(x,y,alignment);
    }

    public void setAlignment(int alignment)  {
        label.setAlignment(alignment);
    }

    private BitmapFont loadFont(int fontSize, boolean isBordered)    {
        if(isBordered) {
            switch (fontSize) {
                case 16:
                    return assets.getFont(assets.gameFontB16);
                case 18:
                    return assets.getFont(assets.gameFont18);
                case 24:
                    return assets.getFont(assets.gameFontB24);
                case 32:
                    return assets.getFont(assets.gameFontB32);
                case 45:
                    return assets.getFont(assets.gameFontB45);
            }
        }
        else {
            switch (fontSize) {
                case 16:
                    return assets.getFont(assets.gameFont16);
                case 18:
                    return assets.getFont(assets.gameFont18);
                case 24:
                    return assets.getFont(assets.gameFont24);
                case 32:
                    return assets.getFont(assets.gameFont32);
                case 45:
                    return assets.getFont(assets.gameFont45);
                case 160:
                    return assets.getFont(assets.cdFont);
            }
        }
        return assets.getFont(assets.gameFontB16);
    }

    public Label getLabel() {
        return label;
    }

    public void setAlpha(float newAlpha)  {
        label.setColor(label.getColor().r,label.getColor().g,label.getColor().b,newAlpha);
    }

    public void disable()   {
        setAlpha(0);
    }

    public void show()  {
        setAlpha(1f);
    }

    public float getXCenter()    {
        return label.getWidth()/2;
    }

    public float getYCenter()    {
        return label.getHeight()/2;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }

    public void setLabelStyle(Label.LabelStyle labelStyle) {
        this.labelStyle = labelStyle;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void draw(Batch batch, float parentAlpha) {
        label.draw(batch,parentAlpha);
    }
}
