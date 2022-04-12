package dev._2lstudios.interfacemaker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class MenuCommand implements CommandExecutor {
    private InterfaceMakerAPI api;
    private Plugin plugin;
    private Configuration config;

    public MenuCommand(InterfaceMakerAPI api, Plugin plugin, Configuration config) {
        this.api = api;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            String fileName = args[1];
            String playerName = args[2];
            InterfaceMenu inventory = api.getConfiguredMenu(fileName);

            if (inventory != null) {
                Player player = plugin.getServer().getPlayer(playerName);

                if (player != null && player.isOnline()) {
                    inventory.build(player);
                    Formatter.sendMessage(sender, config.getString("messages.opened-menu-other")
                            .replace("%menu%", fileName).replace("%player%", playerName));
                } else {
                    Formatter.sendMessage(sender, config.getString("messages.offline"));
                }
            } else {
                Formatter.sendMessage(sender,
                        config.getString("messages.unexistant-menu").replace("%menu%", fileName));
            }
        } else if (args.length > 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String fileName = args[1];
                InterfaceMenu inventory = api.getConfiguredMenu(fileName);

                if (inventory != null) {
                    inventory.build(player);
                    Formatter.sendMessage(sender, config.getString("messages.opened-menu")
                            .replace("%menu%", fileName));
                } else {
                    Formatter.sendMessage(sender,
                        config.getString("messages.unexistant-menu").replace("%menu%", fileName));
                }
            } else {
                Formatter.sendMessage(sender,
                        config.getString("messages.no-console"));
            }
        } else {
            Formatter.sendMessage(sender,
            config.getString("messages.menu-usage").replace("%label%", label));
        }

        return true;
    }
}
