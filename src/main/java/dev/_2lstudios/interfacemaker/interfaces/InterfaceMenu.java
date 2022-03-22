package dev._2lstudios.interfacemaker.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceMenu {
    private InterfaceMakerAPI api = InterfaceMaker.getAPI();
    private Server server = Bukkit.getServer();
    private Map<Integer, InterfaceItem> items = new HashMap<>();
    private String title = "InterfaceMaker";
    private int size = 27;
    private boolean movement = false;
    private Collection<String> commands = new HashSet<>();
    private int autoRefresh = 0;
    private Collection<String> openActions = new HashSet<>();
    private Material openWithItemMaterial = null;
    private boolean openWithItemLeftClick = false;
    private boolean openWithItemRightClick = true;

    public InterfaceMenu setTitle(String title) {
        this.title = title;
        return this;
    }

    public InterfaceMenu setRows(int rows) {
        this.size = rows * 9;
        return this;
    }

    public void populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items) {
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

    public void populateItems(Player player, Inventory inventory) {
        populateItems(player, inventory, items);
    }

    public void build(Player player) {
        Inventory inventory = server.createInventory(player, size, Formatter.format(player, title));
        MenuBuildContext context = new MenuBuildContext(player, inventory);

        onBuild(context);
        populateItems(player, inventory);
        populateItems(player, inventory, context.getItems());

        if (player.getInventory() != inventory) {
            player.openInventory(inventory);
        }

        api.setOpened(inventory, this);
    }

    public InterfaceItem getItem(int slot) {
        return items.getOrDefault(slot, null);
    }

    public InterfaceMenu setItem(int slot, InterfaceItem item) {
        items.put(slot, item);
        return this;
    }

    public InterfaceMenu fill(int gap, InterfaceItem ...items) {
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

    public InterfaceMenu fillEmpty(InterfaceItem item) {
        for (int i = 0; i < size; i++) {
            if (!items.containsKey(i)) {
                items.put(i, item);
            }
        }
        return this;
    }

    public boolean allowsMovement() {
        return movement;
    }

    public InterfaceMenu setCommands(Collection<String> commands) {
        this.commands = commands;
        return this;
    }

    public InterfaceMenu setAutoRefresh(int autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    public InterfaceMenu setOpenActions(Collection<String> openActions) {
        this.openActions = openActions;
        return this;
    }

    public InterfaceMenu setOpenWithItem(Material openWithItemMaterial, boolean openWithItemLeftClick,
            boolean openWithItemRightClick) {
        this.openWithItemMaterial = openWithItemMaterial;
        this.openWithItemLeftClick = openWithItemLeftClick;
        this.openWithItemRightClick = openWithItemRightClick;
        return this;
    }

    public boolean isMovement() {
        return movement;
    }

    public InterfaceMenu setMovement(boolean movement) {
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

    public void onBuild(MenuBuildContext context) {
        // Overriden by super class
    }
}
