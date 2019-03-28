package com.hoxseygames.raidhealer.encounters.entities.raid;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.HealingTracker;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hoxsey on 6/16/2017.
 */
public class Raid extends Group {

    private ArrayList<RaidMember> raidMembers;
    private ArrayList<RaidMember> healers;
    private Timer raidDamageTimer;
    private Assets assets;
    private boolean isRaidAlive;
    private HealingTracker healingTracker;
    private Player player;

    public Raid(int size, Assets assets)   {
        this.assets = assets;
        setName("Raid");
        raidMembers = new ArrayList<>();
        healers = new ArrayList<>();
        preMade(size);
        isRaidAlive = true;
        healingTracker = new HealingTracker();
    }
    public Raid(int numOfTanks, int numOfHealers, int numOfDps, Assets assets)   {
        this.assets = assets;
        setName("Raid");
        raidMembers = new ArrayList<>();
        healers = new ArrayList<>();
        customRaid(numOfTanks,numOfHealers,numOfDps);
        isRaidAlive = true;
        healingTracker = new HealingTracker();
        for(int i = 0; i < raidMembers.size();i++)   {
            System.out.println("raid memeber role "+ raidMembers.get(i).getRole());
        }
    }

    public void start(final Boss t)   {

        raidDamageTimer = new Timer();
        final boolean healerChannel = t.getPlayer().getTalentTree().getTalent("Healer Channel").isSelected();
        raidDamageTimer.scheduleTask(new Timer.Task() {

            @Override
            public void run() {

                for (int i = 0; i < raidMembers.size(); i++) {
                    if(!raidMembers.get(i).isDead()) {
                        if(raidMembers.get(i).getRole().equalsIgnoreCase("Healer"))    {
                            player.receiveMana(raidMembers.get(i).getDamage());
                            if(healerChannel) {
                                getRaidMemberWithLowestHp().receiveHealing(raidMembers.get(i).getDamage(),false);
                                //getRaidMemberWithLowestHp().receiveHealing(200,false);
                            }
                            else {
                                t.takeDamage(raidMembers.get(i).getDamage());
                            }
                        }
                        else    {
                            t.takeDamage(raidMembers.get(i).getDamage());
                        }
                    }
                }

            }
        },3f,1f);
    }

    public void stop()  {
        if(raidDamageTimer != null) {
            raidDamageTimer.stop();
            raidDamageTimer.clear();
            if (raidMembers != null) {
                for (int i = 0; i < raidMembers.size(); i++) {
                    raidMembers.get(i).stop();
                }
            }
        }
    }

    private void preMade(int size)   {
        switch(size) {
            case 3:
                addTank(1);
                //addHealer(1);
                addDps(2);
                break;
            case 6:
                addTank(1);
                addHealer(1);
                addDps(4);
                break;
            case 9:
                addTank(2);
                addHealer(1);
                addDps(6);
                break;
            case 12:
                addTank(2);
                addHealer(2);
                addDps(8);
                break;
            case 15:
                addTank(2);
                addHealer(2);
                addDps(11);
                break;
            case 18:
                addTank(2);
                addHealer(3);
                addDps(13);
                break;
        }
    }

    private void customRaid(int tanks, int healers, int dps)    {
        addTank(tanks);
        addHealer(healers);
        addDps(dps);
    }

    private void addTank(int amount)   {
        for(int i = 0; i < amount; i++) {
            raidMembers.add(new RaidMember(raidMembers.size(), "Tank", assets));
            raidMembers.get(raidMembers.size() - 1).setAssets(assets);
            addActor(raidMembers.get(raidMembers.size() - 1));
        }
    }

    private void addHealer(int amount)   {
        for(int i = 0; i < amount; i++) {
            raidMembers.add(new RaidMember(raidMembers.size(), "Healer", assets));
            healers.add(raidMembers.get(raidMembers.size()-1));
            addActor(raidMembers.get(raidMembers.size() - 1));
        }
    }

    private void addDps(int amount)   {
        for(int i = 0; i < amount; i++) {
            raidMembers.add(new RaidMember(raidMembers.size(), "Dps", assets));
            addActor(raidMembers.get(raidMembers.size() - 1));
        }
    }

    public RaidMember getRaidMember(int index)   {
        return raidMembers.get(index);
    }

    public ArrayList<RaidMember> getRandomRaidMember(int amount) {
        ArrayList<RaidMember> raidMembers = new ArrayList<>();
        int counter = 0;
        ArrayList<RaidMember> temp = new ArrayList<>();
        temp.addAll(this.raidMembers);
        Collections.shuffle(temp);
        for (int i = 0; i < temp.size(); i++) {
            if(counter != amount) {
                if (!temp.get(i).isDead()) {
                    raidMembers.add(temp.get(i));
                    counter++;
                }
            }
            else
                return raidMembers;
        }
        return  raidMembers;
    }

    public ArrayList<RaidMember> getRandomRaidMember(int amount, ArrayList<RaidMember> group) {
        ArrayList<RaidMember> randomMembers = new ArrayList<>();
        Collections.shuffle(group);
        if(group.size() > amount) {
            for (int i = 0; i < amount; i++) {
                if (!group.get(i).isDead()) {
                    randomMembers.add(group.get(i));
                }
            }
        }
        else   {
            for (int i = 0; i < group.size(); i++) {
                if (!group.get(i).isDead()) {
                    randomMembers.add(group.get(i));
                }
            }
        }
        return  randomMembers;
    }

    public Actor getRandomRaidMember()  {
        ArrayList<RaidMember> temp = new ArrayList<>();
        temp.addAll(raidMembers);
        Collections.shuffle(temp);
        for (int i = 0; i < temp.size(); i++) {
            if (!temp.get(i).isDead())
                return temp.get(i);
        }
        return temp.get(0);
    }

    public ArrayList<RaidMember> getRaidMembersWithLowestHp(int cap, RaidMember currentTarget)    {
        ArrayList<RaidMember> lowest = new ArrayList<>();
        ArrayList<RaidMember> temp = new ArrayList<>();
        temp.addAll(raidMembers);

        Collections.sort(temp);
        int counter = 0;
        for(int i = 0; i < temp.size(); i++) {
            if (temp.get(i) != currentTarget && !temp.get(i).isDead()) {
                lowest.add(temp.get(i));
                counter++;
                if(counter == cap)    {
                    return lowest;
                }
            }
        }
        return lowest;
    }

    public ArrayList<RaidMember> getInjuriedRaidMembers(int cap)    {
        ArrayList<RaidMember> lowest = new ArrayList<>();
        ArrayList<RaidMember> temp = new ArrayList<>();

        temp.addAll(raidMembers);

        Collections.shuffle(temp);

        int counter = 0;
        for(int i = 0; i < temp.size(); i++) {
            if (!temp.get(i).isDead() && !temp.get(i).isFullHealth()) {
                lowest.add(temp.get(i));
                counter++;
                if(counter == cap)    {
                    return lowest;
                }
            }
        }
        if(lowest.size() == 0)
            return getRandomRaidMember(1);
        return lowest;
    }

    public RaidMember getRaidMemberWithLowestHp()    {
        ArrayList<RaidMember> temp = new ArrayList<>();
        temp.addAll(raidMembers);
        Collections.sort(temp);

        for(int i = 0; i <  temp.size(); i++)   {
            if(!temp.get(i).isDead())    {
                return temp.get(i);
            }
        }
        return temp.get(0);
    }

    /**
     * Method find the lowest hp raid member that isn't the selected target.
     * @param selectedMember
     * @return
     */
    public RaidMember getRaidMemberWithLowestHp(final RaidMember selectedMember)    {
        ArrayList<RaidMember> temp = new ArrayList<>();
        temp.addAll(raidMembers);
        Collections.sort(temp);

        for(int i = 0; i <  temp.size(); i++)   {
            if(!temp.get(i).isDead() && !selectedMember.isSelected())    {
                return temp.get(i);
            }
        }
        return temp.get(0);
    }

    public void receiveHealing(int output)    {
        for(int i= 0; i <  raidMembers.size(); i++)   {
            raidMembers.get(i).receiveHealing(output, false);
        }
    }

    public void takeDamage(int damage)    {
        for(int i= 0; i <  raidMembers.size(); i++)   {
            if (!raidMembers.get(i).isDead())
                raidMembers.get(i).takeDamage(damage);
        }
    }

    public ArrayList<RaidMember> tanksAlive() {
        ArrayList<RaidMember> tanks = new ArrayList<>();
        for(int i = 0; i < raidMembers.size(); i++)   {
            if(raidMembers.get(i).getRole().equalsIgnoreCase("tank") && !raidMembers.get(i).isDead())  {
                tanks.add(raidMembers.get(i));
            }
        }
        return tanks;

    }

    public ArrayList<RaidMember> healersAlive() {
        ArrayList<RaidMember> healers = new ArrayList<>();
        for(int i = 0; i < raidMembers.size(); i++)   {
            if(raidMembers.get(i).getRole().equalsIgnoreCase("healer") && !raidMembers.get(i).isDead())
                healers.add(raidMembers.get(i));
        }
        return healers;
    }

    public ArrayList<RaidMember> dpsAlive() {
        ArrayList<RaidMember> dps = new ArrayList<>();
        for(int i = 0; i < raidMembers.size(); i++)   {
            if(raidMembers.get(i).getRole().equalsIgnoreCase("dps") && !raidMembers.get(i).isDead())
                dps.add(raidMembers.get(i));
        }
        return dps;
    }

    public boolean isRaidDead() {
        for(int i = 0; i < raidMembers.size(); i++)   {
            if(!raidMembers.get(i).isDead())
                return false;
        }
        return true;
    }

    public void reset() {
        for(int i = 0; i < raidMembers.size(); i++)   {
            raidMembers.get(i).reset();
        }
    }

    public ArrayList<RaidMember> getDebuffLessRaidMembers(String name)    {
        ArrayList<RaidMember> debuffLess = new ArrayList<>();
        for(int i = 0; i <  raidMembers.size(); i++)   {
            if(!raidMembers.get(i).getStatusEffectList().contains(name) && !raidMembers.get(i).isDead())    {
                debuffLess.add(raidMembers.get(i));
            }
        }
        return  debuffLess;
    }

    public ArrayList<RaidMember> getDebuffLessRaidMembers(String name, ArrayList<RaidMember> group)    {
        ArrayList<RaidMember> debuffLess = new ArrayList<>();
        for(int i = 0; i <  group.size(); i++)   {
            if(!group.get(i).getStatusEffectList().contains(name) && !group.get(i).isDead())    {
                debuffLess.add(group.get(i));
            }
        }
        return  debuffLess;
    }

    public ArrayList<RaidMember> getRoleLessRaidMembers(String role)    {
        ArrayList<RaidMember> roleLess = new ArrayList<>();
        for(int i = 0; i <  raidMembers.size(); i++)   {
            if(!raidMembers.get(i).getRole().equalsIgnoreCase(role) && !raidMembers.get(i).isDead())    {
                roleLess.add(raidMembers.get(i));
                System.out.println("Roleless member: "+raidMembers.get(i).getId());
            }
        }
        return  roleLess;
    }

    public ArrayList<RaidMember> getStatusEffectedRaidMembers(String name)    {
        ArrayList<RaidMember> statusEffectedMembers = new ArrayList<>();
        for(int i = 0; i <  raidMembers.size(); i++)   {
            if(raidMembers.get(i).getStatusEffectList().contains(name) && !raidMembers.get(i).isDead())    {
                statusEffectedMembers.add(raidMembers.get(i));
            }
        }
        return statusEffectedMembers;
    }

    public ArrayList<RaidMember> getBuffLessRaidMembers(String name)    {
        ArrayList<RaidMember> buffLess = new ArrayList<>();
        for(int i = 0; i <  raidMembers.size(); i++)   {
            if(!raidMembers.get(i).getStatusEffectList().contains(name))    {
                buffLess.add(raidMembers.get(i));
            }
        }
        return  buffLess;
    }

    public ArrayList<RaidMember> getBuffedRaidMembers(String name)    {
        System.out.println("\n--- "+name+" ---");
        ArrayList<RaidMember> buffed = new ArrayList<>();
        for(int i = 0; i <  raidMembers.size(); i++)   {
            if(raidMembers.get(i).getStatusEffectList().contains(name))    {
                System.out.println(raidMembers.get(i).toString());
                buffed.add(raidMembers.get(i));
            }
        }
        System.out.println("==================");
        return  buffed;
    }

    public int getNumOfAlive() {
        int numOfAlive = 0;
        for(int i = 0; i <  raidMembers.size(); i++)   {
            if(!raidMembers.get(i).isDead())    {
                numOfAlive++;
            }
        }
        return numOfAlive;
    }

    public void loadHealingStats()   {
        for(int i = 0; i < raidMembers.size(); i++)   {
            healingTracker.addHealingTracker(raidMembers.get(i).getHealingTracker());
        }
    }

    public int getRaidDamage()  {
        int totalDamage = 0;
        for(int i = 0; i < raidMembers.size(); i++)   {
            totalDamage = totalDamage + raidMembers.get(i).getDamage();
        }
        return totalDamage;
    }

    public void setupListener()   {
        for(int i = 0; i < raidMembers.size(); i++)   {
            raidMembers.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Member hit");
                    player.setTarget((RaidMember)actor);
                    player.getTarget().selected();
                }
            });
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HealingTracker getHealingTracker()   {
        return healingTracker;
    }

    public ArrayList<RaidMember> getRaidMembers() {
        return raidMembers;
    }

    public void setRaidMembers(ArrayList<RaidMember> raidMembers) {
        this.raidMembers = raidMembers;
    }

    public ArrayList<RaidMember> getHealers() {
        return healers;
    }

    public void setHealers(ArrayList<RaidMember> healers) {
        this.healers = healers;
    }

    public Timer getRaidDamageTimer() {
        return raidDamageTimer;
    }

    public void setRaidDamageTimer(Timer raidDamageTimer) {
        this.raidDamageTimer = raidDamageTimer;
    }

    public boolean isRaidAlive() {
        return isRaidAlive;
    }

    public void setRaidAlive(boolean raidAlive) {
        isRaidAlive = raidAlive;
    }

    public void setHealingTracker(HealingTracker healingTracker) {
        this.healingTracker = healingTracker;
    }
}
