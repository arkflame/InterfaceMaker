package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class InventoryCloseListener implements Listener {
    private InterfaceMakerAPI api;

    public InventoryCloseListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        api.setClosed(inventory);
    }
}
