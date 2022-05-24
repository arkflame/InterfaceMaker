package dev._2lstudios.interfacemaker.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.InterfaceMaker;
import dev._2lstudios.interfacemaker.interfaces.contexts.HotbarBuildContext;
import dev._2lstudios.interfacemaker.interfaces.contexts.MenuBuildContext;
import dev._2lstudios.interfacemaker.placeholders.Formatter;
import dev._2lstudios.interfacemaker.player.InterfacePlayerManager;
import dev._2lstudios.interfacemaker.utils.ProxyUtils;
import dev._2lstudios.interfacemaker.vault.VaultProvider;

public class InterfaceMakerAPI {
    private InterfaceMaker plugin;
    private InterfacePlayerManager interfacePlayerManager;
    private VaultProvider vaultProvider;

    private Map<String, InterfaceMenu> configuredMenus = new HashMap<>();
    private Map<String, InterfaceHotbar> configuredHotbars = new HashMap<>();
    private Map<Inventory, MenuBuildContext> openedMenus = new HashMap<>();
    private Map<Player, HotbarBuildContext> openedHotbars = new HashMap<>();

    private Collection<QueuedBuildable> queuedBuildables = new HashSet<>();

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

    public MenuBuildContext getOpenedMenuContext(Inventory inventory) {
        return openedMenus.getOrDefault(inventory, null);
    }

    public InterfaceMenu getOpenedMenu(Inventory inventory) {
        MenuBuildContext context = getOpenedMenuContext(inventory);

        if (context != null) {
            return context.getMenu();
        }

        return null;
    }

    public void setMenu(Inventory inventory, MenuBuildContext context) {
        openedMenus.put(inventory, context);
    }

    public void unsetMenu(Inventory inventory) {
        openedMenus.remove(inventory);
    }

    public HotbarBuildContext getHotbarContext(Player player) {
        return openedHotbars.getOrDefault(player, null);
    }

    public InterfaceHotbar getHotbar(Player player) {
        HotbarBuildContext context = getHotbarContext(player);

        if (context != null) {
            return context.getHotbar();
        }

        return null;
    }

    public void setHotbar(Player player, HotbarBuildContext context) {
        openedHotbars.put(player, context);
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

    public void runActions(Player player, Collection<String> actions) {
        for (String rawAction : actions) {
            String[] parts = rawAction.split(":");

            if (parts.length > 0) {
                String action = parts[0].trim().toLowerCase();
                String arg = parts[1].trim().replace("%player_name%", player.getName());

                switch (action) {
                    case "tell": {
                        player.sendMessage(Formatter.format(player, arg));
                        break;
                    }
                    case "open-menu": {
                        InterfaceMenu inventory = this.getConfiguredMenu(arg);

                        if (inventory != null) {
                            inventory.build(player);
                        }

                        break;
                    }
                    case "give-hotbar": {
                        InterfaceHotbar hotbar = this.getConfiguredHotbar(arg);

                        if (hotbar != null) {
                            hotbar.build(player);
                        }

                        break;
                    }
                    case "console": {
                        Server server = player.getServer();
                        server.dispatchCommand(server.getConsoleSender(), arg.replace("%player%", player.getName()));
                        break;
                    }
                    case "player": {
                        player.chat("/" + arg);
                        break;
                    }
                    case "server": {
                        ProxyUtils.sendToServer(this.plugin, player, arg);
                        break;
                    }
                    case "sound": {
                        String soundName = arg.toUpperCase();

                        try {
                            player.playSound(player.getLocation(), Sound.valueOf(soundName), 1, 1);
                        } catch (IllegalArgumentException ex) {
                            plugin.getLogger().warning("Tried to play invalid sound: " + soundName);
                        }
                        break;
                    }
                }
            }
        }
    }

    public Map<Inventory, MenuBuildContext> getOpenedMenuContexts() {
        return openedMenus;
    }

    public Map<Player, HotbarBuildContext> getOpenedHotbarContexts() {
        return openedHotbars;
    }

    public void queueBuild(Player player, Buildable buildable, int giveDelay) {
        queuedBuildables.add(new QueuedBuildable(buildable, player, giveDelay));
    }

    public Collection<QueuedBuildable> getQueuedBuildables() {
        return queuedBuildables;
    }
}
