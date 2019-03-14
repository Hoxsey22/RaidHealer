package com.hoxseygames.raidhealer.encounters.spells;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Types.Periodical;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class Lightwell extends Periodical {

    /**
     * @param player
     */
    public Lightwell(Player player, Assets assets)  {
        super(player,
                "Light Well",
                "Summons a magical Light Well that will send holy light at the most injured ally unit and will give the use a little bit of mana.",
                7,
                1,
                7,
                5f,
                70f,
                60f,
                1f,
                assets.getSound(assets.lightwellSFX),
                assets);
        setDescription("Heals the most injured ally unit for "+getOutput()+"hp every second for 60 seconds.");
        setImage(this.getAssets().getTexture(getAssets().lightWellIcon));
    }

    @Override
    public void startDurationTimer()  {

        durationTimer = new Timer();

        durationTimer.scheduleTask(new Timer.Task() {
            float currentTime = 0;

            @Override
            public void run() {
                RaidMember lowest = getOwner().getRaid().getInjuriedRaidMembers(1).get(0);
                currentTime = currentTime + getSpeed();

                if(currentTime >= getDuration() )    {
                    durationTimer.stop();
                }
                lowest.receiveHealing(getOutput(),getCriticalChance().isCritical());
                getOwner().getRaid().getPlayer().receiveMana(2);
            }
        }, getSpeed(), getSpeed());

    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();
        checkHasteBuild();
    }

    @Override
    public void checkLifeboom() {
    }

}
