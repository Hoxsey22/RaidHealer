package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Text;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Item;

public class PurchasePlate extends Group {

    protected Text name;
    protected Text description;

    protected Image iconImage;
    protected Image purchasePlateImage;
    protected Table table;
    protected WindowMenuPopup parent;
    protected int silverCost;
    protected int goldCost;
    protected float cashCost;
    protected int goldGiven;
    public Item itemToBeSold;
    protected Assets assets;

    public PurchasePlate(String name, String description, int goldGiven, Image imageIcon, float cashCost, WindowMenuPopup parent)  {
        this.assets = parent.getPlayer().getAssets();
        this.name = new Text(name, Text.FONT_SIZE_24, Color.BLACK, false, assets);
        this.description = new Text(description, Text.FONT_SIZE_24, Color.BLACK, false, assets);
        this.iconImage = imageIcon;
        this.parent = parent;
        this.cashCost = cashCost;
        this.goldGiven = goldGiven;
        create();
    }

    public PurchasePlate(Item itemToBeSold, WindowMenuPopup parent)  {
        this.assets = parent.getPlayer().getAssets();
        this.name = new Text(itemToBeSold.getName(), Text.FONT_SIZE_32, Color.BLACK, false, assets);
        this.description = new Text(itemToBeSold.getDescription(), Text.FONT_SIZE_24, Color.BLACK, false, assets);
        this.description.setWrap();
        this.iconImage = itemToBeSold.getImage();

        this.parent = parent;
        this.silverCost = itemToBeSold.getSilverCost();
        this.goldCost = itemToBeSold.getGoldCost();
        this.itemToBeSold = itemToBeSold;

        create();
    }

    protected void create()   {
        table = new Table();
        table.reset();
        table.top();

        iconImage.setHeight(80);
        iconImage.setWidth(80);

        purchasePlateImage = new Image(assets.getTexture(assets.purchasePlateImage));

        setBounds(parent.bgImage.getX() + parent.bgImage.getWidth()/2 - purchasePlateImage.getWidth()/2,
                parent.titleBar.getY()-parent.bgImage.getY(),
                purchasePlateImage.getWidth(),
                purchasePlateImage.getHeight());

        //dividerImage = new Image(assets.getTexture(assets.dividerImage));
        table.setBackground(new TextureRegionDrawable(assets.getTexture(assets.purchasePlateImage)));
        table.setFillParent(true);
        table.top();
        table.add(name.getLabel()).padTop(10);
        table.row();
        table.add(new Image(assets.getTexture(assets.dividerImage)));
        table.row();

        // HAVE TO FIGURE OUT HOW TO SCALE THE ICON PROPERLY
        table.add(iconImage).pad(10).height(iconImage.getHeight()*2.7f).width(iconImage.getWidth()*2.7f);
        //table.add(iconImage).pad(10);
        table.row();
        table.add(new Image(assets.getTexture(assets.dividerImage)));
        table.row();
        table.add(description.getLabel()).width(purchasePlateImage.getWidth()-20).pad(10);
        table.validate();
        addActor(table);
    }

    public void purchaseItem()  {
        parent.getPlayer().receiveItem(itemToBeSold);
    }

    public void purchaseGold()  {
        System.out.println("Before: player gold:"+parent.getPlayer().getGold());
        parent.getPlayer().receiveGold(goldGiven);
        System.out.println("After: player gold:"+parent.getPlayer().getGold());
        System.out.println("purchaseGold() - fired");
    }

}
