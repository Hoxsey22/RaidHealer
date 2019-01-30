package com.hoxseygames.raidhealer.encounters.entities.bosses.stage1;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Earthquake;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;

/**
 * Created by Hoxsey on 8/17/2017.
 */

public class Golem extends Boss {

    private AutoAttack autoAttack;
    private Earthquake earthquake;

    public Golem(Assets assets) {
        super("Golem",
                Strings.GOLEM_DESCRIPTION,
                150,
                new Raid(6,assets),
                assets);
        setId(4);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(50);

        autoAttack = new AutoAttack(this, 3f);
        earthquake = new Earthquake(this, 5f);
        earthquake.setAnnounce();

        getPhaseManager().addPhase(new Phase(this, 0, autoAttack, earthquake));

    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewSpellText();
            getRewardPackage().addImage(new Image(getAssets().getTexture(getAssets().flashIcon)));
        }
    }

}
