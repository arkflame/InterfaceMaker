package dev._2lstudios.interfacemaker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class ReloadCommand implements CommandExecutor {
    private InterfaceMaker plugin;
    private Configuration config;

    public ReloadCommand(InterfaceMaker plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String permission = "interfacemaker.reload";

        if (!sender.hasPermission(permission)) {
            Formatter.sendMessage(sender, 
                config.getString("messages.no-permission")
                    .replace("%permission%", permission)
            );
        } else {
            plugin.reloadFiles();
            Formatter.sendMessage(sender, config.getString("messages.reload"));
        }
        
        return true;
    }
}
