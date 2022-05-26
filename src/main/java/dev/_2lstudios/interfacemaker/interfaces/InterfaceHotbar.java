package dev._2lstudios.interfacemaker.interfaces;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.interfaces.contexts.HotbarBuildContext;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class InterfaceHotbar extends InterfaceInventoryHolder implements Buildable {
    private InterfaceMakerAPI api = InterfaceMaker.getAPI();
    private int autoRefresh = 0;
    private int giveDelay = 0;
    private boolean allowMovements = false;
    private boolean giveOnSpawn = false;
    private boolean dropOldItems = false;
    private boolean replaceOldItems = true;
    private boolean clearInventory = false;

    public InterfaceHotbar() {
        super(9, "Hotbar");
    }

    public InterfaceHotbar setItem(int slot, InterfaceItem item) {
        super.setItem(slot, item);
        return this;
    }

    public InterfaceHotbar populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items) {
        super.populateItems(player, inventory, items, dropOldItems, replaceOldItems);
        return this;
    }

    public InterfaceHotbar populateItems(Player player, Inventory inventory) {
        super.populateItems(player, inventory);
        return this;
    }

    public InterfaceHotbar setRows(int rows) {
        super.setRows(rows);
        return this;
    }

    public InterfaceHotbar fill(int gap, InterfaceItem... items) {
        super.fill(gap, items);
        return this;
    }

    public InterfaceHotbar fillEmpty(InterfaceItem item) {
        super.fillEmpty(item);
        return this;
    }

    public boolean allowsMovement() {
        return this.allowMovements;
    }

    public InterfaceHotbar build(Player player, Inventory inventory) {
        HotbarBuildContext context = new HotbarBuildContext(player, this);

        if (this.clearInventory) {
            inventory.clear();
        }

        context.setInventory(inventory);
        context.addItems(getItems());

        onBuild(context);

        context.populateItems(player, inventory);

        api.setHotbar(player, context);
        
        return this;
    }

    public InterfaceHotbar build(Player player) {
        return build(player, player.getInventory());
    }

    public InterfaceHotbar setGiveOnSpawn(boolean giveOnSpawn) {
        this.giveOnSpawn = giveOnSpawn;
        return this;
    }

    public boolean giveOnSpawn() {
        return giveOnSpawn;
    }

    public InterfaceHotbar setDropOldItems(boolean dropOldItems) {
        this.dropOldItems = dropOldItems;
        return this;
    }

    public boolean dropOldItems() {
        return dropOldItems;
    }

    public InterfaceHotbar setReplaceOldItems(boolean replaceOldItems) {
        this.replaceOldItems = replaceOldItems;
        return this;
    }

    public boolean replaceOldItems() {
        return replaceOldItems;
    }

    public InterfaceHotbar setAutoRefresh(int autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    public int getAutoRefresh() {
        return autoRefresh;
    }

    public void setClearInventory(boolean clearInventory) {
        this.clearInventory = clearInventory;
    }

    public void setAllowsMovement(boolean allowMovements) {
        this.allowMovements = allowMovements;
    }

    public void setGiveDelay(int giveDelay) {
        this.giveDelay = giveDelay;
    }

    public int getGiveDelay() {
        return giveDelay;
    }

    public InterfaceHotbar buildLater(Player player, int giveDelay) {
        api.queueBuild(player, this, giveDelay);
        return this;
    }

    public void onBuild(HotbarBuildContext context) {
        // Overriden by super class
    }
}
