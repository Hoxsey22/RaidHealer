package com.hoxseygames.raidhealer.encounters.spells.Items;

import com.badlogic.gdx.graphics.Texture;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Castable;

public class RunBackItem extends Castable {

    /**
     * @param player
     */
    public RunBackItem(Player player) {
        super(player,
                "RUN BACK!",
                "You tell your ally to run back to their body so they can rez and then criticize their floor spec. The ally unit will resurrect with 50hp.",
                player.getAssets().getTexture(player.getAssets().igniteIcon),
                0,
                50,
                30f,
                true,
                3f,
                player.getAssets().getSound(player.getAssets().barrierSFX),
                player.getAssets().getSound(player.getAssets().healSFX)
        );
    }

    /**
     * @param player
     */
    public RunBackItem(Player player, String name, String description, Texture iconTexture, float newCooldown) {
        super(player,
                name,
                description,
                iconTexture,
                0,
                50,
                newCooldown,
                true,
                3f,
                player.getAssets().getSound(player.getAssets().barrierSFX),
                player.getAssets().getSound(player.getAssets().healSFX)
        );
    }

    @Override
    public void applySpell(RaidMember target) {
        getTarget().setDead(false);
        getTarget().receiveHealing(50,false);
    }

    @Override
    protected void applySpell() {

    }

    @Override
    protected boolean isCastable() {
        if(!isReady()) {
            return false;
        }
        if(getOwner().getTarget() != null)    {
            return false;
        }
        if(getOwner().isCasting()) {
            return false;
        }
        if(getQuantity() < 1)    {
            return false;
        }
        return getOwner().getTarget().isDead();
    }


    @Override
    public void checkTalents() {

    }
}
