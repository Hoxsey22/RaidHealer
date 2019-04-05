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


        setText(new Text("Welcome healer! This tutorial will show you how to be a healer!",24, Color.WHITE, false, getAssets()));
        getText().setWrap();
        getText().setAlignment(Align.center);

        getTable().add(getText().getLabel()).width(getTable().getWidth()-10);

        addActor(getTable());
    }

    public void nextStage() {

        setStageNumber(getStageNumber() + 1);
        switch(getStageNumber())    {
            case 2:
                getText().setText("This is the Boss's health bar, you must keep your raid alive until the Boss's health bar is depleted.");
                break;
            case 3:                         // When a special ability is coming, it will state what ability is coming and you will have 1.5 seconds to prepare for said ability.
                getText().setText("This is the announcement section. " +
                        "When a special ability is coming, it will state what ability is coming and you will have 1.5 seconds to prepare for said ability.");
                break;
            case 4:                 // These are your raid frames and each frame is an ally unit you needs to protect so watch them closely!
                getText().setText("These are your raid frames and each frame is an ally unit you needs to protect.\n" +
                        "There are three different types of ally units; Tanks, Healers, and DPS. \n");
                break;
            case 5:
                getText().setText("The Tank is the primary target for the boss and will normally take consistent damage. " +
                        "They have double the health compared to other ally units, 200 hp to be exact, and they do 5 damage per second.");
                break;
            case 6:
                getText().setText("These are Healers, they provide you with 2 mana per second and will deal 2 damage per second.");
                break;
            case 7:
                getText().setText("These are DPS, also known as the damage dealers. " +
                        "They are your primary source of damage, dealing 10 damage per second.");
                break;
            case 8:
                getText().setText("These are buffs and debuffs. Buffs are positive effects and debuffs are negative effect on your ally units. ");
                break;
            case 9:
                getText().setText("The red box around an ally unit indicates that this particular unit is being targeted by the boss.");
                break;
            case 10:
                getText().setText("In order to target an ally unit, you must touch the frame of the ally unit you want to select. The frame will turn light blue indicating the ally unit has been selected.");
                break;
            case 11:                                                // you can now use any of the spells in your spell bar.
                getText().setText("When an ally unit is selected, you can now use any of the spells in your spell bar. " +
                        // Just press the spell and the spell's ability will be triggered on the selected ally unit.
                        "Just press the spell and the spell's ability will be triggered on the selected ally unit. " +
                        // Each spell has it's own unique ability and should be should for certain situations. You are limited to 4 spells only.
                        "Each spell has it's own unique ability and should be used for certain situations. You are limited to only 4 spells.");
                break;
            case 12:
                                    // Spells can only be used if you have enough mana. This is your mana bar which contains 1000 mana as indicated.
                getText().setText("Spells can only be used if you have enough mana. " +
                        "This is your mana bar which contains 1000 mana as indicated.");
                break;
            case 13:                // Certain spells have to be casted before the ability is used. The cast bar shows the progression of the spell cast and when the cast is complete the ability will be triggered.
                getText().setText("Certain spells have to be casted before the ability is triggered. " +
                        "The cast bar shows the progression of the spell cast and when the cast is complete, the ability will be triggered.");
                break;
            case 14:
                getText().setText("These are essentials to becoming a great healer and you will learn more as you progress." +
                        "\nNow let's test your healing skills and start your journey…");
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
