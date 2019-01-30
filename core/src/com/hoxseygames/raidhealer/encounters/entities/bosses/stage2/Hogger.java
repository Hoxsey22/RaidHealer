package com.hoxseygames.raidhealer.encounters.entities.bosses.stage2;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Cleave;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.TankSwap;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;

/**
 * Created by Hoxsey on 8/17/2017.
 */

public class Hogger extends Boss {

    private AutoAttack autoAttack;
    private TankSwap tankSwap;
    private Cleave cleave;
    private int phase;

    public Hogger(Assets assets) {
        super("Hogger",
                Strings.HOGGER_DESCRIPTION,
                210,
                new Raid(12,assets),
                assets);

        setId(7);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(17);

        autoAttack = new AutoAttack(this, 2f);
        tankSwap = new TankSwap(this, 8f);
        cleave = new Cleave(this, 2f);
        cleave.setDamage(getDamage());

        getPhaseManager().addPhase(new Phase(this, 0, autoAttack, tankSwap, cleave));
        phase = 0;

    }

    @Override
    public void update() {
        if(getHpPercent() < 0.75 && getHpPercent() > 0.51 && phase == 0)    {
            cleave.setNumOfTargets(3);
            cleave.setSpeed(cleave.getSpeed()-0.2f);
            phase = 1;
        }
        else if(getHpPercent() < 0.50 && getHpPercent() > 0.26 && phase == 1)    {
            cleave.setNumOfTargets(3);
            cleave.setSpeed(cleave.getSpeed()-0.2f);
            phase = 2;
        }
        else if(getHpPercent() < 0.25 && phase == 2)    {
            cleave.setNumOfTargets(4);
            cleave.setSpeed(cleave.getSpeed()-0.2f);
            phase = 3;
        }
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewSpellText();
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().criticalHealer2Icon)));
        }
    }

}
