package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;

public class WindowMenuPopup extends Group {

    public TitleBar titleBar;
    protected Image disableBg;
    public Image bgImage;
    protected Table body;
    protected String titleString;
    protected Assets assets;
    protected ImageTextButton silverDisplay;
    protected ImageTextButton goldDisplay;
    protected Player player;
    int PAD = 5;



    public WindowMenuPopup(Player player, String titleString)    {
        this.player = player;
        this.assets = player.getAssets();
        this.titleString = titleString;
        create();
    }

    protected void create() {
        disableBg = new Image(assets.getTexture(assets.disableBG));
        disableBg.setBounds(0,0, RaidHealer.WIDTH, RaidHealer.HEIGHT);
        addActor(disableBg);

        bgImage = new Image(assets.getTexture(assets.windowMenuBG));
        bgImage.setPosition(RaidHealer.WIDTH/2-bgImage.getWidth()/2,
        RaidHealer.HEIGHT/2-bgImage.getHeight()/2-25);
        addActor(bgImage);

        titleBar = new TitleBar(titleString,assets);
        titleBar.setPosition(bgImage.getX()+bgImage.getWidth()/2-titleBar.getWidth()/2,
                bgImage.getY()+bgImage.getHeight()-titleBar.getHeight()/2);
        addActor(titleBar);
        setupExitButtonListener();

        silverDisplay = new ImageTextButton(""+player.getSilver(), assets.getSkin(), "silver_display");

        goldDisplay = new ImageTextButton(""+player.getGold(), assets.getSkin(), "gold_display");

        body = new Table();
        body.top();
        body.setBounds(bgImage.getX()+10, bgImage.getY() + 10, bgImage.getWidth()-20, titleBar.getY()-bgImage.getY()-10);
        addActor(body);
        body.add(goldDisplay).right().padRight(5).padBottom(10);
        body.add(silverDisplay).left().padLeft(5).padBottom(10);
        body.row();
    }

    protected void setupExitButtonListener()    {
        titleBar.exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disable();
            }
        });
    }

    private void disable()  {
        this.remove();
    }

    private void updatePlayersCurrency()   {
        silverDisplay.setText(""+player.getSilver());
        goldDisplay.setText(""+player.getGold());
    }

    public Player getPlayer()  {
        return player;
    }

    public Table getBody()  {
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updatePlayersCurrency();
        super.draw(batch, parentAlpha);
    }
}
