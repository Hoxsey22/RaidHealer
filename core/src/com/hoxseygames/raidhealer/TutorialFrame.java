package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 7/27/2017.
 */

public class TutorialFrame extends Group {

    private Image disableBG;
    //private Image frame;
    private Text text;
    private Table table;
    private Assets assets;
    public int stageNumber;
    private Player player;
    private Boss boss;
    private boolean isComplete;

    public TutorialFrame(Player player, Boss boss, Assets assets)  {
        this.setAssets(assets);
        this.setPlayer(player);
        this.setBoss(boss);
        setStageNumber(1);
        setComplete(false);
        create();
    }

    private void create()    {
        setDisableBG(new Image(getAssets().getTexture(getAssets().disableBG)));
        getDisableBG().setName("disable bg");
        getDisableBG().setBounds(0,0,RaidHealer.WIDTH, RaidHealer.HEIGHT);

        /*setFrame(new Image(getAssets().getTexture(getAssets().endGameFrame)));
        getFrame().setName("frame");
        getFrame().setPosition(RaidHealer.WIDTH/2 - getFrame().getWidth()/2, RaidHealer.HEIGHT/2 - getFrame().getWidth()/2);*/
        addActor(getDisableBG());
//        addActor(getFrame());
        createText();
    }

    private void createText()    {
        setTable(new Table());
        getTable().setBounds(RaidHealer.WIDTH/2 -150,RaidHealer.HEIGHT/2-150,300,300);


        setText(new Text("Welcome Healer! This is the tutorial on how to be a healer so pay close attention!",24, Color.WHITE, false, getAssets()));
        getText().setWrap();
        getText().setAlignment(Align.center);

        getTable().add(getText().getLabel()).width(getTable().getWidth()-10);

        addActor(getTable());
    }

    public void nextStage() {

        setStageNumber(getStageNumber() + 1);
        System.out.println(getStageNumber());
        switch(getStageNumber())    {
            case 2:
                getText().setText("This is the Boss Frame. When the health bar is completely empty or red then the boss has been defeated!");
                break;
            case 3:
                getText().setText("This is the announcement section. If a special ability is coming, it will be announced here. You will have about 1.5 seconds to prepare for the ability.");
                break;
            case 4:
                getText().setText("These are the raid frames. These are the ally units you will be protecting so watch them closely!\n" +
                        "There are three different types of Ally Units; Tanks, DPS, and Healers. \n");
                break;
            case 5:
                getText().setText("The Tank is the primary target for the boss and will take constant damage. Watch them carefully. The tank has double the health of the other units, 200 hp to be exact, and the Tank can do about 5 damage per second.");
                break;
            case 6:
                getText().setText("These are the healers, your brethren. Healers provide you with 2 mana per second and will deal 2 damage per second. Protect your brethren as they provide you and your raid with damage and mana back.");
                break;
            case 7:
                getText().setText("The DPS also known as the damage dealers, are the primary source of damage toward the boss. They deal 10 damage per second so they are crucial to keep alive.");                break;
            case 8:
                getText().setText("These are the buffs and debuffs. Buffs are positive benefits and Debuffs are negative benefits for the ally unit. ");
                break;
            case 9:
                getText().setText("Now the red box around the ally unit indicates that this particular unit is being targeted by the boss and should be watched at all times because all bosses have an auto attack.");
                break;
            case 10:
                getText().setText("In order to target a unit, you must touch the frame you want to select. The frame will turn light blue indicating the target has been selected.");
                break;
            case 11:
                getText().setText("When a target is selected, you may use any of the spells in your bar indicated in the green. To do so, just press the spell and the spell will use its ability to heal the selected unit. Each spell has a different ability and are used for different situations, but you can only have 4 spells at once so choose wisely.");
                break;
            case 12:
                getText().setText("Each spell costs mana in order to cast the spell. The bar in the green box indicates your mana. You only have 1000 mana so be cautious. ");
                break;
            case 13:
                getText().setText("Some spells also have a cast time. The bar in the green is your cast bar and when the cast bar is complete, the spell’s ability will be applied to the unit.");
                break;
            case 14:
                getText().setText("Now that you know all the essentials for being a healer, let’s put it to the test and start your journey…");
                break;
            case 15:
                getText().setText("Can you save your raid before it’s too late?");
                break;
            case 16:
                setComplete(true);
                getBoss().reset();
                getBoss().start();
                break;
        }
    }

    public String hitButton(float x, float y)   {
        Actor hit = hit(x,y,false);

        if(hit !=  null)    {
            return hit.getName();
        }
        return "";
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!isComplete())
            super.draw(batch, parentAlpha);
    }

    private Image getDisableBG() {
        return disableBG;
    }

    private void setDisableBG(Image disableBG) {
        this.disableBG = disableBG;
    }

//    private Image getFrame() {
//        return frame;
//    }
//
//    private void setFrame(Image frame) {
//        this.frame = frame;
//    }

    private Text getText() {
        return text;
    }

    private void setText(Text text) {
        this.text = text;
    }

    private Table getTable() {
        return table;
    }

    private void setTable(Table table) {
        this.table = table;
    }

    private Assets getAssets() {
        return assets;
    }

    private void setAssets(Assets assets) {
        this.assets = assets;
    }

    private int getStageNumber() {
        return stageNumber;
    }

    private void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    private Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private Boss getBoss() {
        return boss;
    }

    private void setBoss(Boss boss) {
        this.boss = boss;
    }

    public boolean isComplete() {
        return isComplete;
    }

    private void setComplete(boolean complete) {
        isComplete = complete;
    }
}
