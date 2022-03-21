package dev._2lstudios.interfacemaker.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InterfaceMakerAPI {
    private Map<String, InterfaceInventory> configuredInventories = new HashMap<>();
    private Map<String, InterfaceHotbar> configuredHotbars = new HashMap<>();
    private Map<Inventory, InterfaceInventory> openedInventories = new HashMap<>();
    private Map<Player, InterfaceHotbar> openedHotbars = new HashMap<>();

    public InterfaceInventory getConfiguredInventory(String name) {
        return configuredInventories.getOrDefault(name, null);
    }

    public Collection<InterfaceInventory> getConfiguredInventories() {
        return configuredInventories.values();
    }

    public void addConfiguredInventory(String name, InterfaceInventory interfaceInventory) {
        configuredInventories.put(name, interfaceInventory);
    }

    public InterfaceHotbar getConfiguredHotbar(String name) {
        return configuredHotbars.getOrDefault(name, null);
    }

    public Collection<InterfaceHotbar> getConfiguredHotbars() {
        return configuredHotbars.values();
    }

    public void addConfiguredHotbar(String name, InterfaceHotbar interfaceHotbar) {
        configuredHotbars.put(name, interfaceHotbar);
    }

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
