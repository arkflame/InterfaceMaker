package dev._2lstudios.interfacemaker.interfaces;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InterfaceMakerAPI {
    private Map<Inventory, InterfaceInventory> openedInventories = new HashMap<>();
    private Map<Player, InterfaceHotbar> openedHotbars = new HashMap<>();

    public InterfaceInventory getOpenedInventory(Inventory inventory) {
        return openedInventories.getOrDefault(inventory, null);
    }

    public void setOpened(Inventory inventory, InterfaceInventory interfaceInventory) {
        openedInventories.put(inventory, interfaceInventory);
    }

    public void setClosed(Inventory inventory) {
        openedInventories.remove(inventory);
    }

    public InterfaceHotbar getHotbar(Player player) {
        return openedHotbars.getOrDefault(player, null);
    }

    public void setHotbar(Player player, InterfaceHotbar interfaceHotbar) {
        openedHotbars.put(player, interfaceHotbar);
    }

    public void unsetHotbar(Player player) {
        openedHotbars.remove(player);
    }
}
