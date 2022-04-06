package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.interfaces.contexts.MenuBuildContext;

public class InventoryClickListener implements Listener {
    private InterfaceMakerAPI api;

    public InventoryClickListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            InventoryView view = event.getView();
            Inventory clickedInventory = event.getClickedInventory();

            if (clickedInventory != null) {
                Inventory bottomInventory = view.getBottomInventory();
                int slot = event.getSlot();

                if (clickedInventory == bottomInventory) {
                    InterfaceHotbar interfaceHotbar = api.getHotbar(player);

                    if (interfaceHotbar != null) {
                        InterfaceItem interfaceItem = interfaceHotbar.getItem(slot);

                        if (interfaceItem != null) {
                            if (!interfaceHotbar.allowsMovement() || !interfaceItem.allowsMovement()) {
                                event.setCancelled(true);
                                return;
                            }

                            interfaceItem.onClick(player, clickedInventory);
                        }
                    }
                } else {
                    InventoryHolder inventoryHolder = clickedInventory.getHolder();

                    if (inventoryHolder instanceof MenuBuildContext) {
                        MenuBuildContext menuBuildContext = (MenuBuildContext) inventoryHolder;

                        if (menuBuildContext != null) {
                            if (!menuBuildContext.getMenu().allowsMovement()) {
                                event.setCancelled(true);
                            }

                            InterfaceItem interfaceItem = menuBuildContext.getItem(slot);

                            if (interfaceItem != null) {
                                if (!interfaceItem.allowsMovement()) {
                                    event.setCancelled(true);
                                }

                                interfaceItem.runActions(api, player);
                                interfaceItem.onClick(player, clickedInventory);
                            }
                        }
                    }
                }
            }
        }
    }
}
