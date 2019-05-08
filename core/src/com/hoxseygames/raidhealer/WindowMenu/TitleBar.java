package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Text;

public class TitleBar extends Group {

    protected Image titleImage;
    protected Table table;
    protected Text titleText;
    String titleString;
    protected ImageButton exitButton;
    protected Assets assets;

    public TitleBar(String titleString, Assets assets)   {
        this.titleString = titleString;
        this.assets = assets;
        create();
    }

    private void create()   {
        titleImage = new Image(assets.getTexture(assets.windowMenuTitle));
        setBounds(0,0,titleImage.getWidth(), titleImage.getHeight());

        addActor(titleImage);

        titleText = new Text(titleString, Text.FONT_SIZE_45, Color.WHITE, false, assets);

        exitButton = new ImageButton(assets.getSkin(),"exit_button");

        table = new Table();
        table.setBounds(getX()+20, getY()+20,titleImage.getWidth()-40, titleImage.getHeight()-40);
        table.add(titleText.getLabel()).expandX().center();
        table.add(exitButton).padRight(20);
        addActor(table);
    }


}
