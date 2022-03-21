package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerInteractListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerInteractListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        InterfaceHotbar interfaceHotbar = api.getHotbar(player);

        if (interfaceHotbar != null) {
            PlayerInventory playerInventory = player.getInventory();
            int slot = playerInventory.getHeldItemSlot();
            InterfaceItem interfaceItem = interfaceHotbar.getItem(slot);

            if (interfaceItem != null) {
                if (!interfaceItem.allowsInteraction()) {
                    event.setCancelled(true);
                }

                interfaceItem.onInteract(player);
            }
        }
    }
}
