package com.hoxseygames.raidhealer.encounters.spells.Items;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Item;

import java.util.ArrayList;

public class Inventory {

    private Player parent;
    private Assets assets;
    private ArrayList<Item> inventoryItems;
    private ArrayList<Item> encounterItems;



    public Inventory(Player parent)  {
        this.parent = parent;
        assets = this.parent.getAssets();
        inventoryItems = new ArrayList<>();
        encounterItems = new ArrayList<>();
    }

    public Item addItem(Item newAddedItem)   {
        inventoryItems.add(newAddedItem);
        return newAddedItem;
    }

    public Item addItemToEncounterSlot(Item newAddedItem)    {
        encounterItems.add(newAddedItem);
        return newAddedItem;
    }

    public Item removeItemFromInventory(Item itemRemoved)   {
        inventoryItems.remove(itemRemoved);
        return itemRemoved;
    }

    public Item removeItemFromEncounterSlot(Item itemRemoved)   {
        encounterItems.remove(itemRemoved);
        return itemRemoved;
    }

    public void swapItemsInEncounter(Item item1, Item item2)  {
        int indexOfItem1 = encounterItems.indexOf(item1);
        int indexOfItem2 = encounterItems.indexOf(item2);

        encounterItems.set(indexOfItem1, item2);
        encounterItems.set(indexOfItem2, item1);
    }




}
