package com.hoxseygames.raidhealer.encounters.spells.Items.Type;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;

public abstract class Instant extends Item {

    protected Sound sfx;
    /**
     * @param player
     * @param name
     * @param description
     * @param iconTexture
     * @param quantity
     * @param output
     * @param cooldown
     * @param sfx
     */
    public Instant(Player player, String name, String description, Texture iconTexture,
                   int quantity, int output, float cooldown, boolean needsTarget, Sound sfx) {
        super(player, name, description, iconTexture, quantity, output, cooldown,needsTarget);

        setSpellType("Instant");
        this.sfx = sfx;

    }

    /**
     * @param player
     * @param name
     * @param description
     * @param iconTexture
     * @param output
     * @param cooldown
     * @param sfx
     */
    public Instant(Player player, String name, String description, Texture iconTexture,
                   int output, float cooldown, boolean needsTarget, Sound sfx) {
        super(player, name, description, iconTexture, 0, output, cooldown, needsTarget);

        setSpellType("Instant");
        this.sfx = sfx;

    }

    @Override
    public void castSpell() {
        if(isCastable())    {
            startCooldownTimer();
            AudioManager.playSFX(sfx, false);
            use();
        }
    }
}
