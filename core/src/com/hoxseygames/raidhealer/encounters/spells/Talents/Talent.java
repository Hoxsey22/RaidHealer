package com.hoxseygames.raidhealer.encounters.spells.Talents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.hoxseygames.raidhealer.Assets;

import java.io.Serializable;

/**
 * Created by Hoxsey on 9/2/2017.
 */

public class Talent extends Actor{

    public static class TalentData implements Serializable {

        public String name;
        public String description;

        public TalentData() {
            name = "";
            description = "";
        }

        public void setData(String name, String description){
            this.name = ""+name+"";
            this.description = ""+description+"";
        }
    }

    private int id;
    private TalentTree talentTree;
    private String description;
    private boolean isSelected;
    private int totalPointRequirement;
    private Talent preReq;
    private Texture image;
    private Assets assets;
    public TalentData talentData;

    public Talent(TalentTree talentTree, int id, String name, String description, Talent pair, Texture image, Assets assets) {
        this.talentTree = talentTree;
        this.id = id;
        setName(name);
        this.description = description;
        this.image = image;
        isSelected = false;
        preReq = pair;
        totalPointRequirement = 0;
        this.assets = assets;
        talentData = new TalentData();
    }

    public Talent(TalentTree talentTree, int id, String name, String description, Texture image, Assets assets) {
        this.id = id;
        this.talentTree = talentTree;
        setName(name);
        this.description = description;
        this.image = image;
        isSelected = false;
        totalPointRequirement = 0;
        this.assets = assets;
        talentData = new TalentData();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalPointRequirement() {
        return totalPointRequirement;
    }

    public void setTotalPointRequirement(int totalPointRequirement) {
        this.totalPointRequirement = totalPointRequirement;
    }

    public boolean hasPreReq()  {
        return preReq != null;
    }

    public Talent getPreReq() {
        return preReq;
    }

    private float getCenterX()   {
        return getX() + getWidth()/2;
    }

    public float getTop()   {
        return getY() + getHeight();
    }

    public TalentTree getTalentTree() {
        return talentTree;
    }

    public void setTalentTree(TalentTree talentTree) {
        this.talentTree = talentTree;
    }

    public void setPreReq(Talent preReq) {
        this.preReq = preReq;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    private Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public TalentData getTalentData() {
        talentData.setData(""+getName(), ""+getDescription());
        return talentData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY(), getWidth(), getHeight());
        if(preReq != null) {
            if (isSelected) {
                batch.draw(getAssets().getTexture(getAssets().selectedLine), getCenterX(), getTop(), getAssets().getTexture(getAssets().selectedLine).getWidth(),
                        preReq.getY() - (getY() + getHeight()));
            } else {
                batch.draw(getAssets().getTexture(getAssets().idleLine), getCenterX(), getTop(), getAssets().getTexture(getAssets().idleLine).getWidth(),
                        preReq.getY() - (getY() + getHeight()));
            }
        }
        if(isSelected()) {
            batch.draw(getAssets().getTexture(getAssets().selectedTalent), getX(), getY(), getWidth(), getHeight());
        }
    }

}
