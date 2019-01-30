package com.hoxseygames.raidhealer.encounters.entities.bosses.stage1;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.BullCharge;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;

/**
 * Created by Hoxsey on 8/17/2017.
 */

public class WildBoar extends Boss {

    private BullCharge bullCharge;
    private AutoAttack autoAttack;

    public WildBoar(Assets assets) {
        super("Wild Boar", Strings.BOAR_DESCRIPTION,
                120,
                new Raid(6,assets),
                assets);
        setId(2);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(10);

        bullCharge = new BullCharge(this, 5f);
        bullCharge.setAnnounce();

        autoAttack = new AutoAttack(this, 1f);

        getPhaseManager().addPhase(new Phase(this,0, autoAttack, bullCharge));
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewTalentText();
            getRewardPackage().addNewSpellText();
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().dispelIcon)));
        }
    }

    public BullCharge getBullCharge() {
        return bullCharge;
    }

    public void setBullCharge(BullCharge bullCharge) {
        this.bullCharge = bullCharge;
    }

    public AutoAttack getAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(AutoAttack autoAttack) {
        this.autoAttack = autoAttack;
    }
}
