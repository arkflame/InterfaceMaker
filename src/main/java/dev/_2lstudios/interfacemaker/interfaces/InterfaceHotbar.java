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

        for (Entry<Integer, InterfaceItem> entry : items.entrySet()) {
            int slot = entry.getKey();

            if (slot > -1) {
                InterfaceItem interfaceItem = entry.getValue();
                ItemStack item = interfaceItem.build(player);

                inventory.setItem(slot, item);
            }
        }

        api.setHotbar(player, this);
    }
}
