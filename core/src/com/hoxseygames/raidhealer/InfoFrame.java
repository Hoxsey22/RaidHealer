package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Hoxsey on 5/19/2018.
 */

public class InfoFrame extends Group {

    private Assets assets;
    private Image disableBG;
    private Image infoFrame;
    private Text infoTitle;
    private Text infoDescription;
    private TextButton okButton;
    private Table infoTable;
    private boolean hasTitle = true;

    public InfoFrame(String title, String description, Assets assets)  {
        this.assets = assets;

        create();

        changeDescriptionTo(description);

        if(title.equalsIgnoreCase(""))    {
            hasTitle = false;
        }

        if(hasTitle)
            infoTitle = new Text(title, 32, Color.YELLOW, true, assets);

        infoTable = new Table();
        infoTable.setBounds(infoFrame.getX()+10,infoFrame.getY(),infoFrame.getWidth()-20, infoFrame.getHeight());
        infoTable.top().center();
        if(hasTitle) {
            infoTable.add(infoTitle.getLabel()).expandX().padTop(10).colspan(4).top();
            infoTable.row();
        }
        infoTable.add(infoDescription.getLabel()).width(infoTable.getWidth()-10).expandX().expandY().pad(20).colspan(4).top();
        infoTable.row();
        infoTable.add(okButton).expandX().bottom().padBottom(10).colspan(4);

        addActor(infoTable);
    }

    private void create()   {
        // create frame
        infoFrame = new Image(assets.getTexture(assets.infoFrame2));
        infoFrame.setPosition(RaidHealer.WIDTH/2 - 150, RaidHealer.HEIGHT/2 - 150 );
        infoFrame.setSize(300, 300);
        // create disable bg
        disableBG = new Image(assets.getTexture(assets.disableBG));
        // create ok button
        okButton = new TextButton("OK", assets.getSkin(), "small_button");
        createOkButtonListener();
        // create info description
        infoDescription = new Text("This is an info frame...",18, Color.BLACK, false, assets);
        infoDescription.setWrap();

        addActor(disableBG);
        addActor(infoFrame);

    }

    public void changeDescriptionTo(String newDescription) {
        infoDescription.setText(newDescription);

        if(newDescription.length() > 200)   {
            infoDescription.setFontSize(16);
        }
    }

    private void createOkButtonListener()    {
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });
    }

}
