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
        if (args.length <= 1) {
            Formatter.sendMessage(sender,
                    config.getString("messages.menu-usage").replace("%label%", label));
        } else {
            String menuName = args[1];
            InterfaceMenu menu = api.getConfiguredMenu(menuName);

            if (menu == null) {
                Formatter.sendMessage(sender,
                        config.getString("messages.unexistant-menu").replace("%menu%", menuName));
            } else {
                Player target = null;

                if (args.length <= 2 && !(sender instanceof Player)) {
                    Formatter.sendMessage(sender,
                            config.getString("messages.no-console"));
                } else {
                    if (args.length > 2) {
                        target = plugin.getServer().getPlayerExact(args[2]);
                    } else {
                        target = (Player) sender;
                    }

                    if (target == null || !target.isOnline()) {
                        Formatter.sendMessage(sender,
                                config.getString("messages.offline").replace("%player%", args[2]));
                    } else {
                        String openPermission = "interfacemaker.menu." + menuName;
                        String otherPermission = "interfacemaker.open-others";

                        if (target != sender && !sender.hasPermission(otherPermission)) {
                            Formatter.sendMessage(sender, config.getString("messages.no-permission-menu-others")
                                    .replace("%menu%", menuName).replace("%permission%", otherPermission));
                        } else if (!sender.hasPermission(openPermission)) {
                            Formatter.sendMessage(sender, config.getString("messages.no-permission-menu")
                                    .replace("%menu%", menuName).replace("%permission%", openPermission));
                        } else {
                            menu.build(target);

                            if (target == sender) {
                                Formatter.sendMessage(sender, config.getString("messages.opened-menu")
                                        .replace("%menu%", menuName));
                            } else {
                                Formatter.sendMessage(sender, config.getString("messages.opened-menu-other")
                                        .replace("%menu%", menuName).replace("%player%", target.getName()));
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
