package dev._2lstudios.interfacemaker.configs;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class HotbarConfigProcessor {
    private InterfaceMakerAPI api;

    public HotbarConfigProcessor(InterfaceMakerAPI api) {
        this.api = api;
    }

    public void process(String menuName, Configuration config) {
        InterfaceHotbar interfaceHotbar = new InterfaceHotbar();
        ConfigurationSection hotbarSettings = config.getConfigurationSection("hotbar-settings");
        int autoRefresh = hotbarSettings.getInt("auto-refresh");
        boolean giveOnSpawn = hotbarSettings.getBoolean("give-on-spawn");

        interfaceHotbar.setAutoRefresh(autoRefresh);
        interfaceHotbar.setGiveOnSpawn(giveOnSpawn);

        for (String sectionName : config.getKeys(false)) {
            if (!sectionName.equals("hotbar-settings")) {
                if (config.isConfigurationSection(sectionName)) {
                    ConfigurationSection itemSection = config.getConfigurationSection(sectionName);
                    InterfaceItem interfaceItem = new InterfaceItem();
                    String materialName = itemSection.getString("material");
                    Material material = Material.getMaterial(materialName);
                    int durability = itemSection.getInt("durability");
                    int slot = itemSection.getInt("position");
                    String name = itemSection.getString("name");
                    List<String> lore = itemSection.getStringList("lore");
                    List<String> enchantments = itemSection.getStringList("enchantments");
                    String permission = itemSection.getString("permission");
                    String viewPermission = itemSection.getString("view-permission");
                    String permissionMessage = itemSection.getString("permission-message");
                    List<String> requiredItems = itemSection.getStringList("required-items");
                    int levels = itemSection.getInt("levels");
                    int price = itemSection.getInt("price");
                    List<String> actions = itemSection.getStringList("actions");

                    if (material != null) {
                        interfaceItem.setType(material);
                        interfaceItem.setDurability(durability);
                        interfaceItem.setName(name);
                        interfaceItem.setLore(lore);
                        interfaceItem.setEnchantments(enchantments);
                        interfaceItem.setPermission(permission);
                        interfaceItem.setViewPermission(viewPermission);
                        interfaceItem.setPermissionMessage(permissionMessage);
                        interfaceItem.setRequiredItems(requiredItems);
                        interfaceItem.setLevels(levels);
                        interfaceItem.setPrice(price);
                        interfaceItem.setActions(actions);
                        interfaceHotbar.setItem(slot, interfaceItem);
                    }
                }
            }
        }

        api.addConfiguredHotbar(menuName, interfaceHotbar);
    }
}
