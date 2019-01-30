package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 7/20/2017.
 */

class LevelInfo extends Group {

    private Image frame;
    private Label bossName;
    private Label description;
    private Image exitButton;
    private boolean isActive;
    private Assets assets;
    private Table table;
    private Image startButton;
    private Boss boss;

    public LevelInfo(Assets assets)  {
        this.assets = assets;

        frame = new Image(assets.getTexture(assets.infoFrame));
        frame.setName("frame");
        frame.setPosition(0, RaidHealer.HEIGHT/2);

        exitButton = new Image(assets.getTexture(assets.exitButton));
        exitButton.setName("exit");
        exitButton.setPosition(frame.getX()+frame.getWidth() - exitButton.getWidth()-5, frame.getY() + frame.getHeight() -45);

        startButton = new Image(assets.getTexture(assets.startButton));
        startButton.setName("start");
        startButton.setPosition(frame.getX()+frame.getWidth()/2 - startButton.getWidth()/2, frame.getY()+10);

        isActive = false;
        table = new Table();
        table.setName("lowerTable");
        table.setBounds(frame.getX()+25, frame.getY()+20, frame.getWidth()-50, frame.getHeight()-60);
        table.align(Align.topLeft);
        createFont();

        addActor(frame);
        addActor(exitButton);
        addActor(table);
        addActor(startButton);
    }

    public void setInfo(BossIcon bossIcon)   {
        boss = bossIcon.getBoss();
        bossName.setText(bossIcon.getName());

        description.setText(bossIcon.getDescription());
    }

    public void displayInfo()   {
        isActive = true;
    }

    public void createFont()    {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = assets.getFont(assets.talentTooltipFont);

        bossName = new Label("test",textStyle);
        bossName.setFontScale(1.5f);
        bossName.setName("boss name");

        description = new Label("test", textStyle);
        description.setName("description");
        description.setFontScale(1f);
        description.setWrap(true);
        description.setWidth(table.getWidth());
        description.setAlignment(Align.topLeft);

        table.row();
        table.add(bossName).left();
        table.row();
        table.add(description).width(table.getWidth());

    }

    public boolean hit(float x, float y)   {
        Actor temp = this.hit(x,y, false);
        if(temp != null) {
            if (temp.getName().equalsIgnoreCase("exit")) {
                isActive = false;
                return false;
            }
            else if(temp.getName().equalsIgnoreCase("start"))   {
                return true;
            }
        }
        return false;
    }

    public Boss getBoss() {
        return boss;
    }

    public Image getFrame() {
        return frame;
    }

    public void setFrame(Image frame) {
        this.frame = frame;
    }

    public Label getBossName() {
        return bossName;
    }

    public void setBossName(Label bossName) {
        this.bossName = bossName;
    }

    public Label getDescription() {
        return description;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public Image getExitButton() {
        return exitButton;
    }

    public void setExitButton(Image exitButton) {
        this.exitButton = exitButton;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Image getStartButton() {
        return startButton;
    }

    public void setStartButton(Image startButton) {
        this.startButton = startButton;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isActive)    {

            frame.draw(batch, parentAlpha);
            exitButton.draw(batch, parentAlpha);
            startButton.draw(batch, parentAlpha);
            table.draw(batch,parentAlpha);
        }
    }
}
