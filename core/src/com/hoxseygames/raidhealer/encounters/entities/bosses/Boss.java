package com.hoxseygames.raidhealer.encounters.entities.bosses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.Text;
import com.hoxseygames.raidhealer.encounters.entities.Entity;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Mechanic;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.PhaseManager;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.Debuff;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hoxsey on 6/16/2017.
 */
public abstract class Boss extends Entity {

    private Raid enemies;
    private RaidMember target;
    private Player player;
    private ArrayList<Mechanic> mechanics;
    private ArrayList<Debuff> debuffList;
    private PhaseManager phaseManager;
    private Texture namePlate;
    private int level;
    private int raidSize;
    private Text nameText;
    private Text announcement;
    private String rewardDescription;
    private boolean isDefeated;
    private String description;
    private RewardPackage rewardPackage;

    @SuppressWarnings("unused")
    public Boss(String name, String description, int maxHp, Assets assets) {
        super(name, maxHp, assets);
        this.description = description;
        setBounds(20, 740, 400, 40);
        target = getMainTank();
        mechanics = new ArrayList<>();
        debuffList = new ArrayList<>();
        rewardPackage = new RewardPackage(this);
    }

    protected Boss(String name, String description, int bossLength, Raid enemies, Assets assets) {
        super(name, enemies.getRaidDamage()*bossLength, assets);
        setBounds(20, 740, 400, 40);
        this.description = description;
        this.enemies = enemies;
        raidSize = enemies.getRaidMembers().size();
        target = getMainTank();
        mechanics = new ArrayList<>();
        debuffList = new ArrayList<>();
        phaseManager = new PhaseManager();

        nameText = new Text(name, 45, Color.BLACK,false, assets);
        nameText.setPosition(getX()+(getWidth()/2) - nameText.getWidth()/2 ,getY() + getHeight()/2 - nameText.getHeight()/2);

        announcement = new Text("",16,Color.RED, false, assets);
        announcement.setPosition(getX(), enemies.getRaidMember(0).getY()+enemies.getRaidMember(0).getHeight()+10);
        announcement.setWrap();
        announcement.setAlignment(Align.left);
        rewardPackage = new RewardPackage(this);
        rewardDescription = "";
    }

    public void create()    {
        System.out.println("boss created!");
    }

    public void update()    {
        // checks if there are any additional phases
    }

    public RaidMember getMainTank() {
       return enemies.getRaidMember(0);
    }

    public RaidMember getOffTank() {
        return enemies.getRaidMember(1);
    }

    protected void loadDebuff(Debuff... newDebuff)    {
        Collections.addAll(debuffList, newDebuff);
    }

    protected void displayAnnouncementTimer(final String text)  {
        final Timer announcementTimer = new Timer();

        announcementTimer.scheduleTask(new Timer.Task() {

            int counter = 0;

            @Override
            public void run() {

                counter++;
                if(counter == 1)    {
                    announcement.setText(text);
                }
                if(counter == 2)    {
                    announcement.setText("");
                    announcementTimer.clear();
                    announcementTimer.stop();
                }

            }
        },0.1f, 2f);
    }

    public void start() {
        System.out.println("BOSS IS NOW ACTIVE!");
        create();
        enemies.start(this);
        getPhaseManager().startPhase();

       /* for(int i = 0; i < mechanics.size(); i++)   {
            mechanics.get(i).start();
        }
        */
    }

    public void stop()  {
        System.out.println("BOSS IS NOW INACTIVE!");
        /*for (int i = 0; i <  mechanics.size(); i++)
            mechanics.get(i).stop();
            */
        enemies.stop();
        getPhaseManager().reset();
    }

    public void nextThreat() {

        ArrayList<RaidMember> tanks = enemies.tanksAlive();

        if (!enemies.isRaidDead()) {

            switch (tanks.size())   {
                case 0:
                    target = enemies.getRandomRaidMember(1).get(0);
                    break;
                case 1:
                    target = tanks.get(0);
                    break;
                case 2:
                    if(target == tanks.get(0))  {
                        target = tanks.get(1);
                    }
                    else {
                        target = tanks.get(0);
                    }
                    break;
            }


            /*
            for (int i = 0; i < enemies.raidMembers.size(); i++) {
                if (enemies.getRaidMember(i).getRole() == "Tank" && !enemies.getRaidMember(i).isDead()) {
                    target = enemies.getRaidMember(i);
                    return;
                }
            }
            System.out.println("New threat is random!");
            target = enemies.getRandomRaidMember(1).get(0);
            */
        }

    }

    public RaidMember getNextThreat()   {
        ArrayList<RaidMember> tanks = enemies.tanksAlive();

        if (!enemies.isRaidDead()) {

            switch (tanks.size()) {
                case 0:
                    return enemies.getRandomRaidMember(1).get(0);
                case 1:
                    return tanks.get(0);
                case 2:
                    if (target == tanks.get(0)) {
                        return tanks.get(1);
                    } else {
                        return tanks.get(0);
                    }
            }
        }
        return null;
    }

    public abstract void reward();

    @SuppressWarnings("unused")
    public void setEnemies(Raid enemies)    {
        this.enemies = enemies;
    }

    public int getLevel() {
        return level;
    }

    public Raid getEnemies() {
        return enemies;
    }

    public RaidMember getTarget() {
        return target;
    }

    public void setTarget(RaidMember target) {
        this.target = target;
    }

    @SuppressWarnings("unused")
    public ArrayList<Mechanic> getMechanics() {
        return mechanics;
    }

    @SuppressWarnings("unused")
    public void setMechanics(ArrayList<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public int getRaidSize() {
        return raidSize;
    }

    public void setRaidSize(int raidSize) {
        this.raidSize = raidSize;
    }

    public ArrayList<Debuff> getDebuffList() {
        return debuffList;
    }

    public void setDebuffList(ArrayList<Debuff> debuffList) {
        this.debuffList = debuffList;
    }

    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    public void setPhaseManager(PhaseManager phaseManager) {
        this.phaseManager = phaseManager;
    }

    public Texture getNamePlate() {
        return namePlate;
    }

    public void setNamePlate(Texture namePlate) {
        this.namePlate = namePlate;
    }

    public Text getNameText() {
        return nameText;
    }

    public void setNameText(Text nameText) {
        this.nameText = nameText;
    }

    public Text getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Text announcement) {
        this.announcement = announcement;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public void setDefeated(boolean defeated) {
        isDefeated = defeated;
    }

    public Player getPlayer()   {
        return player;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public void setRaid(Raid newRaid)   {
        enemies = newRaid;
    }

    public void rewardPoint()   {
        getPlayer().getTalentTree().addPoint();
    }

    public void rewardSpell(String spell)   {
        //give player spell
        rewardDescription = rewardDescription +"\n"+spell+" rewarded";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void reset() {
        super.reset();
        enemies.reset();
        //enemies = new Raid(raidSize, assets);
        target = getMainTank();
        getPhaseManager().reset();
    }

    public RewardPackage getRewardPackage() {
        return rewardPackage;
    }

    public void setRewardPackage(RewardPackage rewardPackage) {
        this.rewardPackage = rewardPackage;
    }

    public String getBossIconStyle()    {
        if(isDefeated())    {
            return "defeated_boss_icon";
        }
        else    {
            return "new_boss_icon";
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getAssets().getTexture("black_bar.png"), getX(), getY(), getWidth(), getHeight());
        batch.draw(getAssets().getTexture("red_bar.png"), getX()+2, getY()+2, (getWidth()-4), getHeight()-4);
        batch.draw(getAssets().getTexture("green_bar.png"), getX()+2, getY()+2, (getWidth()-4)*getHpPercent(), getHeight()-4);
        if(target != null)
            batch.draw(getAssets().getTexture("red_outline_bar.png"), target.getX(), target.getY(), target.getWidth(), target.getHeight());
        nameText.draw(batch, parentAlpha);
        announcement.draw(batch, parentAlpha);

    }
}
