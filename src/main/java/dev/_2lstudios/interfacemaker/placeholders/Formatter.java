package dev._2lstudios.interfacemaker.placeholders;

import java.util.List;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Formatter {
    public static String color(String text) {
        if (text != null) {
            return ChatColor.translateAlternateColorCodes('&',
                    IridiumColorAPI.process(text));
        }

        return null;
    }

    public static String format(Player player, String text) {
        if (text != null) {
            if (player.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                text = PlaceholderAPI.setPlaceholders(player, text);
            }
            return color(text).replace("%player_name%", player.getName())
                    .replace("%display_name%", player.getDisplayName());
        }

        return null;
    }

    public static void sendMessage(CommandSender sender, String text) {
        if (text != null) {
            if (sender instanceof Player) {
                sender.sendMessage(format((Player) sender, text));
            } else {
                sender.sendMessage(color(text));
            }
        }
    }

    public static List<String> format(Player player, List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);

            lore.set(i, format(player, line));
        }

        return lore;
    }
}