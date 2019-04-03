package com.hoxseygames.raidhealer.encounters.entities.bosses.stage1;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.BackStab;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.PoisonStab;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.TankSwap;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.PoisonEffect;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class Nax extends Boss {

    private AutoAttack autoAttack;
    private BackStab backStab;
    private PoisonStab poisonStab;
    private boolean isEnrage;

    public Nax(Assets assets) {
        super("Nax, The Bandit Leader",
                Strings.NAX_DESCRIPTION,
                180,
                new Raid(9, assets),
                assets);
        setId(6);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(15);

        autoAttack = new AutoAttack(this, 1.5f);

        backStab = new BackStab(this);
        backStab.setNumOfTargets(2);

        poisonStab = new PoisonStab(this);
        poisonStab.setNumOfTargets(2);

        TankSwap tankSwap = new TankSwap(this, 12f);

        getPhaseManager().addPhase(new Phase(this, 0, autoAttack,tankSwap, backStab, poisonStab));

        loadDebuff(new BleedEffect(this), new PoisonEffect(this));
    }

    @Override
    public void update() {
        if(getHpPercent() < 0.25 && !isEnrage)    {
            autoAttack.setDamage((int)((float)getDamage()*2.5f));
            backStab.setDamage((int)((float)getDamage()*3));
            poisonStab.setDamage((int)((float)getDamage()*3));
            isEnrage = true;
            displayAnnouncementTimer(getName()+" is now enraged!");
        }
    }

    @Override
    public void reset() {
        super.reset();
        autoAttack.setDamage(getDamage());
        backStab.setDamage(getDamage()*2);
        isEnrage = false;
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewTalentText();
            getRewardPackage().addNewSpellText();
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().smiteIcon)));
        }
    }

}
