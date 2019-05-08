package com.hoxseygames.raidhealer.encounters.spells.Items.Type;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.Text;
import com.hoxseygames.raidhealer.encounters.spells.Spell;

public abstract class Item extends Spell {


    protected Text quantityUI;
    protected int quantity;
    protected boolean needsTarget;
    protected int silverCost;
    protected int goldCost;

    /**
     * @param player
     * @param name
     * @param description
     * @param output
     * @param cooldown
     */
    public Item(Player player, String name, String description, int goldCost, Texture iconTexture, int quantity, int output, float cooldown, boolean needsTarget) {
        super(player, name, description,iconTexture,0,output, 0, cooldown,player.getAssets());

        this.goldCost = goldCost;
        this.silverCost = 100 * goldCost;


        setAssets(player.getAssets());
        this.quantity = quantity;

        quantityUI = new Text(quantity+"",Text.FONT_SIZE_16, Color.WHITE, false, getAssets());
        quantityUI.setPosition(getX()+getWidth()-quantityUI.getWidth()-1,getY());

        this.needsTarget = needsTarget;

        add(quantityUI);
    }

    public void use()   {
        subtractQuantity(1);
        if(needsTarget)
            applySpell(getTarget());
        else
            applySpell();
    }

    protected abstract void applySpell();

    public void addQuantity(int howMuchToAdd)   {
        quantity += howMuchToAdd;
    }

    public void subtractQuantity(int howMuchToSubtract)   {
        quantity -= howMuchToSubtract;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    protected boolean isCastable() {
        if(!isReady()) {
            return false;
        }
        if(needsTarget)    {
            if(getTarget() == null)    {
                return false;
            }
        }
        if(getOwner().isCasting()) {
            return false;
        }
        if(quantity < 1)    {
            return false;
        }
        return true;
    }

    public int getSilverCost() {
        return silverCost;
    }

    public int getGoldCost() {
        return goldCost;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //quantityUI.draw(batch,parentAlpha);
    }
}
