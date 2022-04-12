package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerCommandPreProcessListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerCommandPreProcessListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();

        for (InterfaceMenu inventory : api.getConfiguredMenusValues()) {
            if (inventory.getCommands().contains(message)) {
                Player player = event.getPlayer();

                inventory.build(player);
                event.setCancelled(true);
                break;
            }
        }
    }
}
