package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerQuitListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerQuitListener(InterfaceMakerAPI api) {
        this.api = api;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        api.getInterfacePlayerManager().remove(player.getUniqueId());
    }
}
