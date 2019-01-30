package com.hoxseygames.raidhealer.encounters.player.bars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.Heal;
import com.hoxseygames.raidhealer.encounters.spells.Renew2;
import com.hoxseygames.raidhealer.encounters.spells.Spell;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/20/2017.
 */
public class SpellBar extends Group {

    private ArrayList<Spell> spells;
    private Image image;
    private Player owner;
    private Assets assets;
    private ArrayList<Rectangle> positions;

    public SpellBar(Player owner) {
        setBounds(0,0,480,97);

        spells = new ArrayList<>(4);
        positions = new ArrayList<>(4);

        this.owner = owner;

        assets = owner.getAssets();

        image = new Image(assets.getTexture(assets.spellBar));
        image.setBounds(0,0,480,97);
        setName("Spell Bar");

        createPositions();
    }

    private void createPositions()   {
        for (int i = 0; i < 4; i++) {
            positions.add(new Rectangle(78+80*i,8,80,80));
        }
    }


    /**
     * This method will check if a spell actor collides with one of the available spaces and if so,
     * it will add the spell to the spell bar.
     *
     * @param spell
     * @return
     */
    public boolean addSpell(Spell spell)   {
        return  positionSpell(spell);
    }

    private boolean positionSpell(Spell spell)    {
        int bestPosition = -1;
        float bestPositionResult = -1;

        Rectangle spellBounds = new Rectangle(spell.getX(), spell.getY(), spell.getWidth(), spell.getHeight());
        for(int i = 0; i < positions.size(); i++)   {
            if(spellBounds.overlaps(positions.get(i)))    {
                if(bestPosition == -1)    {
                    bestPosition = i;
                    bestPositionResult = getDistanceBetweenRectangles(positions.get(i), spellBounds);
                }
                else {
                    if(bestPositionResult > getDistanceBetweenRectangles(positions.get(i), spellBounds))    {
                        bestPosition = i;
                        bestPositionResult = getDistanceBetweenRectangles(positions.get(i), spellBounds);
                    }
                }
            }
        }
        if(bestPosition != -1)    {
            addSpell(bestPosition, spell);
            return true;
        }
        else {
            return false;
        }
    }

    private float getDistanceBetweenRectangles(Rectangle r1, Rectangle r2) {
        Vector2 r1Center = r1.getCenter(new Vector2());
        Vector2 r2Center = r2.getCenter(new Vector2());

        return Math.abs(r1Center.y - r2Center.y) + Math.abs(r1Center.x - r2Center.x);
    }





    private void addSpell(int index, Spell spell)  {
        if(index > spells.size()-1)   {

            for(int i = 0; i < spells.size(); i++)   {
                if(spell.getName().equalsIgnoreCase(spells.get(i).getName()))    {
                    removeActor(spells.get(i));
                    spells.remove(i);
                    spells.add(spell);
                    addActor(spells.get(spells.size()-1));
                    resetSpellPosition();
                    return;
                }
            }
            spells.add(spell);
            addActor(spells.get(spells.size()-1));
            resetSpellPosition();
        }
        else    {
            swapSpell(index, spell);
            resetSpellPosition();
        }
    }

    private void swapSpell(int index, Spell spell) {

        for(int i = 0; i < spells.size(); i++)   {

            if(spell.getName().equalsIgnoreCase(spells.get(i).getName()))    {
                removeActor(spells.get(i));
                spells.set(i, spells.get(index));
                spells.set(index, spell);
                addActor(spells.get(index));
                return;
            }

        }
        removeActor(spells.get(index));
        spells.set(index, spell);
        addActor(spells.get(index));

    }

    public void resetToDefault()    {
        clearSpells();
        addSpell(0,new Heal(owner, assets));
        addSpell(1,new Renew2(owner,assets));
    }

    public void loadSpells(ArrayList<String> spellNames)    {
        clearSpells();
        if(spellNames.size() == 0)    {
            addSpell(0,new Heal(owner, assets));
            addSpell(1,new Renew2(owner,assets));
        }
        else {

            for (int i = 0; i < spellNames.size(); i++) {
                addSpell(i,owner.getSpellBook().getCopySpell(owner.getSpellBook().getSpell(spellNames.get(i))));
            }
        }
        System.out.println("-- Spells loaded.");
    }


    private void resetSpellPosition()    {
        for(int i = 0; i < spells.size(); i++)   {
            spells.get(i).setPosition(positions.get(i).getX(), positions.get(i).getY());
        }
    }

    public Spell getSpell(int index)  {
        return spells.get(index);
    }

    private void clearSpells()   {
        spells.clear();
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        for(int i = 0; i < spells.size(); i++)   {
            if(positions.get(i).contains(x,y))  {
                return spells.get(i);
            }
        }
        return null;
    }

    public void revalidateSpellBar()    {
        for(int i = 0; i < spells.size(); i++)   {
            System.out.println("Spell: "+spells.get(i).getName());
            if(!owner.getSpellBook().isSpellSelectable(spells.get(i)))    {
                spells.get(i).remove();
                spells.remove(i);
                System.out.println("---- spell removed");
                i--;
            }
        }
        resetSpellPosition();
        System.out.println("Spells size:"+spells.size());
    }

    public ArrayList<String> getData()  {
        ArrayList<String> spellData = new ArrayList<>();
        for (int i = 0; i < spells.size(); i++) {
            spellData.add(spells.get(i).getName());
        }
        return spellData;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public ArrayList<Rectangle> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Rectangle> positions) {
        this.positions = positions;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.draw(batch, parentAlpha);
        for(int i = 0; i < 4; i++)   {
            if(i > spells.size()-1)    {
                batch.draw(assets.getTexture(assets.blankIcon),
                        positions.get(i).getX(),
                        positions.get(i).getY(),
                        positions.get(i).getWidth(),
                        positions.get(i).getHeight());
            }
        }
        super.draw(batch, parentAlpha);
    }
}

