package dev._2lstudios.interfacemaker.configs;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.holders.InterfaceInventoryHolder;

public class ItemConfigProcessor {
    public void process(InterfaceInventoryHolder interfaceInventoryHolder, ConfigurationSection itemSection) {
        InterfaceItem interfaceItem = new InterfaceItem();
        String materialName = itemSection.getString("material").toUpperCase();
        Material material = Material.getMaterial(materialName == null ? "STONE" : materialName);
        int durability = itemSection.getInt("durability");
        int positionX = Math.max(0, itemSection.getInt("position-x") - 1);
        int positionY = Math.max(0, itemSection.getInt("position-y") - 1);
        int slot = positionX + (positionY * 9);
        String name = itemSection.getString("name");
        List<String> lore = itemSection.getStringList("lore");
        List<String> enchantments = itemSection.getStringList("enchantments");
        boolean keepOpen = itemSection.getBoolean("keep-open");
        String permission = itemSection.getString("permission");
        String viewPermission = itemSection.getString("view-permission");
        String permissionMessage = itemSection.getString("permission-message");
        List<String> requiredItems = itemSection.getStringList("required-items");
        int levels = itemSection.getInt("levels");
        int price = itemSection.getInt("price");
        List<String> actions = itemSection.getStringList("actions");
        String skullOwner = itemSection.getString("skull-owner");
        String potionEffect = itemSection.getString("potion-effect");
        int leatherArmorColor = itemSection.getInt("leather-armor-color");

        if (material != null) {
            interfaceItem.setType(material);
            interfaceItem.setDurability(durability);
            interfaceItem.setName(name);
            interfaceItem.setLore(lore);
            interfaceItem.setEnchantments(enchantments);
            interfaceItem.setKeepOpen(keepOpen);
            interfaceItem.setPermission(permission);
            interfaceItem.setViewPermission(viewPermission);
            interfaceItem.setPermissionMessage(permissionMessage);
            interfaceItem.setRequiredItems(requiredItems);
            interfaceItem.setLevels(levels);
            interfaceItem.setPrice(price);
            interfaceItem.setActions(actions);
            interfaceItem.setSkullOwner(skullOwner);
            interfaceItem.setMainPotionEffect(potionEffect);

            if (leatherArmorColor > 0) {
                interfaceItem.setLeatherArmorColor(Color.fromRGB(leatherArmorColor));
            }

            interfaceInventoryHolder.setItem(slot, interfaceItem);
        }
    }
}
