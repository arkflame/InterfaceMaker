package dev._2lstudios.interfacemaker.listeners;

import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.placeholders.Formatter;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerCommandPreProcessListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerCommandPreProcessListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();

        if (message.startsWith("/") && message.length() > 1) {
            String alias = message.substring(message.indexOf("/") + 1);

            for (Entry<String, InterfaceMenu> entry : api.getConfiguredMenus().entrySet()) {
                InterfaceMenu inventory = entry.getValue();

                if (inventory.getCommands().contains(alias)) {
                    String menuName = entry.getKey();
                    Player player = event.getPlayer();

                    String openPermission = "interfacemaker.menu." + menuName;

                    if (player.hasPermission(openPermission)) {
                        inventory.build(player);
                        event.setCancelled(true);
                    } else {
                        Formatter.sendMessage(player, api.getConfig().getString("messages.no-permission-menu")
                                .replace("%menu%", menuName).replace("%permission%", openPermission));
                    }

                    break;
                }
            }
        }
    }
}
