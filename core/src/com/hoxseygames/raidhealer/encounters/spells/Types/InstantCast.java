package com.hoxseygames.raidhealer.encounters.spells.Types;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Spell;

/**
 * Created by Hoxsey on 8/31/2017.
 */

public abstract class InstantCast extends Spell {

    private Sound spellSFX;

    /**
     *
     * @param player
     * @param name
     * @param description
     * @param output
     * @param costPercentage
     * @param cooldown
     * @param spellSFX
     * @param assets
     */
    protected InstantCast(Player player, String name, String description, Texture imageIcon, int levelRequirement,
                          int numOfTargets, int output, float costPercentage, float cooldown, Sound spellSFX, Assets assets) {
        super(player, name, description, imageIcon, levelRequirement, output, costPercentage, cooldown, assets);
        setSpellType("Instant");
        this.spellSFX = spellSFX;
        setNumOfTargets(numOfTargets);
    }

    @Override
    public void castSpell() {
        if(isCastable())    {
            useMana();
            startCooldownTimer();
            AudioManager.playSFX(spellSFX, false);
            applySpell(getOwnerTarget());
        }

    }

    @Override
    public void applySpell(RaidMember target)    {
        target.receiveHealing(getOutput(), getCriticalChance().isCritical());
        if(getNumOfTargets() > 1) {
            getRandomTargets(target);
            for (int i = 0; i < getTargets().size(); i++) {
                getTargets().get(i).receiveHealing(getOutput(), getCriticalChance().isCritical());
            }
        }
    }

    @Override
    public void stop() {

    }

    public void getRandomTargets(RaidMember currentTarget) {
        setTargets(getOwner().getRaid().getRaidMembersWithLowestHp(getNumOfTargets(), currentTarget));
    }

    public Sound getSpellSFX() {
        return spellSFX;
    }

    public void setSpellSFX(Sound spellSFX) {
        this.spellSFX = spellSFX;
    }
}
