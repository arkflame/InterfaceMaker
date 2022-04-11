package dev._2lstudios.interfacemaker.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class InterfacePlayerManager {
    private InterfaceMakerAPI api;
    private Map<UUID, InterfacePlayer> players = new HashMap<>();

    public InterfacePlayerManager(InterfaceMakerAPI api) {
        this.api = api;
    }

    public InterfacePlayerManager create(UUID uuid) {
        players.put(uuid, new InterfacePlayer(api));
        return this;
    }

    public InterfacePlayerManager create(Player player) {
        return create(player.getUniqueId());
    }

    public InterfacePlayerManager remove(UUID uuid) {
        players.remove(uuid);
        return this;
    }

    public InterfacePlayer get(UUID uuid) {
        return players.getOrDefault(uuid, null);
    }

    public InterfacePlayer get(Player player) {
        return get(player.getUniqueId());
    }
}