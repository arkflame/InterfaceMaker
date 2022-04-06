package dev._2lstudios.interfacemaker.interfaces.contexts;

import org.bukkit.entity.Player;

import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class HotbarBuildContext extends InterfaceInventoryHolder {
    private Player player;

    public HotbarBuildContext(Player player) {
        super(9, "Hotbar");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
