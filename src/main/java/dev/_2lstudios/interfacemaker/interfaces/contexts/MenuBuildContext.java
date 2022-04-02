package dev._2lstudios.interfacemaker.interfaces.contexts;

import org.bukkit.entity.Player;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class MenuBuildContext extends InterfaceInventoryHolder {
    private Player player;
    private InterfaceMenu menu;

    public MenuBuildContext(Player player, InterfaceMenu menu, int inventorySize) {
        super(inventorySize);
        this.player = player;
        this.menu = menu;
    }

    public MenuBuildContext(Player player, InterfaceMenu menu) {
        super(menu.getSize());
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public InterfaceMenu getMenu() {
        return menu;
    }
}
