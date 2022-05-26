package dev._2lstudios.interfacemaker.interfaces.holders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;

public class InterfaceItemHolder {
    private Map<Integer, InterfaceItem> items = new HashMap<>();

    public Map<Integer, InterfaceItem> getItems() {
        return items;
    }

    public InterfaceItem getItem(int slot) {
        return items.getOrDefault(slot, null);
    }

    public void addItems(Map<Integer, InterfaceItem> items) {
        this.items.putAll(items);
    }

    public InterfaceItemHolder setItem(int slot, InterfaceItem item) {
        items.put(slot, item);
        return this;
    }

    public boolean hasItem(int slot) {
        return items.containsKey(slot);
    }

    public InterfaceItemHolder populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items,
            boolean dropOldItems, boolean replaceOldItems) {
        Location location = player.getLocation();
        World world = location.getWorld();
        int inventorySize = inventory.getSize();

        for (Entry<Integer, InterfaceItem> entry : items.entrySet()) {
            int slot = entry.getKey();
            InterfaceItem interfaceItem = entry.getValue();

            if (interfaceItem != null) {
                String viewPermission = interfaceItem.getViewPermission();

                if (viewPermission != null && !player.hasPermission(viewPermission)) {
                    continue;
                }

                ItemStack item = interfaceItem.build(player);

                if (slot < inventorySize) {
                    try {
                        ItemStack oldItem = inventory.getItem(slot);

                        if (dropOldItems && oldItem != null && oldItem.getType() != Material.AIR) {
                            world.dropItem(location, item);
                        }

                        if (replaceOldItems || oldItem != null) {
                            inventory.setItem(slot, item);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        // Ignored
                    }
                }
            }
        }

        return this;
    }

    public InterfaceItemHolder populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items,
            boolean dropOldItems) {
        return populateItems(player, inventory, items, dropOldItems, true);
    }

    public InterfaceItemHolder populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items) {
        return populateItems(player, inventory, items, false);
    }

    public InterfaceItemHolder populateItems(Player player, Inventory inventory) {
        populateItems(player, inventory, items);
        return this;
    }
}
