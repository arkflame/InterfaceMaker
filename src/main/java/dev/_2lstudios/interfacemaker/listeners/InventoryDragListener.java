package dev._2lstudios.interfacemaker.listeners;

import java.util.Collection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import dev._2lstudios.interfacemaker.interfaces.InterfaceInventory;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class InventoryDragListener implements Listener {
    private InterfaceMakerAPI api;

    public InventoryDragListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryView view = event.getView();
        Inventory topInventory = view.getTopInventory();
        InterfaceInventory interfaceInventory = api.getOpenedInventory(topInventory);

        if (interfaceInventory != null && interfaceInventory.allowsMovement()) {
            Collection<Integer> slots = event.getInventorySlots();

            for (int slot : slots) {
                InterfaceItem interfaceItem = interfaceInventory.getItem(slot);

                if (interfaceItem != null) {
                    if (!interfaceItem.allowsMovement()) {
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            event.setCancelled(true);
        }
    }
}
