package dev._2lstudios.interfacemaker.commands;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class ListCommand implements CommandExecutor {
    private InterfaceMakerAPI api;
    private Configuration config;

    public ListCommand(InterfaceMakerAPI api, Configuration config) {
        this.api = api;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            String arg1 = args[1].toLowerCase();

            if (arg1.startsWith("m")) {
                Map<String, InterfaceMenu> menus = api.getConfiguredMenus();

                if (menus.isEmpty()) {
                    Formatter.sendMessage(sender,
                            config.getString("messages.no-menus"));
                } else {
                    Formatter.sendMessage(sender,
                            config.getString("messages.menu-list-header"));

                    for (Entry<String, InterfaceMenu> entry : menus.entrySet()) {
                        Formatter.sendMessage(sender,
                                config.getString("messages.menu-list-entry").replace("%entry%", entry.getKey()));
                    }
                }
            } else if (arg1.startsWith("h")) {
                Map<String, InterfaceHotbar> hotbars = api.getConfiguredHotbars();

                if (hotbars.isEmpty()) {
                    Formatter.sendMessage(sender,
                            config.getString("messages.no-hotbars"));
                } else {
                    Formatter.sendMessage(sender,
                            config.getString("messages.hotbar-list-header"));

                    for (Entry<String, InterfaceHotbar> entry : hotbars.entrySet()) {
                        Formatter.sendMessage(sender,
                                config.getString("messages.hotbar-list-entry").replace("%entry%", entry.getKey()));
                    }
                }
            } else {
                Formatter.sendMessage(sender,
                        config.getString("messages.list-invalid-argument").replace("%argument%", arg1));
            }
        } else {
            Formatter.sendMessage(sender,
                    config.getString("messages.list-usage").replace("%label%", label));
        }

        return true;
    }
}
