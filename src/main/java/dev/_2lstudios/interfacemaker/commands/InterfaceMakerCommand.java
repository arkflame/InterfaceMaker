package dev._2lstudios.interfacemaker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev._2lstudios.interfacemaker.InterfaceMaker;

public class InterfaceMakerCommand implements CommandExecutor {
    private InterfaceMaker plugin;

    public InterfaceMakerCommand(InterfaceMaker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equals("reload")) {
                plugin.reloadConfig();
                sender.sendMessage("The plugin has been reloaded!");
            } else if (args[0].equals("menu")) {
                if (args.length > 2) {
                    String playerName = args[1];
                    String menuName = args[2];

                    sender.sendMessage("Opened menu " + menuName + " for player " + playerName + "!");
                } else {
                    sender.sendMessage("/interfacemaker menu <player> <menu>");
                }
            } else if (args[0].equals("hotbar")) {
                if (args.length > 2) {
                    String playerName = args[1];
                    String hotbarName = args[2];

                    sender.sendMessage("Opened hotbar " + hotbarName + " for player " + playerName + "!");
                } else {
                    sender.sendMessage("/interfacemaker hotbar <player> <hotbar>");
                }
            } else {
                sender.sendMessage("No command found for argument: '" + args[0] + "'");
                sender.sendMessage("Possible options: reload");
            }
        } else {
            sender.sendMessage("/interfacemaker reload");
        }
        
        return true;
    }
}
