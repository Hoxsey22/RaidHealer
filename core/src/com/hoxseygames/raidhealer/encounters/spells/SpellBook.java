package com.hoxseygames.raidhealer.encounters.spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 9/1/2017.
 */

public class SpellBook extends Group{

    private static final String HEAL = "Heal";
    private static final String FLASH_HEAL = "Flash Heal";
    private static final String RENEW = "Renew";
    private static final String BARRIER = "Barrier";
    private static final String GREATER_HEAL = "Greater Heal";
    private static final String DIVINE_HYMN = "Divine Hymn";
    private static final String HOLY_NOVA = "Holy Nova";
    private static final String LIGHTWELL = "Light Well";
    private static final String SMITE = "Smite";
    private static final String PRAYER_OF_MENDING = "Prayer of Mending";
    private static final String DISPEL = "Dispel";
    private static final String HOLY_SHOCK = "Holy Shock";
    private static final String PENANCE = "Penance";
    private static final String DIVINE_PROTECTION = "Divine Protection";
    private static final String BLESSED_GARDEN = "Blessed Garden";

    private Player owner;
    private ArrayList<Spell> spells;
    private final Assets assets;

    public SpellBook(Player player) {
        owner = player;
        spells = new ArrayList<>();
        assets = owner.getAssets();
        createSpells();
        placeSpellPosition();
        setName("Spell Book");
    }

    public SpellBook(Player player, int addedPoints) {
        owner = player;
        spells = new ArrayList<>();
        assets = owner.getAssets();
        createSpells();
        placeSpellPosition();
        setName("Spell Book");
    }

    private void createSpells()  {

        // add spell to list
        spells.add(new Heal(owner, assets));
        spells.add(new Renew(owner, assets));
        spells.add(new FlashHeal(owner, assets));
        spells.add(new Dispel(owner, assets));
        spells.add(new HolyNova(owner, assets));
        spells.add(new PrayerOfMending(owner, assets));
        spells.add(new Barrier(owner, assets));
        spells.add(new GreaterHeal(owner, assets));
        spells.add(new Smite(owner, assets));
        spells.add(new Penance(owner, assets));
        spells.add(new HolyShock(owner, assets));
        spells.add(new Lightwell(owner, assets));
        spells.add(new DivineHymn(owner, assets));
        spells.add(new DivineProtection(owner, assets));
        spells.add(new BlessedGarden(owner, assets));

        // add spell to group
        for(int i = 0; i < spells.size(); i++)   {
            addActor(spells.get(i));
        }
    }

    public void toJSON()    {
        Json json = new Json(JsonWriter.OutputType.javascript);
        FileHandle file = Gdx.files.external("spells.json");

        ArrayList<Spell.SpellData> spellData = new ArrayList<>();
        for(int i = 0; i < spells.size(); i++)   {
            spellData.add(spells.get(i).getSpellData());
        }

        file.writeString(json.toJson(spellData),false);

    }

    private void placeSpellPosition()   {

        for(int i = 0; i < 3; i++)   {
            for(int j = 0; j < 5; j++)   {
                spells.get(i*5+j).setBounds(30+spells.get(i).getWidth()*j+30*j,610-spells.get(i).getWidth()*i-30*i,60,60);
            }
        }

    }

    public Spell selectSpell(float x, float y)  {
        Spell hit = hit(x,y);
        if(hit != null) {
            if(isSpellSelectable(hit))  {
                return getCopySpell(hit);
            }
        }
        return null;

    }

    private Spell hit(float x, float y) {
        Actor hit = hit(x,y,false);
        if(hit != null)    {
            return getSpell(hit.getName());
        }
        return null;
    }

    public Spell getSpell(String name)   {
        for(int i = 0; i < spells.size(); i++)   {
            if(spells.get(i).getName().equalsIgnoreCase(name))    {
                return spells.get(i);
            }
        }
        return null;
    }

    public boolean isSpellSelectable(Spell spell)    {

        switch (spell.getName())    {
            case BLESSED_GARDEN:
                return owner.getTalentTree().getTalent(TalentTree.AOD).isSelected();
            case DIVINE_PROTECTION:
                return owner.getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected();
            case DIVINE_HYMN:
                return owner.getTalentTree().getTalent(TalentTree.HOLY_FOCUS).isSelected();
            case LIGHTWELL:
                return owner.getTalentTree().getTalent(TalentTree.HOLY_FOCUS).isSelected();
        }

        return owner.getLevel() >= spell.getLevelRequirement();

    }

    public Spell getCopySpell(Spell spell)    {
        switch (spell.getName())    {
            case HEAL:
                return new Heal(owner,assets);
            case FLASH_HEAL:
                return new FlashHeal(owner,assets);
            case GREATER_HEAL:
                return new GreaterHeal(owner,assets);
            case HOLY_NOVA:
                return new HolyNova(owner,assets);
            case RENEW:
                return new Renew(owner,assets);
            case BARRIER:
                return new Barrier(owner,assets);
            case LIGHTWELL:
                if(owner.getTalentTree().getTalent(TalentTree.HOLY_FOCUS).isSelected()) {
                    return new Lightwell(owner, assets);
                }
            case SMITE:
                return new Smite(owner,assets);
            case PRAYER_OF_MENDING:
                return new PrayerOfMending(owner, assets);
            case DISPEL:
                return new Dispel(owner, assets);
            case HOLY_SHOCK:
                return new HolyShock(owner, assets);
            case PENANCE:
                return new Penance(owner, assets);
            case DIVINE_HYMN:
                if(owner.getTalentTree().getTalent(TalentTree.HOLY_FOCUS).isSelected()) {
                    return new DivineHymn(owner, assets);
                }
                break;
            case DIVINE_PROTECTION:
                if(owner.getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected()) {
                    return new DivineProtection(owner, assets);
                }
                break;
            case BLESSED_GARDEN:
                if(owner.getTalentTree().getTalent(TalentTree.AOD).isSelected()) {
                    return new BlessedGarden(owner, assets);
                }
                break;

        }
        return null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public float getLeft() {
        return getSmallestX();
    }

    public float getBottom()    {
        return getSmallestY();
    }

    public float getRight() {
        return getLargestX() + spells.get(0).getWidth();
    }

    public float getTop()   {
        return getLargestY() + spells.get(0).getHeight();
    }

    private float getLargestY() {
        float largest = 0;
        for(int i = 0; i < spells.size(); i++)   {
            if(largest == 0)    {
                largest = spells.get(i).getY();
            }
            else if(largest < spells.get(i).getY())    {
                largest = spells.get(i).getY();
            }
        }
        return largest;
    }

    private float getSmallestY()    {
        float smallest = 0;
        for(int i = 0; i < spells.size(); i++)   {
            if(smallest == 0)    {
                smallest = spells.get(i).getY();
            }
            else if(smallest > spells.get(i).getY())    {
                smallest = spells.get(i).getY();
            }
        }
        return smallest;
    }

    private float getLargestX() {
        float largest = 0;
        for(int i = 0; i < spells.size(); i++)   {
            if(largest == 0)    {
                largest = spells.get(i).getX();
            }
            else if(largest < spells.get(i).getX())    {
                largest = spells.get(i).getX();
            }
        }
        return largest;
    }

    private float getSmallestX()    {
        float smallest = 0;
        for(int i = 0; i < spells.size(); i++)   {
            if(smallest == 0)    {
                smallest = spells.get(i).getX();
            }
            else if(smallest > spells.get(i).getX())    {
                smallest = spells.get(i).getX();
            }
        }
        return smallest;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(int i = 0; i < spells.size(); i++)   {
            if(!isSpellSelectable(spells.get(i))) {
                batch.draw(assets.getTexture(assets.shadowIcon), spells.get(i).getX(), spells.get(i).getY(),
                        spells.get(i).getWidth(), spells.get(i).getHeight());
            }
        }
    }
}
