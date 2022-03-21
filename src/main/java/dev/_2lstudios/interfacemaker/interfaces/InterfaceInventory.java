package dev._2lstudios.interfacemaker.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
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
    private Collection<String> commands = new HashSet<>();
    private int autoRefresh = 0;
    private Collection<String> openActions = new HashSet<>();
    private Material openWithItemMaterial = null;
    private boolean openWithItemLeftClick = false;
    private boolean openWithItemRightClick = true;

    public InterfaceInventory(InterfaceMakerAPI api, Server server) {
        this.api = api;
        this.server = server;
    }

    public InterfaceInventory setTitle(String title) {
        this.title = title;
        return this;
    }

    public InterfaceInventory setRows(int rows) {
        this.size = rows * 9;
        return this;
    }

    public void populateItems(Player player, Inventory inventory) {
        int inventorySize = inventory.getSize();

        for (Entry<Integer, InterfaceItem> entry : items.entrySet()) {
            int slot = entry.getKey();
            InterfaceItem interfaceItem = entry.getValue();
            ItemStack item = interfaceItem.build(player);

            if (slot < inventorySize) {
                try {
                    inventory.setItem(slot, item);
                } catch (IndexOutOfBoundsException ex) {
                    // Ignored
                }
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

    public InterfaceInventory setCommands(Collection<String> commands) {
        this.commands = commands;
        return this;
    }

    public InterfaceInventory setAutoRefresh(int autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    public InterfaceInventory setOpenActions(Collection<String> openActions) {
        this.openActions = openActions;
        return this;
    }

    public InterfaceInventory setOpenWithItem(Material openWithItemMaterial, boolean openWithItemLeftClick,
            boolean openWithItemRightClick) {
        this.openWithItemMaterial = openWithItemMaterial;
        this.openWithItemLeftClick = openWithItemLeftClick;
        this.openWithItemRightClick = openWithItemRightClick;
        return this;
    }

    public boolean isMovement() {
        return movement;
    }

    public InterfaceInventory setMovement(boolean movement) {
        this.movement = movement;
        return this;
    }

    public Collection<String> getCommands() {
        return commands;
    }

    public int getAutoRefresh() {
        return autoRefresh;
    }

    public Collection<String> getOpenActions() {
        return openActions;
    }

    public Material getOpenWithItemMaterial() {
        return openWithItemMaterial;
    }

    public boolean isOpenWithItemLeftClick() {
        return openWithItemLeftClick;
    }

    public boolean isOpenWithItemRightClick() {
        return openWithItemRightClick;
    }
}
