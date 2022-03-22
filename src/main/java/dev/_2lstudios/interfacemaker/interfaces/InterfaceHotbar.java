package dev._2lstudios.interfacemaker.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceHotbar {
    private InterfaceMakerAPI api;
    private Map<Integer, InterfaceItem> items = new HashMap<>();
    private int autoRefresh = 0;
    private boolean giveOnSpawn = false;

    public InterfaceHotbar(InterfaceMakerAPI api) {
        this.api = api;
    }

    public InterfaceItem getItem(int slot) {
        return items.getOrDefault(slot, null);
    }

    public boolean allowsMovement() {
        return false;
    }

    public void setItem(int slot, InterfaceItem item) {
        items.put(slot, item);
    }

    public void build(Player player) {
        Inventory inventory = player.getInventory();

        inventory.clear();

        for (Entry<Integer, InterfaceItem> entry : items.entrySet()) {
            int slot = entry.getKey();

            if (slot > -1 && slot < inventory.getSize()) {
                InterfaceItem interfaceItem = entry.getValue();
                ItemStack item = interfaceItem.build(player);

                try {
                    inventory.setItem(slot, item);
                } catch (IndexOutOfBoundsException ex) {
                    // Ignored
                }
            }
        }

        api.setHotbar(player, this);
    }

    public InterfaceHotbar fillEmpty(InterfaceItem item) {
        for (int i = 0; i < 9; i++) {
            if (!items.containsKey(i)) {
                items.put(i, item);
            }
        }
        return this;
    }

    public InterfaceHotbar setGiveOnSpawn(boolean giveOnSpawn) {
        this.giveOnSpawn = giveOnSpawn;
        return this;
    }

    public boolean giveOnSpawn() {
        return giveOnSpawn;
    }

    public InterfaceHotbar setAutoRefresh(int autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    public int getAutoRefresh() {
        return autoRefresh;
    }
}
