package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerJoinListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerJoinListener(InterfaceMakerAPI api) {
        this.api = api;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (InterfaceHotbar hotbar : api.getConfiguredHotbarsValues()) {
            if (hotbar.giveOnSpawn()) {
                Player player = event.getPlayer();
                
                hotbar.build(player);
                break;
            }
        }
    }
}
