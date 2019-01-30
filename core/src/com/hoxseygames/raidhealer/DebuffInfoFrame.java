package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 5/19/2018.
 */

public class DebuffInfoFrame extends Group {

    private Assets assets;
    private Image disableBG;
    private Image infoFrame;
    private Text infoTitle;
    private Table debuffListTable;
    private Text debuffDescription;
    private TextButton okButton;
    private ArrayList<DebuffIconButton> debuffButtons;
    private Boss boss;

    public DebuffInfoFrame(Assets assets)  {
        this.assets = assets;
        debuffButtons = new ArrayList<>();

        infoFrame = new Image(assets.getTexture(assets.endGameFrame));
        infoFrame.setPosition(RaidHealer.WIDTH/2 - infoFrame.getWidth()/2, RaidHealer.HEIGHT/2 - infoFrame.getHeight()/2 );
        infoFrame.setSize(infoFrame.getWidth()+30, infoFrame.getHeight()+100);

        okButton = new TextButton("OK", assets.getSkin(), "small_button");
        createOkButtonListener();

        disableBG = new Image(assets.getTexture(assets.disableBG));

        infoTitle = new Text("Debuff", 32, Color.YELLOW, true, assets);

        debuffDescription = new Text("Click on the debuff icon to see the description of the ability.",24, Color.WHITE, false, assets);
        debuffDescription.setWrap();

        debuffListTable = new Table();
        debuffListTable.setBounds(infoFrame.getX()+5,infoFrame.getY(),infoFrame.getWidth()-10, infoFrame.getHeight());
    }

    private void loadDebuffs()  {

        for(int i = 0; i < boss.getDebuffList().size(); i++)   {
            debuffButtons.add(new DebuffIconButton(boss.getDebuffList().get(i),assets));

            debuffButtons.get(debuffButtons.size()-1).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    infoTitle.setText(actor.getName());
                    debuffDescription.setText(debuffButtons.get(debuffButtons.indexOf(actor)).getDescription());
                }
            });
        }

    }

    private void create()   {

        addActor(disableBG);
        addActor(infoFrame);

        debuffListTable.top();
        debuffListTable.add(infoTitle.getLabel()).expandX().padTop(10).colspan(4).top();
        debuffListTable.row();

        for(int i = 0; i < debuffButtons.size(); i++)   {
            debuffListTable.add(debuffButtons.get(i)).expandX().height(50).top();
            if(((i+1) % 3) == 0)    {
                debuffListTable.row();
            }
        }

        debuffListTable.row();
        debuffListTable.add(debuffDescription.getLabel()).width(debuffListTable.getWidth()-10).expandX().expandY().pad(5).colspan(4).top();
        debuffListTable.row();
        debuffListTable.add(okButton).expandX().bottom().padBottom(10).colspan(4);

        addActor(debuffListTable);
    }

    public void addInfo(Boss boss)   {
        this.boss = boss;
        infoTitle.setText("Debuff");
        debuffDescription.setText("Click on the debuff icon to see the description of the ability.");
        debuffButtons.clear();
        debuffListTable.clearChildren();
        loadDebuffs();
        create();
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
