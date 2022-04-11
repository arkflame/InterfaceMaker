package dev._2lstudios.interfacemaker.commands;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.placeholders.Formatter;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class InterfaceMakerCommand implements CommandExecutor {
    private InterfaceMakerAPI api;
    private InterfaceMaker plugin;

    public InterfaceMakerCommand(InterfaceMakerAPI api, InterfaceMaker plugin) {
        this.api = api;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Configuration config = api.getConfig();

            if (args[0].equals("reload")) {
                plugin.reloadFiles();
                Formatter.sendMessage(sender, config.getString("messages.reload"));
            } else if (args[0].equals("open")) {
                if (args.length > 3) {
                    String mode = args[1];
                    String fileName = args[2];
                    String playerName = args[3];

                    switch (mode) {
                        case "hotbar": {
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
                            break;
                        }
                        case "menu": {
                            InterfaceMenu inventory = api.getConfiguredMenu(fileName);

                            if (inventory != null) {
                                Player player = plugin.getServer().getPlayer(playerName);

                                if (player != null && player.isOnline()) {
                                    inventory.build(player);
                                    sender.sendMessage("Opened menu " + fileName + " for player " + playerName + "!");
                                } else {
                                    sender.sendMessage("The player is not online!");
                                }
                            } else {
                                sender.sendMessage("The menu " + fileName + " does not exist!");
                            }
                            break;
                        }
                    }
                } else if (args.length > 2) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        String mode = args[1];
                        String fileName = args[2];

                        switch (mode) {
                            case "hotbar": {
                                InterfaceHotbar hotbar = api.getConfiguredHotbar(fileName);

                                if (hotbar != null) {
                                    hotbar.build(player);
                                    sender.sendMessage("Opened hotbar " + fileName + "!");
                                } else {
                                    sender.sendMessage("The hotbar " + fileName + " does not exist!");
                                }
                                break;
                            }
                            case "menu": {
                                InterfaceMenu inventory = api.getConfiguredMenu(fileName);

                                if (inventory != null) {
                                    inventory.build(player);
                                    sender.sendMessage("Opened menu " + fileName + "!");
                                } else {
                                    sender.sendMessage("The menu " + fileName + " does not exist!");
                                }
                                break;
                            }
                        }
                    } else {
                        sender.sendMessage("Cannot open menu from console!");
                    }
                } else {
                    sender.sendMessage("/interfacemaker open <type> <menu> [player]");
                }
            } else if (args[0].equals("menus")) {
                Map<String, InterfaceHotbar> hotbars = api.getConfiguredHotbars();

                if (hotbars.isEmpty()) {
                    sender.sendMessage("There are no hotbars configured!");
                } else {
                    sender.sendMessage("Possible menus:");

                    for (Entry<String, InterfaceHotbar> entry : hotbars.entrySet()) {
                        sender.sendMessage(entry.getKey());
                    }
                }
            } else if (args[0].equals("hotbars")) {
                Map<String, InterfaceHotbar> hotbars = api.getConfiguredHotbars();

                if (hotbars.isEmpty()) {
                    sender.sendMessage("There are no hotbars configured!");
                } else {
                    sender.sendMessage("Possible hotbars:");

                    for (Entry<String, InterfaceHotbar> entry : hotbars.entrySet()) {
                        sender.sendMessage(entry.getKey());
                    }
                }
            } else {
                sender.sendMessage("No command found for argument: '" + args[0] + "'");
                sender.sendMessage("Possible commands: reload, open, menus, hotbars");
            }
        } else {
            sender.sendMessage("InterfaceMaker by LinsaFTW");
            sender.sendMessage("Possible commands: reload, open, menus, hotbars");
        }

        return true;
    }
}
