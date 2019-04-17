package com.hoxseygames.raidhealer.encounters.player.bars;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.RaidHealer;
import com.hoxseygames.raidhealer.encounters.spells.Items.GodsGraceItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.GreaterManaPotionItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Item;
import com.hoxseygames.raidhealer.encounters.spells.Items.LesserManaPotionItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.ManaPotionItem;
import com.hoxseygames.raidhealer.encounters.spells.Items.RunBackItem;

import java.util.ArrayList;

public class ItemBar extends Group {

    private Player owner;
    private Assets assets;
    private final int MAX_ITEM_NUMBER = 4;
    private final int PAD_RIGHT = 48;
    private final int PAD_BOTTOM = 1;
    private final int ICON_SIZE = 60;
    private final int TABLE_X = 48;


    private Image itemBarBGImage;

    // empty item Icons
    private Table emptyItemTable;
    private ArrayList<Image> emptyItemsIcons;
    private Texture emptyItemIconTexture;

    private Table itemTable;
    private ArrayList<Item> items;

    public ItemBar(Player player)    {
        owner = player;
        assets = player.getAssets();
        init();
        create();
    }

    private void init() {
        itemBarBGImage = new Image(assets.getTexture(assets.itemBar));
        itemBarBGImage.setHeight(73);

        emptyItemTable = new Table();

        emptyItemsIcons = new ArrayList<>();

        emptyItemIconTexture = assets.getTexture(assets.blankIcon);

        itemTable = new Table();

        items = new ArrayList<>();
    }

    public void create()    {
        addActor(itemBarBGImage);

        createEmptyItemSlotDisplay();

        createItemDisplay();
    }

    private void createEmptyItemSlotDisplay()   {
        emptyItemTable.setBounds(TABLE_X, 0, RaidHealer.WIDTH, itemBarBGImage.getHeight());
        emptyItemTable.align(Align.left);
        addActor(emptyItemTable);

        for(int i = 0; i < MAX_ITEM_NUMBER; i++) {
            Image blankIcon = new Image(emptyItemIconTexture);
            emptyItemsIcons.add(blankIcon);
            emptyItemTable.add(emptyItemsIcons.get(i)).padRight(PAD_RIGHT).padBottom(PAD_BOTTOM).height(ICON_SIZE).width(ICON_SIZE);
        }
    }

    private void createItemDisplay()    {
        itemTable.setBounds(TABLE_X, 0, RaidHealer.WIDTH, itemBarBGImage.getHeight());
        itemTable.align(Align.left);
        addActor(itemTable);
    }

    /**
     * Attempts to index the new Item into the ItemBar.
     * @param newItem
     * @return The index of the item slot in which the new Item was placed. If -1, then the item
     * didn't collide with any of the item slots.
     */
    public int insertItem(Item newItem)   {
        int itemDropPosition = findItemSlotIndex(newItem);
        if(itemDropPosition != -1) {
            if(items.contains(newItem))    {
                int indexOfDuplicatedItem = items.indexOf(newItem);
                swapItemPosition(itemDropPosition, indexOfDuplicatedItem);
            } else if (items.size() == MAX_ITEM_NUMBER) {
                itemTable.getCells().get(itemDropPosition).setActor(newItem);
                items.set(itemDropPosition, newItem);
            } else {
                addItemToTable(newItem);
                itemDropPosition = items.size();
                addItem(newItem);
            }
        }
        return itemDropPosition;
    }

    private void addItemToTable(Item itemToBeAdded)   {
        itemTable.add(itemToBeAdded).padRight(PAD_RIGHT).padBottom(PAD_BOTTOM).height(ICON_SIZE).width(ICON_SIZE);;
    }

    private void addItemToTable(Item itemToBeAdded, int position)   {
        itemTable.getCells().get(position).setActor(itemToBeAdded).padRight(PAD_RIGHT).padBottom(PAD_BOTTOM).height(ICON_SIZE).width(ICON_SIZE);;
    }

    private void addItem(Item newItem)  {
        items.add(newItem);
    }

    private void swapItemPosition(int pos1, int pos2)  {
        Item item1 = items.get(pos1);
        Item item2 = items.get(pos2);

        items.set(pos1, item2);
        items.set(pos2, item1);
    }

    public void clearItems()    {
        for(int i = 0; i < items.size(); i++)   {
            itemTable.removeActor(items.get(i));
            items.remove(i);
        }
    }

    /**
     * Finds a position for the new Item in the ItemBar.
     * @param newItem
     * @return The index of the item slot in which the new Item will be placed. If -1, then the item
     * didn't collide with any of the item slots.
     */
    private int findItemSlotIndex(Item newItem)   {
        int bestPosition = -1;
        float bestPositionResult = -1;

        Rectangle newItemBounds = new Rectangle(newItem.getX(), newItem.getY(), newItem.getWidth(), newItem.getHeight());
        for(int i = 0; i < items.size(); i++)   {
            Rectangle currentItemBounds = new Rectangle(items.get(i).getX(),items.get(i).getY(),items.get(i).getWidth(),items.get(i).getHeight());

            if(newItemBounds.overlaps(currentItemBounds))    {
                if(bestPosition == -1)    {
                    bestPosition = i;
                    bestPositionResult = getDistanceBetweenRectangles(currentItemBounds, newItemBounds);
                }
                else {
                    if(bestPositionResult > getDistanceBetweenRectangles(currentItemBounds, newItemBounds))    {
                        bestPosition = i;
                        bestPositionResult = getDistanceBetweenRectangles(currentItemBounds, newItemBounds);
                    }
                }
            }
        }
        return bestPosition;
    }

    private void removeItem(Item itemToRemove)    {
        itemTable.removeActor(itemToRemove);
        items.remove(itemToRemove);
    }

    public void setupDebug()    {
        items.add(new ManaPotionItem(owner));

        items.add(new LesserManaPotionItem(owner));
        items.add(new GreaterManaPotionItem(owner));
        items.add(new RunBackItem(owner));
        items.add(new GodsGraceItem(owner));

        for(int i = 0; i < items.size(); i++)   {
            addItemToTable(items.get(i));
        }
    }

    private float getDistanceBetweenRectangles(Rectangle r1, Rectangle r2) {
        Vector2 r1Center = r1.getCenter(new Vector2());
        Vector2 r2Center = r2.getCenter(new Vector2());

        return Math.abs(r1Center.y - r2Center.y) + Math.abs(r1Center.x - r2Center.x);
    }

    public Table getEmptyItemTable()    {
        return emptyItemTable;
    }

    public boolean isEmpty()    {
        if(items.size() < 1)    {
            return true;
        }
        return false;
    }

}
