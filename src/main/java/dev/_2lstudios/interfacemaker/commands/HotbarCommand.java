package dev._2lstudios.interfacemaker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class HotbarCommand implements CommandExecutor {
    private InterfaceMakerAPI api;
    private Plugin plugin;
    private Configuration config;

    public HotbarCommand(InterfaceMakerAPI api, Plugin plugin, Configuration config) {
        this.api = api;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            String fileName = args[1];
            String playerName = args[2];
            InterfaceHotbar hotbar = api.getConfiguredHotbar(fileName);

            if (hotbar != null) {
                Player player = plugin.getServer().getPlayer(playerName);

                if (player != null && player.isOnline()) {
                    hotbar.build(player);
                    Formatter.sendMessage(sender, config.getString("messages.opened-hotbar-other")
                            .replace("%hotbar%", fileName).replace("%player%", playerName));
                } else {
                    Formatter.sendMessage(sender, config.getString("messages.offline"));
                }
            } else {
                Formatter.sendMessage(sender,
                        config.getString("messages.unexistant-hotbar").replace("%hotbar%", fileName));
            }
        } else if (args.length > 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String fileName = args[1];
                InterfaceHotbar hotbar = api.getConfiguredHotbar(fileName);

                if (hotbar != null) {
                    hotbar.build(player);
                    Formatter.sendMessage(sender, config.getString("messages.opened-hotbar")
                            .replace("%hotbar%", fileName));
                } else {
                    Formatter.sendMessage(sender,
                            config.getString("messages.unexistant-hotbar").replace("%hotbar%", fileName));
                }
            } else {
                Formatter.sendMessage(sender,
                        config.getString("messages.no-console"));
            }
        } else {
            Formatter.sendMessage(sender,
                    config.getString("messages.hotbar-usage").replace("%label%", label));
        }

        return true;
    }
}
