package dev._2lstudios.interfacemaker.interfaces;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MenuBuildContext {
    private Player player;
    private Inventory inventory;
    private Map<Integer, InterfaceItem> items = new HashMap<>();

    public MenuBuildContext(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, InterfaceItem> getItems() {
        return items;
    }

    public InterfaceItem getItem(int slot) {
        return items.getOrDefault(slot, null);
    }

    public MenuBuildContext setItem(int slot, InterfaceItem item) {
        items.put(slot, item);
        return this;
    }

    public MenuBuildContext fill(int gap, InterfaceItem ...items) {
        int size = inventory.getSize();
        int firstSlot = gap * 8 + gap * 2;
        int itemIndex = 1;

        for (int slot = firstSlot; slot < size; slot++) {
            if (itemIndex - 1 >= items.length) {
                break;
            }

            setItem(slot, items[itemIndex - 1]);

            if (itemIndex % (9 - gap * 2) == 0) {
                slot += (gap * 2);
            }

            itemIndex++;
        }
        return this;
    }

    public MenuBuildContext fillEmpty(InterfaceItem item) {
        int size = inventory.getSize();

        for (int i = 0; i < size; i++) {
            if (!items.containsKey(i)) {
                items.put(i, item);
            }
        }
        return this;
    }
}
