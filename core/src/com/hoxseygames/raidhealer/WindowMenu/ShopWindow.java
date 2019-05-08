package com.hoxseygames.raidhealer.WindowMenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hoxseygames.raidhealer.Player;

import java.util.ArrayList;

public abstract class ShopWindow extends WindowMenuPopup {

    protected PurchasePlate currentPurchasePlate;
    protected ItemListingManager itemListingManager;
    protected ArrayList<PurchasePlate> plates;
    protected ImageTextButton silverButton;
    protected ImageTextButton goldButton;
    protected TextButton cashButton;
    private ImageButton leftButton;
    private ImageButton rightButton;
    protected int index;

    public ShopWindow(Player player, String name) {
        super(player, name);
        create();
    }

    protected void create() {
        super.create();
        index = 0;

        itemListingManager = new ItemListingManager(player);
        loadListings();

        plates = new ArrayList<>();

        currentPurchasePlate = loadListings();

        body.add(currentPurchasePlate).colspan(2);
        body.row();

        loadPurchaseButtons();

        leftButton = new ImageButton(assets.getSkin(), "page_left");
        leftButton.setPosition(body.getX()+leftButton.getWidth()/2 -10,
                bgImage.getY()+bgImage.getHeight()/2);
        addActor(leftButton);

        rightButton = new ImageButton(assets.getSkin(), "page_right");
        rightButton.setPosition(body.getX()+body.getWidth()-rightButton.getWidth()/2-40,
                bgImage.getY()+bgImage.getHeight()/2);
        addActor(rightButton);

        setupNavigationButtons();
    }

    /**
     * Loading in all the item in which the current shop will display.
     *
     * @return the first plate to be displayed
     */
    protected abstract PurchasePlate loadListings();

    protected abstract void loadPurchaseButtons();

    protected abstract void updatePurchaseButtons();

    private void setCurrentPurchasePlate()  {
        Cell cell = body.getCell(currentPurchasePlate);

        currentPurchasePlate = itemListingManager.convertToPlate(index, this);
        cell.setActor(currentPurchasePlate);

        updatePurchaseButtons();
    }

    public void nextPlate() {
        index++;
        setCurrentPurchasePlate();
    }

    public void previousPlate() {
        if(index > 0) {
            index--;
            setCurrentPurchasePlate();
        }
    }

    private void setupNavigationButtons()   {
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                previousPlate();

            }
        });

        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                nextPlate();
            }
        });
    }



}
