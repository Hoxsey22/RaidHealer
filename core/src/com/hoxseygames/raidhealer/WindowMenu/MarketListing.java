package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Item;

public class MarketListing {

    private String name;
    private String description;
    private Image imageIcon;
    private int goldCost;
    private int silverCost;
    private int amountofGoldGiven;
    private float cashCost;
    private Item itemListing;
    private boolean isGold;


    public MarketListing(String name, String description, int gold, Image imageIcon, float cashCost)  {
        isGold = true;
        this.name = name;
        this.description = description;
        this.imageIcon = imageIcon;
        this.cashCost = cashCost;
        amountofGoldGiven = gold;
    }

    public MarketListing(Item itemListing)  {
        isGold = false;
        name = itemListing.getName();
        description = itemListing.getDescription();
        imageIcon = itemListing.getImage();
        goldCost = itemListing.getGoldCost();
        silverCost = itemListing.getSilverCost();
        this.itemListing = itemListing;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImageIcon() {
        return imageIcon;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getSilverCost() {
        return silverCost;
    }

    public float getCashCost() {
        return cashCost;
    }

    public Item getItemListing() {
        return itemListing;
    }

    public int getAmountofGoldGiven()   {
        return amountofGoldGiven;
    }

    public boolean isGold() {
        return isGold;
    }
}
