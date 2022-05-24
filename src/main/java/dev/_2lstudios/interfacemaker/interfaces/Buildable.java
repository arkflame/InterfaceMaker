package dev._2lstudios.interfacemaker.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Buildable {
    public Buildable build(Player player);

    public Buildable build(Player player, Inventory inventory);
}
