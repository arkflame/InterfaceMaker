package dev._2lstudios.interfacemaker.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceInventory {
    private InterfaceMakerAPI api;
    private Server server;
    private Map<Integer, InterfaceItem> items = new HashMap<>();
    private String title = "InterfaceMaker";
    private int size = 24;
    private boolean movement = false;
    
    public InterfaceInventory(InterfaceMakerAPI api, Server server) {
        this.api = api;
        this.server = server;
    }

    public InterfaceInventory setTitle(String title) {
        this.title = title;
        return this;
    }

    public InterfaceInventory setSize(int rows) {
        this.size = rows * 9;
        return this;
    }

    public InterfaceInventory setAllowsMovement(boolean movement) {
        this.movement = movement;
        return this;
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
        Inventory inventory = server.createInventory(player, size, Formatter.format(player, title));

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
