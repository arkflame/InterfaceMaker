package dev._2lstudios.interfacemaker;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.interfacemaker.commands.InterfaceMakerCommand;
import dev._2lstudios.interfacemaker.configs.ConfigManager;
import dev._2lstudios.interfacemaker.configs.HotbarConfigProcessor;
import dev._2lstudios.interfacemaker.configs.ItemConfigProcessor;
import dev._2lstudios.interfacemaker.configs.MenuConfigProcessor;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.listeners.InventoryClickListener;
import dev._2lstudios.interfacemaker.listeners.InventoryCloseListener;
import dev._2lstudios.interfacemaker.listeners.InventoryDragListener;
import dev._2lstudios.interfacemaker.listeners.PlayerCommandPreProcessListener;
import dev._2lstudios.interfacemaker.listeners.PlayerDropItemListener;
import dev._2lstudios.interfacemaker.listeners.PlayerInteractListener;
import dev._2lstudios.interfacemaker.listeners.PlayerJoinListener;
import dev._2lstudios.interfacemaker.listeners.PlayerQuitListener;
import dev._2lstudios.interfacemaker.player.InterfacePlayerManager;
import dev._2lstudios.interfacemaker.tasks.RefreshTask;

public class InterfaceMaker extends JavaPlugin {
    public void reloadFiles() {
        saveDefaultConfig();

        api.clearConfiguredHotbars();
        api.clearConfiguredInventories();

        ConfigManager configManager = new ConfigManager(this);
        ItemConfigProcessor itemConfigProcessor = new ItemConfigProcessor();
        HotbarConfigProcessor hotbarConfigProcessor = new HotbarConfigProcessor(api, itemConfigProcessor);
        MenuConfigProcessor menuConfigProcessor = new MenuConfigProcessor(api, itemConfigProcessor);
        File hotBarsFolder = new File(getDataFolder(), "hotbars");
        File menusFolder = new File(getDataFolder(), "menus");

        if (!hotBarsFolder.exists()) {
            configManager.saveConfig("hotbars/default.yml");
        }

        if (!menusFolder.exists()) {
            configManager.saveConfig("menus/default.yml");
        }

        for (File file : hotBarsFolder.listFiles()) {
            Configuration config = configManager.getConfig(file);

            hotbarConfigProcessor.process(file.getName().replace(".yml", ""), config);
        }

        for (File file : menusFolder.listFiles()) {
            Configuration config = configManager.getConfig(file);

            menuConfigProcessor.process(file.getName().replace(".yml", ""), config);
        }
    }

    @Override
    public void onEnable() {
        Server server = getServer();
        InterfaceMaker.api = new InterfaceMakerAPI(this);
        PluginManager pluginManager = server.getPluginManager();

        reloadFiles();

        InterfacePlayerManager interfacePlayerManager = api.getInterfacePlayerManager();

        for (Player player : server.getOnlinePlayers()) {
            interfacePlayerManager.create(player);
        }

        pluginManager.registerEvents(new InventoryClickListener(api), this);
        pluginManager.registerEvents(new InventoryCloseListener(api), this);
        pluginManager.registerEvents(new InventoryDragListener(api), this);
        pluginManager.registerEvents(new PlayerCommandPreProcessListener(api), this);
        pluginManager.registerEvents(new PlayerDropItemListener(api), this);
        pluginManager.registerEvents(new PlayerInteractListener(api), this);
        pluginManager.registerEvents(new PlayerJoinListener(api), this);
        pluginManager.registerEvents(new PlayerQuitListener(api), this);
        
        server.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getCommand("interfacemaker").setExecutor(new InterfaceMakerCommand(api, this));

        server.getScheduler().runTaskTimer(this, new RefreshTask(api), 1L, 1L);
    }

    private static InterfaceMakerAPI api;

    public static InterfaceMakerAPI getAPI() {
        return InterfaceMaker.api;
    }
}