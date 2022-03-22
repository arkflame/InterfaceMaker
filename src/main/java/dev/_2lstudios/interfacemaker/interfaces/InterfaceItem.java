package dev._2lstudios.interfacemaker.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceItem {
    private Material type = Material.DIRT;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private List<String> lore = new ArrayList<>();
    private Collection<String> actions = new HashSet<>();
    private Collection<ItemStack> requiredItems = new HashSet<>();
    private String name = "InterfaceMaker";
    private String permission = "";
    private String viewPermission = "";
    private String permissionMessage = "";
    private int amount = 1;
    private int levels = 0;
    private int price = 0;
    private short durability = 0;
    private boolean movement = false;
    private boolean interaction = false;
    private boolean keepOpen = false;

    public ItemStack build(Player player) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(Formatter.format(player, name));

        if (!lore.isEmpty()) {
            itemMeta.setLore(Formatter.format(player, lore));
        }

        item.setItemMeta(itemMeta);
        item.setAmount(amount);

        if (durability != 0) {
            item.setDurability(durability);
        }

        return item;
    }

    public InterfaceItem setName(String name) {
        this.name = name;
        return this;
    }

    public InterfaceItem setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public InterfaceItem setLore(String[] lore) {
        return setLore(Arrays.asList(lore));
    }

    public InterfaceItem setType(Material type) {
        this.type = type == null ? Material.DIRT : type;
        return this;
    }

    public InterfaceItem setType(String name) {
        Material type = Material.getMaterial(name);

        return setType(type);
    }

    public InterfaceItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public InterfaceItem setMovement(boolean movement) {
        this.movement = movement;
        return this;
    }

    public InterfaceItem setInteraction(boolean interaction) {
        this.interaction = interaction;
        return this;
    }

    public boolean allowsMovement() {
        return movement;
    }

    public boolean allowsInteraction() {
        return interaction;
    }

    public InterfaceItem setDurability(short durability) {
        this.durability = durability;
        return this;
    }

    public InterfaceItem setDurability(int durability) {
        return setDurability((short) durability);
    }

    public InterfaceItem addEnchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public InterfaceItem setEnchantments(List<String> enchantments) {
        this.enchantments.clear();

        if (this.enchantments != null) {
            for (String text : enchantments) {
                String[] splittedText = text.split(", ");
                String enchantmentName = splittedText[0];
                String enchantmentLevelString = splittedText[1];

                try {
                    Enchantment enchantment = Enchantment.getByName(enchantmentName);
                    int level = Integer.parseInt(enchantmentLevelString);

                    addEnchantment(enchantment, level);
                } catch (NumberFormatException ex) {
                    // Ignored
                }
            }
        }

        return this;
    }

    public InterfaceItem setKeepOpen(boolean keepOpen) {
        this.keepOpen = keepOpen;
        return this;
    }

    public InterfaceItem setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public InterfaceItem setViewPermission(String viewPermission) {
        this.viewPermission = viewPermission;
        return this;
    }

    public InterfaceItem setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    public InterfaceItem setRequiredItems(List<String> requiredItems) {
        for (String text : requiredItems) {
            String[] splittedText = text.split(", ");
            String materialName = splittedText[0];
            String amountString = splittedText[1];

            try {
                Material type = Material.getMaterial(materialName);

                if (type != null) {
                    int amount = Integer.parseInt(amountString);

                    this.requiredItems.add(new ItemStack(type, amount));
                }
            } catch (NumberFormatException ex) {
                // Ignored
            }
        }

        return this;
    }

    public InterfaceItem setLevels(int levels) {
        this.levels = levels;
        return this;
    }

    public InterfaceItem setPrice(int price) {
        this.price = price;
        return this;
    }

    public InterfaceItem setActions(List<String> actions) {
        this.actions = actions;
        return this;
    }

    public Material getType() {
        return type;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public InterfaceItem setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public List<String> getLore() {
        return lore;
    }

    public Collection<String> getActions() {
        return actions;
    }

    public Collection<ItemStack> getRequiredItems() {
        return requiredItems;
    }

    public InterfaceItem setRequiredItems(Collection<ItemStack> requiredItems) {
        this.requiredItems = requiredItems;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getViewPermission() {
        return viewPermission;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public int getAmount() {
        return amount;
    }

    public int getLevels() {
        return levels;
    }

    public int getPrice() {
        return price;
    }

    public short getDurability() {
        return durability;
    }

    public boolean isMovement() {
        return movement;
    }

    public boolean isInteraction() {
        return interaction;
    }

    public boolean isKeepOpen() {
        return keepOpen;
    }

    public void runActions(InterfaceMakerAPI api, Player player) {
        for (String action : getActions()) {
            String[] splittedAction = action.split(" ");

            if (splittedAction.length > 0) {
                switch (splittedAction[0].toLowerCase()) {
                    case "tell:": {
                        player.sendMessage(Formatter.format(player, action.replace("tell: ", "")));

                        break;
                    }
                    case "open-menu:": {
                        InterfaceInventory inventory = api.getConfiguredInventory(action.replace("open-menu: ", ""));

                        if (inventory != null) {
                            inventory.build(player);
                        }

                        break;
                    }
                    case "give-hotbar:": {
                        InterfaceHotbar hotbar = api.getConfiguredHotbar(action.replace("give-hotbar: ", ""));

                        if (hotbar != null) {
                            hotbar.build(player);
                        }

                        break;
                    }
                    case "console:": {
                        Server server = player.getServer();

                        server.dispatchCommand(server.getConsoleSender(), action.replace("console: ", ""));

                        break;
                    }
                    case "player:": {
                        Server server = player.getServer();

                        server.dispatchCommand(player, action.replace("player: ", ""));

                        break;
                    }
                }
            }
        }
    }

    public void onClick(Player player, Inventory clickedInventory) {
        // Overriden by super class
    }

    public void onInteract(Player player) {
        // Overriden by super class
    }
}
