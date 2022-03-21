package dev._2lstudios.interfacemaker.placeholders;

import java.util.List;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Formatter {
    public static String format(Player player, String text) {
        return ChatColor.translateAlternateColorCodes('&',
                IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(player, text)));
    }

    public static List<String> format(Player player, List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);

            lore.set(i, format(player, line));
        }

        return lore;
    }
}