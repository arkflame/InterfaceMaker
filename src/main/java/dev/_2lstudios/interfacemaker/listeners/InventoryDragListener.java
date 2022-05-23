package dev._2lstudios.interfacemaker.listeners;

import java.util.Collection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
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
        InterfaceMenu interfaceMenu = api.getOpenedMenu(topInventory);

        if (interfaceMenu != null) {
            if (interfaceMenu.allowsMovement()) {
                Collection<Integer> slots = event.getInventorySlots();

                for (int slot : slots) {
                    InterfaceItem interfaceItem = interfaceMenu.getItem(slot);

                    if (interfaceItem != null) {
                        if (!interfaceItem.allowsMovement()) {
                            cancelEvent(event);
                        }
                    }
                }
            } else {
                cancelEvent(event);
            }
        }
    }

    private void cancelEvent(InventoryDragEvent event) {
        event.setResult(Result.DENY);
        event.setCancelled(true);
    }
}
