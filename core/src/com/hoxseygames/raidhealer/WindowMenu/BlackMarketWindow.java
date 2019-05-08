package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.hoxseygames.raidhealer.Player;

public class BlackMarketWindow extends ShopWindow {

    public BlackMarketWindow(Player player) {
        super(player, "Black Market");
    }


    @Override
    protected PurchasePlate loadListings() {
        itemListingManager.loadItemListing();
        return itemListingManager.convertToPlate(index,this);
    }

    @Override
    protected void loadPurchaseButtons() {
        silverButton = new ImageTextButton(""+currentPurchasePlate.itemToBeSold.getSilverCost(), assets.getSkin(), "silver_currency_button");
        goldButton = new ImageTextButton(""+currentPurchasePlate.itemToBeSold.getGoldCost(), assets.getSkin(), "gold_currency_button");

        body.add(goldButton).padTop(10).left();
        body.add(silverButton).padTop(10).right();
    }

    @Override
    protected void updatePurchaseButtons() {
        silverButton.setText(""+currentPurchasePlate.itemToBeSold.getSilverCost());
        goldButton.setText(""+currentPurchasePlate.itemToBeSold.getGoldCost());

    }
}
