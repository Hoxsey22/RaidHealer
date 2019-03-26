package com.hoxseygames.raidhealer;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.player.bars.CastBar;
import com.hoxseygames.raidhealer.encounters.player.bars.ManaBar;
import com.hoxseygames.raidhealer.encounters.player.bars.SpellBar;
import com.hoxseygames.raidhealer.encounters.spells.Spell;
import com.hoxseygames.raidhealer.encounters.spells.SpellBook;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class Player {

    public static class PlayerData implements Serializable{
        public int level;
        public int totalPoints;
        public int unusedPoints;
        public ArrayList<String> talents;
        public ArrayList<String> spells;

        public PlayerData() {
            level = 0;
            totalPoints = 0;
            unusedPoints = 0;
            talents = new ArrayList<>();
            spells = new ArrayList<>();
        }

        public void setData(int level, int totalPoints, int usedPoints, ArrayList<String> talents, ArrayList<String> spells){
            this.level = level;
            this.totalPoints = totalPoints;
            this.unusedPoints = usedPoints;
            this.talents = talents;
            this.spells = spells;
        }
    }

    private final int originCritical = 10;
    private int maxMana;
    private int mana;
    private ManaBar manaBar;
    private CastBar castBar;
    private RaidMember target;
    private Raid raid;
    private Boss eTarget;
    private SpellBar spellBar;
    private float spellCastPercent;
    private boolean isCasting;
    private Assets assets;
    private TalentTree talentTree;
    private SpellBook spellBook;
    private int criticalChance;
    private int level;
    private PlayerData playerData;
    private boolean holyShockIncrease;



    public Player(Assets assets) {
        level = 0;
        maxMana = 1000;
        mana = 1000;
        spellCastPercent = 0;
        setAssets(assets);
        isCasting = false;
        talentTree = new TalentTree(this);
        spellBook = new SpellBook(this);
        createSpellBar();
        criticalChance = originCritical;

        manaBar = new ManaBar(this, assets);

        castBar = new CastBar(this, assets);
        //castBar.anchor(manaBar);
        playerData = new PlayerData();
    }

    public void loadTalents()   {
        for (int i = 0; i <  spellBar.getSpells().size(); i++)    {
            spellBar.getSpells().get(i).checkTalents();
        }
    }

    public void newLevel(int level)  {
        if(level > this.level)    {
            this.level = level;
        }
    }

    public void setLevel(int level)  {
        this.level = level;
    }

    public int getLevel()   {
        return level;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public float manaBarPercent()   {
        return ((float)mana/(float)maxMana);
    }

    public void setSpellCastPercent(float percent)   {
        spellCastPercent = percent;
    }

    public float getSpellCastPercent()  {
        return spellCastPercent;
    }

    public boolean isCasting()  {
        return isCasting;
    }

    public void notCasting()    {
        isCasting = false;
    }

    public RaidMember getTarget() {
        return target;
    }

    public void setTarget(RaidMember target) {
        if(this.target != null) {
            this.target.unselected();
            this.target = target;
            this.target.selected();
        }
        else    {
            this.target = target;
            this.target.selected();
        }

    }

    public void setRaid(Raid raid)   {
        this.raid = raid;
    }

    private void setAssets(Assets assets) {
        this.assets = assets;
    }

    public Assets getAssets()   {
        return assets;
    }

    private void createSpellBar()    {
        spellBar = new SpellBar(this);
    }

    public void reset() {
        mana = maxMana;
        //stop();
        for (int i = 0; i < spellBar.getSpells().size(); i++) {
            spellBar.getSpells().get(i).resetCD();
        }
        isCasting = false;
        setSpellCastPercent(0);
    }

    public void stop()  {
        for (int i = 0; i < spellBar.getSpells().size(); i++) {
            spellBar.getSpells().get(i).stop();
        }
        reset();
    }

    public Boss getBoss() {
        return eTarget;
    }

    public void setBoss(Boss eTarget) {
        this.eTarget = eTarget;
    }

    public void addPoint()  {
        talentTree.addPoint();
    }

    public void setTalentTree(TalentTree talentTree) {
        this.talentTree = talentTree;
    }

    public Raid getRaid() {
        return raid;
    }

    public void setCasting(boolean casting) {
        isCasting = casting;
    }

    public TalentTree getTalentTree() {
        return talentTree;
    }

    public SpellBook getSpellBook() {
        return spellBook;
    }

    public void setSpellBook(SpellBook spellBook) {
        this.spellBook = spellBook;
    }

    public SpellBar getSpellBar() {
        return spellBar;
    }

    public void setSpellBar(SpellBar spellBar) {
        this.spellBar = spellBar;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }

    public ManaBar getManaBar() {
        return manaBar;
    }

    public void setManaBar(ManaBar manaBar) {
        this.manaBar = manaBar;
    }

    public CastBar getCastBar() {
        return castBar;
    }

    public void setCastBar(CastBar castBar) {
        this.castBar = castBar;
    }

    public void stopCast()  {
        Spell castingSpell = getCastingSpell();
        if(castingSpell != null)    {
            castingSpell.stop();
        }
    }

    public Spell getCastingSpell()  {
        for(int i = 0; i <  spellBar.getSpells().size(); i++)   {
            if(spellBar.getSpells().get(i).isCasting()){
                return spellBar.getSpells().get(i);
            }
        }
        return null;
    }

    public void receiveMana(int amount)   {
        mana = mana +amount;
        if(mana > maxMana)    {
            mana = maxMana;
        }
    }

    public void save()  {
        GameData.save(this);
    }

    public boolean load()  {
        return GameData.load(this);
    }

    public void newGame()   {
        GameData.remove("save");
        resetData();
        GameData.save(this);

    }

    public PlayerData getData()   {
        playerData.setData(getLevel(), talentTree.getTotalPoints(), talentTree.getUnusedPoints(), talentTree.getData(), spellBar.getData());
        return playerData;
    }

    public void setData(PlayerData data)   {
        setLevel(data.level);
        talentTree.setTotalPoints(data.totalPoints);
        talentTree.setUnusedPoints(data.unusedPoints);
        talentTree.loadTalents(data.talents);
        spellBar.loadSpells(data.spells);
        System.out.println("- Player loaded.");
    }

    private void resetData()   {
        setLevel(0);
        talentTree.resetToDefault();
        spellBar.resetToDefault();
        System.out.println("- Player reset");
    }

    public int getOriginCritical() {
        return originCritical;
    }

    public Boss geteTarget() {
        return eTarget;
    }

    public void seteTarget(Boss eTarget) {
        this.eTarget = eTarget;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public boolean isHolyShockIncrease() {
        return holyShockIncrease;
    }

    public void setHolyShockIncrease(boolean holyShockIncrease) {
        this.holyShockIncrease = holyShockIncrease;
    }
}
