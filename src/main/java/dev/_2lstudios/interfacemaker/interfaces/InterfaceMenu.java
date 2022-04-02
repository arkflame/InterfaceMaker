package dev._2lstudios.interfacemaker.interfaces;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.interfaces.contexts.MenuBuildContext;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceMenu extends InterfaceInventoryHolder {
    private InterfaceMakerAPI api = InterfaceMaker.getAPI();
    private Server server = Bukkit.getServer();
    private String title = "InterfaceMaker";
    private boolean movement = false;
    private Collection<String> commands = new HashSet<>();
    private int autoRefresh = 0;
    private Collection<String> openActions = new HashSet<>();
    private Material openWithItemMaterial = null;
    private boolean openWithItemLeftClick = false;
    private boolean openWithItemRightClick = true;

    public InterfaceMenu() {
        super(27);
    }

    public InterfaceMenu setItem(int slot, InterfaceItem item) {
        super.setItem(slot, item);
        return this;
    }

    public InterfaceMenu populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items) {
        super.populateItems(player, inventory, items);
        return this;
    }

    public InterfaceMenu populateItems(Player player, Inventory inventory) {
        super.populateItems(player, inventory);
        return this;
    }

    public InterfaceMenu setRows(int rows) {
        super.setRows(rows);
        return this;
    }

    public InterfaceMenu fill(int gap, InterfaceItem... items) {
        super.fill(gap, items);
        return this;
    }

    public InterfaceMenu fillEmpty(InterfaceItem item) {
        super.fillEmpty(item);
        return this;
    }

    public InterfaceMenu setTitle(String title) {
        this.title = title;
        return this;
    }

    public InterfaceMenu build(Player player) {
        int size = getSize();
        MenuBuildContext context = new MenuBuildContext(player, this, size);
        Inventory inventory = server.createInventory(context, size, Formatter.format(player, title));
        context.setInventory(inventory);

        context.addItems(getItems());
        onBuild(context);
        context.populateItems(player, inventory);

        if (player.getInventory() != inventory) {
            player.closeInventory();
            player.openInventory(inventory);
        }

        api.setOpened(inventory, this);
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
