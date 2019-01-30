package com.hoxseygames.raidhealer.encounters.entities.bosses;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 11/21/2017.
 */

public class RewardPackage {

    private ArrayList<Image> images;
    private Boss boss;
    private String reward;
    private boolean spell;
    private boolean talent;
    private boolean level;

    public RewardPackage(Boss boss)  {
        this.reward = "";
        images = new ArrayList<>();
        this.boss = boss;
    }

    public void addNewSpellText()  {
        spell = true;
        reward+="New Spell!";
    }

    public void addNewTalentText()  {
        talent = true;
        reward+="New Talent Point!\n";
        boss.rewardPoint();

    }
    public void addNewLevelText()  {
        level = true;
        reward+="Level up!\n";
    }

    public void addImage(Image image)   {
        images.add(image);
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public boolean isSpell() {
        return spell;
    }

    public void setSpell(boolean spell) {
        this.spell = spell;
    }

    public boolean isTalent() {
        return talent;
    }

    public void setTalent(boolean talent) {
        this.talent = talent;
    }

    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }
}
