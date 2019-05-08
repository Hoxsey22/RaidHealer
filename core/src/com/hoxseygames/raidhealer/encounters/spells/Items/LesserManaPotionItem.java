package com.hoxseygames.raidhealer.encounters.spells.Items;

import com.hoxseygames.raidhealer.Player;

public class LesserManaPotionItem extends ManaPotionItem {

    public LesserManaPotionItem(Player player) {
        super(player,
                "Lesser Mana Potion",
                3,
                150,
                player.getAssets().getTexture(player.getAssets().resurgenceIcon)
        );
    }
}
