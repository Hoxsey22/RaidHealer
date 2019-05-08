package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.Player;

public class GoldBankWindow extends ShopWindow {

    public GoldBankWindow(Player player) {
        super(player, "Gold Bank");
    }

    @Override
    protected PurchasePlate loadListings() {
        itemListingManager.loadGoldListings();
        return itemListingManager.convertToPlate(index, this);
    }

    @Override
    protected void loadPurchaseButtons() {
        cashButton = new TextButton("$"+currentPurchasePlate.cashCost, assets.getSkin());

        cashButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.receiveGold(currentPurchasePlate.goldGiven);
            }
        });

        body.add(cashButton).center().colspan(2);
    }

    @Override
    protected void updatePurchaseButtons() {
        cashButton.setText("$"+currentPurchasePlate.cashCost);
    }
}
