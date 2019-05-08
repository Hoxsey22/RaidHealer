package com.hoxseygames.raidhealer.encounters.spells.Items;

import com.hoxseygames.raidhealer.Player;

public class GreaterManaPotionItem extends ManaPotionItem {

    public GreaterManaPotionItem(Player player) {
        super(player,
                "Greater Mana Potion",
                2,
                500,
                player.getAssets().getTexture(player.getAssets().resurgenceIcon)
        );
    }
}
