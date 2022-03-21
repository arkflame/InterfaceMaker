package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerDropItemListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerDropItemListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        int slot = player.getInventory().getHeldItemSlot();
        InterfaceHotbar interfaceHotbar = api.getHotbar(player);

        if (interfaceHotbar != null) {
            if (!interfaceHotbar.allowsMovement()) {
                event.setCancelled(true);
            } else {
                InterfaceItem interfaceItem = interfaceHotbar.getItem(slot);

                if (interfaceItem != null) {
                    if (!interfaceItem.allowsMovement()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
