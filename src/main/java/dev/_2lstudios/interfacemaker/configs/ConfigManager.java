package dev._2lstudios.interfacemaker.configs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
    private Map<String, Configuration> configs;
    private Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.configs = new HashMap<>();
        this.plugin = plugin;
    }

    public Configuration getConfig(File file) {
        Configuration config;

        try {
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IllegalArgumentException ex) {
            config = new YamlConfiguration();
        }

        return config;
    }

    public Configuration getConfig(String name) {
        if (configs.containsKey(name)) {
            return configs.get(name);
        }

        File file = new File(plugin.getDataFolder(), name);
        Configuration config = getConfig(file);

        configs.put(name, config);

        return config;
    }

    public Configuration saveConfig(String name) {
        File configFile = new File(plugin.getDataFolder(), name);

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        return getConfig(name);
    }
}