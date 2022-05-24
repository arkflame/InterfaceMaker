package dev._2lstudios.interfacemaker.interfaces;

import org.bukkit.entity.Player;

public class QueuedBuildable {
    private Buildable buildable;
    private Player player;
    private int ticks;

    public QueuedBuildable(Buildable buildable, Player player, int ticks) {
        this.buildable = buildable;
        this.player = player;
        this.ticks = ticks;
    }

    public int tick() {
        return --ticks;
    }

    public void build() {
        if (player != null && player.isOnline()) {
            buildable.build(player);
        }
    }
}
