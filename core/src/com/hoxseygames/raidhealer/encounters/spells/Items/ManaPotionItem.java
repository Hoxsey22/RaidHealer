package com.hoxseygames.raidhealer.encounters.spells.Items;

import com.badlogic.gdx.graphics.Texture;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Instant;

public class ManaPotionItem extends Instant {
    /**
     * @param player
     */
    public ManaPotionItem(Player player) {
        super(player,
                "Mana Potion",
                "Supplies 300 mana to you.",
                player.getAssets().getTexture(player.getAssets().resurgenceIcon),
                0,
                300,
                30f,
                false,
                player.getAssets().getSound(player.getAssets().barrierSFX)
        );
    }

    /**
     * @param player
     */
    public ManaPotionItem(Player player, String name, int output, Texture iconTexture) {
        super(player,
                name,
                "Supplies "+output+" mana to you.",
                iconTexture,
                0,
                output,
                30f,
                false,
                player.getAssets().getSound(player.getAssets().barrierSFX)
        );
    }

    @Override
    public void applySpell() {
        getOwner().receiveMana(getOutput());
    }

    @Override
    public void checkTalents() {
    }

    @Override
    public void applySpell(RaidMember target) {
    }

    @Override
    public void stop() {

    }
}
