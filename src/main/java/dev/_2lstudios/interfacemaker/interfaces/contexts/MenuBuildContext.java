package dev._2lstudios.interfacemaker.interfaces.contexts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class MenuBuildContext extends InterfaceInventoryHolder {
    private Player player;

    public MenuBuildContext(Player player, Inventory inventory) {
        super(inventory.getSize());
        this.player = player;
    }

    public MenuBuildContext(Player player, InterfaceMenu menu) {
        super(menu.getSize());
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
