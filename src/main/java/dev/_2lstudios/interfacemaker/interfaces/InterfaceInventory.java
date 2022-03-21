package dev._2lstudios.interfacemaker.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceInventory {
    private InterfaceMakerAPI api;
    private Server server;
    private Map<Integer, InterfaceItem> items = new HashMap<>();
    private String title = "InterfaceMaker";
    private int size = 24;
    private boolean movement = false;
    
    public InterfaceInventory(InterfaceMakerAPI api, Server server, Map<Integer, InterfaceItem> items, String title,
            int size, boolean movement) {
        this.api = api;
        this.server = server;
        this.items = items;
        this.title = title;
        this.size = size;
        this.movement = movement;
    }

    public void populateItems(Player player, Inventory inventory) {
        int inventorySize = inventory.getSize();

        for (Entry<Integer, InterfaceItem> entry : items.entrySet()) {
            int slot = entry.getKey();
            InterfaceItem interfaceItem = entry.getValue();
            ItemStack item = interfaceItem.build(player);

            if (slot < inventorySize) {
                inventory.setItem(slot, item);
            }
        }
    }

    public void build(Player player) {
        Inventory inventory = server.createInventory(player, size, title);

        populateItems(player, inventory);
        player.openInventory(inventory);
        api.setOpened(inventory, this);
    }

    public InterfaceItem getItem(int slot) {
        return items.getOrDefault(slot, null);
    }

    public void setItem(int slot, InterfaceItem item) {
        items.put(slot, item);
    }

    public boolean allowsMovement() {
        return movement;
    }
}
