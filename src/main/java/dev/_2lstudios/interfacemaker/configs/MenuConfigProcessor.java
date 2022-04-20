package dev._2lstudios.interfacemaker.configs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;

public class MenuConfigProcessor {
    private InterfaceMakerAPI api;
    private ItemConfigProcessor itemConfigProcessor;

    public MenuConfigProcessor(InterfaceMakerAPI api, ItemConfigProcessor itemConfigProcessor) {
        this.api = api;
        this.itemConfigProcessor = itemConfigProcessor;
    }

    public void process(String menuName, Configuration config) {
        InterfaceMenu interfaceMenu = new InterfaceMenu();
        ConfigurationSection menuSettings = config.getConfigurationSection("menu-settings");
        String title = menuSettings.getString("name");
        int rows = menuSettings.getInt("rows");
        List<String> commands = menuSettings.getStringList("commands");
        int autoRefresh = menuSettings.getInt("auto-refresh");
        List<String> openActions = menuSettings.getStringList("open-actions");

        interfaceMenu.setTitle(title);
        interfaceMenu.setRows(rows);
        interfaceMenu.setCommands(commands);
        interfaceMenu.setAutoRefresh(autoRefresh);
        interfaceMenu.setOpenActions(openActions);

        if (menuSettings.contains("open-with-item")) {
            ConfigurationSection openWithItem = menuSettings.getConfigurationSection("open-with-item");
            String materialName = openWithItem.getString("material").toUpperCase();
            Material material = Material.getMaterial(materialName);
            boolean leftClick = openWithItem.getBoolean("left-click");
            boolean rightClick = openWithItem.getBoolean("right-click");

            if (material != null) {
                interfaceMenu.setOpenWithItem(material, leftClick, rightClick);
            }
        }

        if (menuSettings.contains("style.fill")) {
            String name = menuSettings.getString("style.fill.name");
            short durability = (short) menuSettings.getInt("style.fill.durability", 0);
            String materilaName = menuSettings.getString("style.fill.material");
            Material material = Material.getMaterial(materilaName);

            if (material != null) {
                interfaceMenu.fillEmpty(new InterfaceItem()
                    .setType(material)
                    .setDurability(durability)
                    .setName(ChatColor.translateAlternateColorCodes('&', name))
                );
            }
        }

        for (String sectionName : config.getKeys(false)) {
            if (!sectionName.equals("menu-settings")) {
                if (config.isConfigurationSection(sectionName)) {
                    ConfigurationSection itemSection = config.getConfigurationSection(sectionName);

                    itemConfigProcessor.process(interfaceMenu, itemSection);
                }
            }
        }

        api.addConfiguredMenu(menuName, interfaceMenu);
    }
}
