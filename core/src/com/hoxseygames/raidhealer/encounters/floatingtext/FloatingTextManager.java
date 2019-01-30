package com.hoxseygames.raidhealer.encounters.floatingtext;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/29/2017.
 */
public class FloatingTextManager {

    private RaidMember owner;
    private ArrayList<FloatingText> floatingTexts;
    private int idCounter;
    private Assets assets;


    public FloatingTextManager(RaidMember owner, Assets assets)    {
        this.assets = assets;
        this.owner = owner;
        floatingTexts = new ArrayList<>();
        idCounter = 0;

    }

    public void add(int damage, int type)   {
        floatingTexts.add(new FloatingText(idCounter, owner, damage,type, false, assets));
        floatingTexts.get(getIndex(idCounter)).startAnimation();
        idCounter++;
    }

    public void add(int damage, int type, boolean isCritical)   {
        floatingTexts.add(new FloatingText(idCounter, owner, damage, type,isCritical, assets));
        floatingTexts.get(getIndex(idCounter)).startAnimation();
        idCounter++;
    }

    private boolean remove(FloatingText floatingText)    {
        int removeIndex = getIndex(floatingText.getId());
        if(removeIndex != -1) {
            //floatingText.dispose();
            floatingTexts.remove(removeIndex);
            return true;
        }
        else {
            System.out.println("Index not found!");
            return false;
        }
    }

    private boolean removeInActiveText(FloatingText floatingText) {
        return !floatingText.isAnimating && remove(floatingText);
    }

    private int getIndex(int id)    {
        for (int i = 0; i < floatingTexts.size(); i++)  {
            if(floatingTexts.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    public void clear() {
        floatingTexts.clear();
        idCounter = 0;
    }

    public void draw(Batch batch, float alpha)  {

        for (int i = 0; i < floatingTexts.size(); i++) {
            if(!removeInActiveText(floatingTexts.get(i)))
                floatingTexts.get(i).draw(batch, alpha);
        }
        
    }
}
