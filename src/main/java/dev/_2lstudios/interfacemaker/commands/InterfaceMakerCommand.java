package dev._2lstudios.interfacemaker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceMakerCommand implements CommandExecutor {
    private InterfaceMakerAPI api;
    private InterfaceMaker plugin;

    public InterfaceMakerCommand(InterfaceMakerAPI api, InterfaceMaker plugin) {
        this.api = api;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Configuration config = api.getConfig();

        if (args.length > 0) {
            String arg0 = args[0].toLowerCase();

            if (arg0.equals("reload")) {
                new ReloadCommand(plugin, config).onCommand(sender, command, label, args);
            } else if (arg0.equals("menu")) {
                new MenuCommand(api, plugin, config).onCommand(sender, command, label, args);
            } else if (arg0.equals("hotbar")) {
                new HotbarCommand(api, plugin, config).onCommand(sender, command, label, args);
            } else if (arg0.equals("list")) {
                new ListCommand(api, config).onCommand(sender, command, label, args);
            } else {
                Formatter.sendMessage(sender, config.getString("messages.no-subcommand").replace("%argument%", arg0));
            }
        } else {
            Formatter.sendMessage(sender, config.getString("messages.interfacemaker-usage"));
        }

        return true;
    }
}
