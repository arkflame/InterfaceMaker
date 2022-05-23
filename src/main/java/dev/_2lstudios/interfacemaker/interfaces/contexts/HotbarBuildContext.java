package dev._2lstudios.interfacemaker.interfaces.contexts;

import org.bukkit.entity.Player;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class HotbarBuildContext extends InterfaceInventoryHolder {
    private Player player;
    private InterfaceHotbar hotbar;

    public HotbarBuildContext(Player player, InterfaceHotbar hotbar) {
        super(9, "Hotbar");
        this.player = player;
        this.hotbar = hotbar;
    }

    public Player getPlayer() {
        return player;
    }

    public InterfaceHotbar getHotbar() {
        return hotbar;
    }
}
