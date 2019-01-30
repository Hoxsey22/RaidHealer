package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage1.GiantHornet;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage1.Golem;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage1.Nax;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage1.WildBoar;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage1.Wolf;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage2.Apprentice;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage2.Hogger;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage2.Proctor;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage2.Sorcerer;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage2.WampusCat;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.BloodQueen;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.DeathDragon;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.Hydra;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.MotherSpider;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.ZombieHorde;

/**
 * Created by Hoxsey on 7/30/2017.
 */

public class MapFrame extends Group {

    private Image bgFrame;
    private Image innerFrame;
    private Map map;
    private TextButton infoButton;
    private DebuffInfoFrame infoFrame;
    private ButtonGroup<BossIcon> bossIconList;
    //private ArrayList<BossIcon> bossIconsList;
    private Table table;
    private Text title;
    private Text body;
    private int page;
    private Player player;
    private Boss selectedBoss;
    private Assets assets;

    public MapFrame(Player player, int page, Assets assets)    {
        setName("map frame "+page);
        this.setPage(page);
        this.setAssets(assets);
        setTable(new Table());
        this.setPlayer(player);
        getTable().setName("lowerTable");
        setName("Map "+page);
        create();
    }

    private void create()    {
        setBgFrame(new Image(getAssets().getTexture(getAssets().mapOuterFrame)));
        getBgFrame().setName("bg");
        getBgFrame().setPosition(0,0);
        addActor(getBgFrame());

        bossIconList = new ButtonGroup<>();
        bossIconList.setMaxCheckCount(1);
        bossIconList.setMinCheckCount(0);
        bossIconList.setUncheckLast(true);

        setInfoFrame(new DebuffInfoFrame(getAssets()));

        setInfoButton(new TextButton("INFO", getAssets().getSkin(), "small_button"));
        createInfoButtonListener();

        setInnerFrame(new Image(getAssets().getTexture(getAssets().mapInnerFrame)));
        getInnerFrame().setName("inner frame");
        getInnerFrame().setPosition(getBgFrame().getX() + getBgFrame().getWidth()/2 - getInnerFrame().getWidth()/2,
                getBgFrame().getY() + getBgFrame().getHeight() - getInnerFrame().getHeight() - 20);
        addActor(getInnerFrame());

        //texture
        setMap(new Map(getPage(), getInnerFrame(), getAssets()));
        addActor(getMap());

        getTable().setBounds(getMap().getImage().getX(), getInnerFrame().getY()+13, getMap().getImage().getWidth(),
                getInnerFrame().getHeight()- getMap().getImage().getHeight() - 30);
        getTable().align(Align.topLeft);

        createFont();

        getTable().row();
        getTable().add(getInfoButton()).bottom().padBottom(10);
    }

    public void changePage(int page)    {
        this.setPage(page);
        getTitle().setText("");
        getBody().setText("");
        getMap().changeMap(page);
    }

    public void showInfoButton()    {
        getInfoButton().setVisible(true);
    }

    public void disableInfoButton() {
        getInfoButton().setVisible(false);
    }

    private void createInfoButtonListener() {
        getInfoButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                infoFrame.addInfo(selectedBoss);
                getParent().addActor(getInfoFrame());
                System.out.println("-----Info Frame hit");
            }
        });
    }

    private void createFont()    {

        setTitle(new Text("", 32, Color.YELLOW, false, getAssets()));
        getTitle().setWrap();
        getTitle().setAlignment(Align.center);


        getTable().add(getTitle().getLabel()).expandX().fillY().padBottom(5);
        getTable().row();

        body = new Text("",18, Color.WHITE, false, getAssets());
        body.setWrap();

        getTable().add(body.getLabel()).width(getTable().getWidth()).expandX().expandY().top().padLeft(5);

        addActor(getTable());

    }

    private void add(Boss boss) {
        boss.setDefeated(getPlayer().getLevel() > boss.getId());
        BossIcon bossIcon = new BossIcon(getAssets(),boss);
        getBossIconsList().add(bossIcon);
        getMap().add(bossIcon);
    }

    public void setTitle(String newTitle)  {
        getTitle().setText(newTitle);
    }

    public void setBody(String newBody)  {
        body.setFontSize(18);
        body.setText(newBody);

    }

    public Map getMap() {
        return map;
    }

    public void setBoss(int index)   {

        switch (index) {
            case 2:
                add(new WildBoar(getAssets()));
                break;
            case 5:
                add(new Wolf(getAssets()));
                break;
            case 3:
                add(new GiantHornet(getAssets()));
                break;
            case 4:
                add(new Golem(getAssets()));
                break;
            case 6:
                add(new Nax(getAssets()));
                break;
            case 7:
                add(new Hogger(getAssets()));
                break;
            case 8:
                add(new WampusCat(getAssets()));
                break;
            case 9:
                add(new Proctor(getAssets()));
                break;
            case 10:
                add(new Apprentice(getAssets()));
                break;
            case 11:
                add(new Sorcerer(getAssets()));
                break;
            case 12:
                add(new MotherSpider(getAssets()));
                break;
            case 13:
                add(new ZombieHorde(getAssets()));
                break;
            case 14:
                add(new BloodQueen(getAssets()));
                break;
            case 15:
                add(new Hydra(getAssets()));
                break;
            case 16:
                add(new DeathDragon(getAssets()));
                break;
        }
    }

    private void clearBossList() {

        //remove boss icons from their parent
        for(int i = 0; i < getBossIconsList().getButtons().size; i++)   {
            getBossIconsList().getButtons().get(i).remove();
        }
        getBossIconsList().clear();

    }

    @Override
    public boolean remove() {
        clearBossList();
        disableInfoButton();
        return super.remove();
    }

    public void addBossSelection(Boss boss)  {
        selectedBoss = boss;
    }

    private Image getBgFrame() {
        return bgFrame;
    }

    private void setBgFrame(Image bgFrame) {
        this.bgFrame = bgFrame;
    }

    public Image getInnerFrame() {
        return innerFrame;
    }

    private void setInnerFrame(Image innerFrame) {
        this.innerFrame = innerFrame;
    }

    private void setMap(Map map) {
        this.map = map;
    }

    private TextButton getInfoButton() {
        return infoButton;
    }

    private void setInfoButton(TextButton infoButton) {
        this.infoButton = infoButton;
    }

    public DebuffInfoFrame getInfoFrame() {
        return infoFrame;
    }

    private void setInfoFrame(DebuffInfoFrame infoFrame) {
        this.infoFrame = infoFrame;
    }

    public ButtonGroup<BossIcon> getBossIconsList() {
        return bossIconList;
    }

    public void setBossIconsList(ButtonGroup<BossIcon> bossIconsList) {
        this.bossIconList = bossIconsList;
    }

    private Table getTable() {
        return table;
    }

    private void setTable(Table table) {
        this.table = table;
    }

    private Text getTitle() {
        return title;
    }

    private void setTitle(Text title) {
        this.title = title;
    }

    public Text getBody() {
        return body;
    }

    private void setBody(Text body) {
        this.body = body;
    }

    public int getPage() {
        return page;
    }

    private void setPage(int page) {
        this.page = page;
    }

    private Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private Assets getAssets() {
        return assets;
    }

    private void setAssets(Assets assets) {
        this.assets = assets;
    }
}
