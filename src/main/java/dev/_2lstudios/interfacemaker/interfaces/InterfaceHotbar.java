package dev._2lstudios.interfacemaker.interfaces;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.interfaces.contexts.HotbarBuildContext;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class InterfaceHotbar extends InterfaceInventoryHolder {
    private InterfaceMakerAPI api = InterfaceMaker.getAPI();
    private int autoRefresh = 0;
    private boolean giveOnSpawn = false;

    public InterfaceHotbar() {
        super(9);
    }

    public InterfaceHotbar setItem(int slot, InterfaceItem item) {
        super.setItem(slot, item);
        return this;
    }

    public InterfaceHotbar populateItems(Player player, Inventory inventory, Map<Integer, InterfaceItem> items) {
        super.populateItems(player, inventory, items);
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
        return false;
    }

    public void build(Player player) {
        Inventory inventory = player.getInventory();

        inventory.clear();

        for (Entry<Integer, InterfaceItem> entry : getItems().entrySet()) {
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

    public void onBuild(HotbarBuildContext context) {
        // Overriden by super class
    }
}
