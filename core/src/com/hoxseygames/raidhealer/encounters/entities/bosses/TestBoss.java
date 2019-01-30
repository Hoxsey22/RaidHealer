package com.hoxseygames.raidhealer.encounters.entities.bosses;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Earthquake;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;

/**
 * Created by Hoxsey on 8/17/2017.
 */

public class TestBoss extends Boss {

    private AutoAttack autoAttack;
    private Earthquake earthquake;

    public TestBoss(Assets assets) {
        super("TEST BOSS","", 3000000, new Raid(15,assets), assets);
        setId(16);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(0);
    }

    @Override
    public void reward() {

    }
}
