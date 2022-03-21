package dev._2lstudios.interfacemaker;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.interfacemaker.commands.InterfaceMakerCommand;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.listeners.InventoryClickListener;
import dev._2lstudios.interfacemaker.listeners.InventoryCloseListener;
import dev._2lstudios.interfacemaker.listeners.InventoryDragListener;
import dev._2lstudios.interfacemaker.listeners.PlayerCommandPreProcessListener;
import dev._2lstudios.interfacemaker.listeners.PlayerDropItemListener;
import dev._2lstudios.interfacemaker.listeners.PlayerInteractListener;

public class InterfaceMaker extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        InterfaceMaker.api = new InterfaceMakerAPI();
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new InventoryClickListener(api), this);
        pluginManager.registerEvents(new InventoryCloseListener(api), this);
        pluginManager.registerEvents(new InventoryDragListener(api), this);
        pluginManager.registerEvents(new PlayerCommandPreProcessListener(api), this);
        pluginManager.registerEvents(new PlayerDropItemListener(api), this);
        pluginManager.registerEvents(new PlayerInteractListener(api), this);

        getCommand("interfacemaker").setExecutor(new InterfaceMakerCommand(this));
    }

    private static InterfaceMakerAPI api;

    public static InterfaceMakerAPI getAPI() {
        return InterfaceMaker.api;
    }
}