package com.hoxseygames.raidhealer.encounters.entities.bosses.stage1;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Pounce;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

/**
 * Created by Hoxsey on 8/17/2017.
 */

public class Wolf extends Boss {

    private Pounce pounce;
    private AutoAttack autoAttack;

    public Wolf(Assets assets) {
        super("Nax's Wolf",
                Strings.NAXS_WOLF_DESCRIPTION,
                150,
                new Raid(6,assets),
                assets);
        setId(5);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(15);

        pounce = new Pounce(this, 2);
        pounce.setNumOfTargets(3);
        pounce.setSpeed(15f);
        pounce.setAnnounce();

        autoAttack = new AutoAttack(this, 1f);

        getPhaseManager().addPhase(new Phase(this, 0,autoAttack, pounce));

        loadDebuff(new BleedEffect(this));
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewTalentText();
            getRewardPackage().addNewSpellText();
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().greaterHealerIcon)));
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().barrierIcon)));
        }
    }

    public Pounce getPounce() {
        return pounce;
    }

    public void setPounce(Pounce pounce) {
        this.pounce = pounce;
    }

    public AutoAttack getAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(AutoAttack autoAttack) {
        this.autoAttack = autoAttack;
    }
}
