package com.hoxseygames.raidhealer.encounters.entities.bosses.stage2;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Eviscerate;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Pounce;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Swipe;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.TankSwap;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

/**
 * Created by Hoxsey on 7/20/2017.
 */

public class WampusCat extends Boss {

    private Pounce pounce;
    private AutoAttack autoAttack;

    public WampusCat(Assets assets) {
        super("Wampus Cat",
                Strings.WAMPUS_CAT_DESCRIPTION,
                150 ,
                new Raid(9, assets),
                assets);

        setId(8);
        create();
    }

    @Override
    public void create()    {
        super.create();
        setDamage(20);
        autoAttack = new AutoAttack(this);
        TankSwap tankSwap = new TankSwap(this, 12f);
        pounce = new Pounce(this);
        Eviscerate eviscerate = new Eviscerate(this, 10f);
        Swipe swipe = new Swipe(this);

        getPhaseManager().addPhase(new Phase(this,30f, autoAttack, tankSwap, pounce));
        getPhaseManager().addPhase(new Phase(this, 30f, autoAttack, tankSwap, swipe, eviscerate));

        loadDebuff(new BleedEffect(this));
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewTalentText();
            getRewardPackage().addNewSpellText();
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().penanceIcon)));
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
