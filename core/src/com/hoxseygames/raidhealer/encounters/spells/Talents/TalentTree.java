package com.hoxseygames.raidhealer.encounters.spells.Talents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 9/1/2017.
 */

public class TalentTree extends Group{

    public static final String LIFEBOOM = "Lifeboom";
    private static final String HEALER_CHANNEL = "Healer Channel";
    public static final String RENEWING_NOVA = "Renewing Nova";
    public static final String AOD = "Aspect of the Druid";
    public static final String CRITICAL_HEALER = "Critical Healer";
    public static final String BARRIER_MASTER = "Barrier Master";
    public static final String DISCIPLINE = "Discipline";
    public static final String CRITICAL_HEALER_II = "Critical Healer II";
    public static final String HASTE_BUILD = "Haste Build";
    public static final String SUPER_NOVA = "Super Nova";
    public static final String MASTERING_HEALING = "Mastering Healing";
    public static final String HOLY_FOCUS = "Holy Focus";


    private Player owner;
    private ArrayList<Talent> talents;
    private int unusedPoints;
    private int totalPoints;
    private Assets assets;

    public TalentTree(Player player) {
        owner = player;
        talents = new ArrayList<>();
        unusedPoints = 0;
        totalPoints = 0;
        assets = owner.getAssets();
        createTalents();
        placeTalentPosition();
        //  toJSON();
    }

    public TalentTree(Player player, int unusedPoints, int totalPoints) {
        owner = player;
        talents = new ArrayList<>();
        this.unusedPoints = unusedPoints;
        this.totalPoints = totalPoints;
        assets = owner.getAssets();
        createTalents();
        placeTalentPosition();
        //toJSON();
    }

    public void toJSON()    {
        Json json = new Json(JsonWriter.OutputType.javascript);
        FileHandle file = Gdx.files.external("talents.json");

        ArrayList<Talent.TalentData> talentData = new ArrayList<>();
        for(int i = 0; i < talents.size(); i++)   {
            talentData.add(talents.get(i).getTalentData());
        }

        file.writeString(json.toJson(talentData),false);

    }

    private void createTalents()  {
        talents.add(new Talent(this, 1, LIFEBOOM, "When a Renew expires, the ally unit will receive a burst of healing based on 50% of Renew’s total healing done.", getAssets().getTexture(getAssets().lifeboomIcon), assets));
        talents.add(new Talent(this, 2, HEALER_CHANNEL, "All healers in the raid will no longer deal damage, but will instead heal.", talents.get(talents.size()-1),
                getAssets().getTexture(getAssets().workTogetherIcon), assets));
        talents.add(new Talent(this, 3, RENEWING_NOVA, "Holy Nova will now place a Renew on each of the ally units healed by Holy Nova.", talents.get(talents.size()-1),
                getAssets().getTexture(getAssets().renewingNovaIcon), assets));
        talents.add(new Talent(this, 4, AOD,"Renew now does more healing, the time between ticks are faster and ally units will now be able to receive one additional Renew.", talents.get(talents.size()-1),
                getAssets().getTexture(getAssets().aodIcon), assets));

        talents.add(new Talent(this, 5,CRITICAL_HEALER,"The critical chance of spells will now be 35% instead of 15%.",
                getAssets().getTexture(getAssets().smiteIcon), assets));
        talents.add(new Talent(this, 6, BARRIER_MASTER, "Barrier cooldown will now be 1 second and the cost will be reduced by 10.", talents.get(talents.size()-1),
                getAssets().getTexture(getAssets().tankIcon), assets));
        talents.add(new Talent(this, 7, DISCIPLINE, "Barrier absorbs more damage, Smite does more damage, critical Smites will place a small barrier and all direct heals will place an atonement buff. Smite heals will be applied to all ally units with atonement.",talents.get(talents.size()-1), getAssets().getTexture(getAssets().disciplineIcon), assets));
        talents.add(new Talent(this, 8,CRITICAL_HEALER_II, "All spells that are critical will now place a barrier for 50% of the heal and Barriers created from Smite will now be stronger.",
                talents.get(talents.size()-1), getAssets().getTexture(getAssets().criticalHealer2Icon), assets));

        talents.add(new Talent(this, 9,HASTE_BUILD,"All castable spells are now 0.5 second faster.", getAssets().getTexture(getAssets().flashIcon), assets));
        talents.add(new Talent(this, 10, SUPER_NOVA,"Holy Nova will now heals 4 ally units.", talents.get(talents.size()-1),
                getAssets().getTexture(getAssets().superNovaIcon), assets));
        talents.add(new Talent(this, 11,HOLY_FOCUS,"Divine Hymn and Light Well will now be available and Divine Hymn will now restores 15% mana back.",talents.get(talents.size()-1),
                getAssets().getTexture(getAssets().divineHymnIcon), assets));
        talents.add(new Talent(this, 12, MASTERING_HEALING,"All healing spells will now have a 60% chance to increase the heal base on the ally unit’s missing health and Heal will now heal an additional ally unit.",
                talents.get(talents.size()-1), getAssets().getTexture(getAssets().innerFocusIcon), assets));

        for(int i = 0; i < talents.size(); i++)   {
            addActor(talents.get(i));
        }


    }

    private void placeTalentPosition()   {

        for(int i = 0; i < talents.size(); i++)   {
            talents.get(i).setBounds(getAssets().talentPositions.get(i).x,getAssets().talentPositions.get(i).y, 75, 75);
        }

    }

    public Talent hit(float x, float y) {
        Actor hit = hit(x,y,false);
        if(hit != null)    {
            return getTalent(hit.getName());
        }
        return null;
    }

    public Talent getTalent(String name)   {
        for(int i =0; i < talents.size(); i++)   {
            if(talents.get(i).getName().equalsIgnoreCase(name))    {
                return talents.get(i);
            }
        }
        System.out.println("Cannot find talent!");
        return null;
    }

    public boolean usePoint(Talent selectedTalent)  {
        if(isTalentSelectable(selectedTalent))    {
            selectedTalent.setSelected(true);
            unusedPoints--;
            return true;
        }
        return false;
    }

    private boolean isTalentSelectable(Talent talent) {
        if (getUnusedPoints() < 1) {
            return false;
        }
        if (talent.hasPreReq()) {
            if (!talent.getPreReq().isSelected()) {
                return false;
            }
        }
        return talent.getTotalPointRequirement() <= getTotalPoints() && !talent.isSelected();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void addPoint()  {
        unusedPoints++;
        totalPoints++;
    }

    public ArrayList<Talent> getTalents() {
        return talents;
    }

    public void setTalents(ArrayList<Talent> talents) {
        this.talents = talents;
    }

    public void loadTalents(ArrayList<String> talentNames)    {
        clearTalents();
        for(int i = 0; i < talentNames.size(); i++)   {
            getTalent(talentNames.get(i)).setSelected(true);
        }
        System.out.println("-- Talents loaded.");
    }

    public int getUnusedPoints() {
        return unusedPoints;
    }

    public void setUnusedPoints(int unusedPoints) {
        this.unusedPoints = unusedPoints;
    }

    public float getLeft() {
        return getSmallestX();
    }

    public float getBottom()    {
        return getSmallestY();
    }

    public float getRight() {
        return getLargestX() + talents.get(0).getWidth();
    }

    public float getTop()   {
        return getLargestY() + talents.get(0).getHeight();
    }

    private float getLargestY() {
        float largest = 0;
        for(int i = 0; i < talents.size(); i++)   {
            if(largest == 0)    {
                largest = talents.get(i).getY();
            }
            else if(largest < talents.get(i).getY())    {
                largest = talents.get(i).getY();
            }
        }
        return largest;
    }

    private float getSmallestY()    {
        float smallest = 0;
        for(int i = 0; i < talents.size(); i++)   {
            if(smallest == 0)    {
                smallest = talents.get(i).getY();
            }
            else if(smallest > talents.get(i).getY())    {
                smallest = talents.get(i).getY();
            }
        }
        return smallest;
    }

    private float getLargestX() {
        float largest = 0;
        for(int i = 0; i < talents.size(); i++)   {
            if(largest == 0)    {
                largest = talents.get(i).getX();
            }
            else if(largest < talents.get(i).getX())    {
                largest = talents.get(i).getX();
            }
        }
        return largest;
    }

    private float getSmallestX()    {
        float smallest = 0;
        for(int i = 0; i < talents.size(); i++)   {
            if(smallest == 0)    {
                smallest = talents.get(i).getX();
            }
            else if(smallest > talents.get(i).getX())    {
                smallest = talents.get(i).getX();
            }
        }
        return smallest;
    }

    /**
     * All talents will no longer be selected.
     */
    private void clearTalents()  {
        for (int i = 0; i <  talents.size(); i++)   {
            talents.get(i).setSelected(false);
        }
    }

    public void resetToDefault()    {
        clearTalents();
        unusedPoints = 0;
        totalPoints = 0;
    }

    public void reset() {
        clearTalents();
        owner.getSpellBar().revalidateSpellBar();
        unusedPoints = totalPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public ArrayList<String> getData()  {
        ArrayList<String> talentData = new ArrayList<>();
        for(int i = 0; i < talents.size(); i++)   {
            if(talents.get(i).isSelected())    {
                talentData.add(talents.get(i).getName());
            }
        }
        return talentData;
    }

    private Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(int i = 0; i < talents.size(); i++)   {
            if(!isTalentSelectable(talents.get(i)) && !talents.get(i).isSelected()) {
                batch.draw(getAssets().getTexture(getAssets().shadowIcon), talents.get(i).getX(), talents.get(i).getY(),
                        talents.get(i).getWidth(), talents.get(i).getHeight());
            }
        }
    }
}
