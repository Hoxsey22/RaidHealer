package com.hoxseygames.raidhealer.encounters.spells;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.Text;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.AtonementEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.BarrierEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.RenewingNovaEffect;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/17/2017.
 */
public abstract class Spell extends Actor {


    public static class SpellData implements Serializable {

        public String name;
        public String description;
        public String cost;
        public String spelltype;
        public String cooldown;

        public SpellData() {
            name = "";
            description = "";
            cost = "";
            spelltype = "";
            cooldown = "";
        }

        public void setData(String name, String description, String cost, String spelltype, String cooldown){
            this.name = ""+name+"";
            this.description = ""+description+"";
            this.cost = ""+cost+"";
            this.spelltype = ""+spelltype+"";
            this.cooldown = ""+cooldown+"";
        }
    }

    private Player owner;
    private String name;
    private String description;
    private String spellType;
    private int output;
    private int MIN_OUTPUT;
    private int cost;
    private int MIN_COST;
    private float cooldown;
    private float MIN_COOLDOWN;
    private float cdCounter;
    private boolean isReady;
    private boolean isCasting;
    private Texture image;
    private float cdPercentage;
    private Timer cdTimer;
    private Assets assets;
    private Text text;
    private RaidMember target;
    private CriticalChance criticalChance;
    private int MIN_CRITICAL;
    private int levelRequirement;
    protected int numOfTargets;
    protected ArrayList<RaidMember> targets;
    private SpellData spellData;
//
    /**
     * @param player
     * @param name
     * @param description
     * @param output
     * @param costPercentage
     * @param cooldown
     * @param assets
     */
    protected Spell(Player player, String name, String description, int levelRequirement, int output,
                    float costPercentage, float cooldown, Assets assets) {
        setBounds(0,0,80,80);
        owner = player;
        this.assets = assets;
        this.name = name;
        setName(name);
        this.description = description;
        this.levelRequirement = levelRequirement;
        this.output = output;
        MIN_OUTPUT = output;
        this.cost = (int)((float)owner.getMaxMana()*(costPercentage/100f));
        MIN_COST = cost;
        this.cooldown = cooldown;
        MIN_COOLDOWN = cooldown;
        isReady = true;
        isCasting = false;
        cdCounter = 0f;
        cdPercentage = 1f;
        MIN_CRITICAL = 15;
        criticalChance = new CriticalChance(MIN_CRITICAL);
        spellData = new SpellData();
        setupText();
    }
    public abstract void castSpell();

    public abstract void checkTalents();

    public abstract void applySpell(RaidMember target);

    /**
     * This starts the cooldown timer for the spell
     */
    protected void startCooldownTimer()    {
        cdTimer = new Timer();
        isReady = false;
        setCdCounter(cooldown);
        System.out.println("start cd");
        cdTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                cdCount();
                if(getCdPercentage() <= 0f) {
                    cdTimer.clear();
                    isReady = true;
                    setCdCounter(0);
                }

            }
        },0.1f,0.1f, (int)(cooldown/0.1));
    }

    private void setupText() {
        text = new Text("", 24, Color.WHITE, true,assets);
        text.setPosition(getX()+getWidth()/2 - text.getXCenter(), getY() + getHeight()/2 -
                text.getYCenter());
        text.setAlignment(Align.center);
    }

    /**
     * Gets the player that owns the the spell
     * @return Player
     */
    protected Player getOwner() {
        return owner;
    }

    /**
     * Changes the owner of the spell
     * @param owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     *
     * @return boolean
     */
    protected boolean isCasting() {
        return isCasting;
    }

    protected void setCasting(boolean casting) {
        isCasting = casting;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public float getCdCounter() {
        return cdCounter;
    }

    private void setCdCounter(float cdCounter) {
        this.cdCounter = cdCounter;
    }

    private void cdCount()   {
        cdCounter = cdCounter - 0.1f;
        getCdPercentage();
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    protected int getOutput() {
        return output;
    }

    protected void setOutput(int output) {
        this.output = output;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCostPercentage(float costPercentage) {
        cost = (int)((float)owner.getMaxMana()*(costPercentage/100f));
    }

    private float getCdPercentage() {
        return cdPercentage = cdCounter/cooldown;
    }

    public void setCdPercentage(float cdPercentage) {
        this.cdPercentage = cdPercentage;
    }

    protected void useMana()   {
        owner.setMana(owner.getMana() - cost);
    }

    public void setCriticalChance(int chance)  {
        criticalChance.setChanceThreshold(chance);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected boolean isCastable() {
        if(owner.getTarget() == null)    {
            System.out.println("NO TARGET FOUND!");
            return false;
        }
        if(owner.getMana() < cost) {
            System.out.println("OUT OF MANA!");
            return false;
        }
        if(!isReady) {
            System.out.println(name + " IS NOT READY!");
            return false;
        }
        if(isCasting) {
            System.out.println(name + " IS STILL CASTING!");
            return false;
        }
        if(owner.getTarget().isDead())    {
            System.out.println("ID:"+owner.getTarget().getId()+" Target is dead!");
            return false;
        }

        return true;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    protected RaidMember getOwnerTarget() {
        return owner.getTarget();
    }

    public RaidMember getTarget() {
        return target;
    }

    public String getSpellType() {
        return spellType;
    }

    protected void setSpellType(String spellType) {
        this.spellType = spellType;
    }

    public void setOwnerTarget(RaidMember newTarget) {
        owner.setTarget(newTarget);
    }

    public void setTarget(RaidMember newTarget) {
        target = newTarget;
    }

    public void resetCD()   {
        if(cdTimer != null) {
            cdTimer.stop();
            cdTimer.clear();
        }
        isReady = true;
        isCasting = false;
        setCdCounter(0);
    }

    public abstract void stop();

    /*
    Talents
     */

    public int applyCriticalHealerII(RaidMember target, int output)   {
        if (criticalChance.isCritical()) {
            BarrierEffect barrier = new BarrierEffect(owner);
            target.applyShield(output);
            target.addStatusEffect(barrier);
            return target.receiveHealing(output, true);
        }
        else    {
            return target.receiveHealing(output, false);
        }
    }

    /**
     * @param target
     * @description Checks and applies renewing nova talent if not nothing.
     */
    public void applyRenewingNova(RaidMember target) {
        target.addStatusEffect(new RenewingNovaEffect(owner));
    }

    public void triggerAtonement(int output) {
        // Atonement
        ArrayList<RaidMember> smiteBuffedMembers = owner.getRaid().getStatusEffectedRaidMembers("Atonement Effect");

        for (int i = 0; i < smiteBuffedMembers.size(); i++) {
            applyCriticalHealerII(smiteBuffedMembers.get(i), (int) ((float) output * 0.4f));
        }
    }

    public void applyAtonement(RaidMember target)    {
        if(name.equalsIgnoreCase("Barrier")) {
            target.addStatusEffect(new AtonementEffect(owner, true));
        }
        else    {
            target.addStatusEffect(new AtonementEffect(owner, false));
        }
    }

    public void checkCriticalHealer()   {
        if(owner.getTalentTree().getTalent(TalentTree.CRITICAL_HEALER).isSelected())    {
            setCriticalChance(35);
        }
    }

    public void applyMasteringHealing(RaidMember target, int output) {
        if(CriticalDice.roll(60, 100,1)) {
            int newOutput = output;
            float missingHpPercentage = 1f - target.getFullHealthPercent();
            float spellIncrease = 0.5f * missingHpPercentage;

            newOutput = newOutput + (int) ((float) newOutput * spellIncrease);

            target.receiveHealing(newOutput, criticalChance.isCritical());
        }
        else    {
            target.receiveHealing(output, criticalChance.isCritical());
        }

        if (this.name.equalsIgnoreCase("Heal")) {
            RaidMember raidMember = owner.getRaid().getRaidMembersWithLowestHp(1).get(0);
            raidMember.receiveHealing((int) ((float) output / 2f), criticalChance.isCritical());
        }


    }

    protected void getRandomTargets()  {
        targets = getOwner().getRaid().getRaidMembersWithLowestHp(numOfTargets);
    }

    public void resetDefault()  {
        output = MIN_OUTPUT;
        cost = MIN_COST;
        cooldown = MIN_COOLDOWN;
        setCriticalChance(MIN_CRITICAL);
    }

    public String toString()    {
        return description+"\n"+100*(float)cost/owner.getMaxMana()+"% of base mana\n"+
                spellType+"\n"+
                cooldown+" second cooldown";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getMIN_OUTPUT() {
        return MIN_OUTPUT;
    }

    public void setMIN_OUTPUT(int MIN_OUTPUT) {
        this.MIN_OUTPUT = MIN_OUTPUT;
    }

    public int getMIN_COST() {
        return MIN_COST;
    }

    public void setMIN_COST(float costPercentage) {
        this.MIN_COST = (int)((float)owner.getMaxMana()*(costPercentage/100f));
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public float getMIN_COOLDOWN() {
        return MIN_COOLDOWN;
    }

    public void setMIN_COOLDOWN(float MIN_COOLDOWN) {
        this.MIN_COOLDOWN = MIN_COOLDOWN;
    }

    protected Assets getAssets() {
        return assets;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    protected CriticalChance getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(CriticalChance criticalChance) {
        this.criticalChance = criticalChance;
    }

    public int getMIN_CRITICAL() {
        return MIN_CRITICAL;
    }

    public void setMIN_CRITICAL(int MIN_CRITICAL) {
        this.MIN_CRITICAL = MIN_CRITICAL;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    protected int getNumOfTargets() {
        return numOfTargets;
    }

    protected void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }

    protected ArrayList<RaidMember> getTargets() {
        return targets;
    }

    protected void setTargets(ArrayList<RaidMember> targets) {
        this.targets = targets;
    }

    public SpellData getSpellData() {
        spellData.setData(""+getName(), ""+getDescription(), ""+getCost()+" Mana",""+getSpellType(), ""+getCooldown()+"s CD");
        return spellData;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(),getY(),getWidth(), getHeight());
        batch.draw(getAssets().getTexture("cooldown_bar.png"), getX(),getY(),getWidth(), getHeight()*getCdPercentage());
        text.setText(String.format("%.1f",cdCounter));
        text.setPosition(getX()+getWidth()/2 - text.getXCenter(), getY() + getHeight()/2 -
                text.getYCenter());
        if(!isReady)
            text.draw(batch, parentAlpha);
    }
}
