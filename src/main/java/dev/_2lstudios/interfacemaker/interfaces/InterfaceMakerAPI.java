package dev._2lstudios.interfacemaker.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.player.InterfacePlayerManager;
import dev._2lstudios.interfacemaker.vault.VaultProvider;

public class InterfaceMakerAPI {
    private InterfaceMaker plugin;
    private InterfacePlayerManager interfacePlayerManager;
    private VaultProvider vaultProvider;
    
    private Map<String, InterfaceMenu> configuredMenus = new HashMap<>();
    private Map<String, InterfaceHotbar> configuredHotbars = new HashMap<>();
    private Map<Inventory, InterfaceMenu> openedMenus = new HashMap<>();
    private Map<Player, InterfaceHotbar> openedHotbars = new HashMap<>();

    public InterfaceMakerAPI(InterfaceMaker plugin) {
        this.plugin = plugin;
        this.interfacePlayerManager = new InterfacePlayerManager(this);
        this.vaultProvider = new VaultProvider(plugin.getServer());
    }

    public InterfacePlayerManager getInterfacePlayerManager() {
        return interfacePlayerManager;
    }

    public InterfaceMenu getConfiguredMenu(String name) {
        return configuredMenus.getOrDefault(name, null);
    }

    public Collection<InterfaceMenu> getConfiguredMenusValues() {
        return configuredMenus.values();
    }

    public Map<String, InterfaceMenu> getConfiguredMenus() {
        return configuredMenus;
    }

    public void addConfiguredMenu(String name, InterfaceMenu interfaceMenu) {
        configuredMenus.put(name, interfaceMenu);
    }

    public InterfaceHotbar getConfiguredHotbar(String name) {
        return configuredHotbars.getOrDefault(name, null);
    }

    public Collection<InterfaceHotbar> getConfiguredHotbarsValues() {
        return configuredHotbars.values();
    }

    public Map<String, InterfaceHotbar> getConfiguredHotbars() {
        return configuredHotbars;
    }

    public void addConfiguredHotbar(String name, InterfaceHotbar interfaceHotbar) {
        configuredHotbars.put(name, interfaceHotbar);
    }

    public InterfaceMenu getOpenedMenu(Inventory inventory) {
        return openedMenus.getOrDefault(inventory, null);
    }

    public void setOpened(Inventory inventory, InterfaceMenu interfaceMenu) {
        openedMenus.put(inventory, interfaceMenu);
    }

    public void setClosed(Inventory inventory) {
        openedMenus.remove(inventory);
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

    public void clearConfiguredHotbars() {
        configuredHotbars.clear();
    }

    public void clearConfiguredInventories() {
        configuredMenus.clear();
    }

    public Configuration getConfig() {
        return plugin.getConfig();
    }

    public VaultProvider getVaultProvider() {
        return vaultProvider;
    }
}
