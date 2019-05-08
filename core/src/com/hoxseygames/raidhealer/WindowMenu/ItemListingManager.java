package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.Items.GodsGraceItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.GreaterManaPotionItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.LesserManaPotionItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.ManaPotionItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.RunBackItem;

import java.util.ArrayList;

public class ItemListingManager {

    private ArrayList<MarketListing> marketListings;
    private Player player;
    private Assets assets;



    public ItemListingManager(Player player) {
        marketListings = new ArrayList<>();
        this.player = player;
        this.assets = player.getAssets();
    }


    public void loadItemListing()   {

        ManaPotionItem manaPotionItem = new ManaPotionItem(player);
        manaPotionItem.setQuantity(3);
        marketListings.add(new MarketListing(manaPotionItem));

        LesserManaPotionItem lesserManaPotionItem = new LesserManaPotionItem(player);
        lesserManaPotionItem.setQuantity(6);
        marketListings.add(new MarketListing(lesserManaPotionItem));

        GreaterManaPotionItem greaterManaPotionItem = new GreaterManaPotionItem(player);
        greaterManaPotionItem.setQuantity(1);
        marketListings.add(new MarketListing(greaterManaPotionItem));

        RunBackItem runBackItem = new RunBackItem(player);
        runBackItem.setQuantity(1);
        marketListings.add(new MarketListing(runBackItem));

        GodsGraceItem godsGraceItem = new GodsGraceItem(player);
        godsGraceItem.setQuantity(1);
        marketListings.add(new MarketListing(godsGraceItem));
    }



    public void loadGoldListings()  {
        marketListings.add(new MarketListing("Handful of Gold", "Supplies you with 100 gold.",100,new Image(assets.getTexture(assets.goldCoinsIcon)),1.99f));
        marketListings.add(new MarketListing("2x Handful of Gold", "Supplies you with 200 gold.",200,new Image(assets.getTexture(assets.goldCoinsIcon)),3.99f));
        marketListings.add(new MarketListing("Bag Full of Gold", "Supplies you with 500 gold.",500,new Image(assets.getTexture(assets.bagOfGoldIcon)),7.49f));
        marketListings.add(new MarketListing("Huge Bag of Gold", "Supplies you with 750 gold.",750,new Image(assets.getTexture(assets.bagOfGoldIcon)),8.99f));
        marketListings.add(new MarketListing("A Brick of Gold", "Supplies you with 1000 gold.",1000,new Image(assets.getTexture(assets.brickOfGoldIcon)),9.99f));
    }

    public PurchasePlate convertToPlate(int index, WindowMenuPopup windowMenuPopup)   {
        MarketListing selectedListing = marketListings.get(Math.abs(index%marketListings.size()));
        if(!selectedListing.isGold())
            return new PurchasePlate(selectedListing.getItemListing(), windowMenuPopup);
        else {
            return new PurchasePlate(selectedListing.getName(),
                    selectedListing.getDescription(),
                    selectedListing.getAmountofGoldGiven(),
                    selectedListing.getImageIcon(),
                    selectedListing.getCashCost(),
                    windowMenuPopup
            );
        }
    }


}
