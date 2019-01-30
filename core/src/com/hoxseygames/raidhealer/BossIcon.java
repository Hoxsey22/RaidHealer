package com.hoxseygames.raidhealer;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 7/20/2017.
 */

public class BossIcon extends ImageButton {
    public final Boss boss;
    private final String description;

    public BossIcon(Assets assets, Boss boss)   {
        super(assets.getSkin(),boss.getBossIconStyle());
        this.boss = boss;

        setName(boss.getName());

        description = boss.getDescription();

        setBounds(assets.bossIconPosition.get(boss.getId()-2).x, assets.bossIconPosition.get(boss.getId()-2).y, 50, 50);
    }

    public String getDescription()  {
        return description;
    }

    public Boss getBoss() {
        return boss;
    }
}
