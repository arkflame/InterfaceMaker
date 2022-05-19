package dev._2lstudios.interfacemaker.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceItem {
    private Material type = Material.DIRT;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private List<String> lore = new ArrayList<>();
    private Collection<String> actions = new HashSet<>();
    private Collection<ItemStack> requiredItems = new HashSet<>();
    private Collection<ItemFlag> flags = new HashSet<>();
    private String name = "InterfaceMaker";
    private String permission = null;
    private String viewPermission = null;
    private String permissionMessage = null;
    private String skullOwner = null;
    private String mainPotionEffect = null;
    private Color leatherArmorColor = null;
    private int amount = 1;
    private int levels = 0;
    private int price = 0;
    private int customModel = -1;
    private short durability = 0;
    private boolean movement = false;
    private boolean interaction = false;
    private boolean keepOpen = false;

    public ItemStack build(Player player) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();

        if (mainPotionEffect != null && itemMeta instanceof PotionMeta) {
            PotionEffectType potionEffectType = PotionEffectType.getByName(mainPotionEffect);

            if (potionEffectType != null) {
                PotionType type = PotionType.getByEffect(potionEffectType);
                Potion potion = new Potion(type);
                
                item = potion.toItemStack(amount);
                itemMeta = item.getItemMeta();
            }
        }

        itemMeta.setDisplayName(Formatter.format(player, name));

        if (customModel != -1) {
            try {
                this.getClass().getMethod("setCustomModelData", Integer.class);
                itemMeta.setCustomModelData(this.customModel);
            } catch (NoSuchMethodException | SecurityException ignored) {
            }
        }

        if (!lore.isEmpty()) {
            itemMeta.setLore(Formatter.format(player, lore));
        }

        if (skullOwner != null && itemMeta instanceof SkullMeta) {
            ((SkullMeta) itemMeta).setOwner(skullOwner.replace("%player%", player.getName()));
            item.setDurability((short) 3);
        }

        if (leatherArmorColor != null && itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) itemMeta).setColor(leatherArmorColor);
        }

        for (Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            itemMeta.addEnchant(entry.getKey(), entry.getValue(), true);
        }

        for (ItemFlag flag : flags) {
            itemMeta.addItemFlags(flag);
        }

        item.setItemMeta(itemMeta);
        item.setAmount(amount);

        if (durability != 0) {
            item.setDurability(durability);
        }

        return item;
    }

    public InterfaceItem setLeatherArmorColor(Color color) {
        this.leatherArmorColor = color;
        return this;
    }

    public Color getLeatherArmorColor() {
        return leatherArmorColor;
    }

    public InterfaceItem setMainPotionEffect(String potionEffect) {
        this.mainPotionEffect = potionEffect;
        return this;
    }

    public String getMainPotionEffect() {
        return mainPotionEffect;
    }

    public InterfaceItem setSkullOwner(String owner) {
        this.skullOwner = owner;
        return this;
    }

    public String getSkullOwner() {
        return skullOwner;
    }

    public InterfaceItem setName(String name) {
        this.name = name;
        return this;
    }

    public InterfaceItem setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public InterfaceItem setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public InterfaceItem setLore(String lore) {
        return setLore(lore.split("\n"));
    }

    public InterfaceItem addLoreLine(String line) {
        this.lore.add(line);
        return this;
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

    public InterfaceItem setAllowsMovement(boolean movement) {
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

    public InterfaceItem setCustomModel(int model) {
        this.customModel = model;
        return this;
    }

    public InterfaceItem setEnchantments(List<String> enchantments) {
        this.enchantments.clear();

        if (this.enchantments != null) {
            for (String text : enchantments) {
                String[] splittedText = text.split(", ");
                String enchantmentName = splittedText[0].toUpperCase();
                String enchantmentLevelString = splittedText[1];

                try {
                    Enchantment enchantment = Enchantment.getByName(enchantmentName);
                    int level = Integer.parseInt(enchantmentLevelString);

                    if (enchantment != null) {
                        addEnchantment(enchantment, level);
                    }
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
            String[] splittedMaterial = splittedText[0].split(":");
            String materialName = splittedMaterial[0].toUpperCase();
            String amountString = splittedText[1];
            Material type = Material.getMaterial(materialName);

            if (type != null) {
                try {
                    int amount = Integer.parseInt(amountString);
                    short data = 0;

                    if (splittedMaterial.length > 1) {
                        try {
                            data = Short.parseShort(splittedMaterial[1]);
                        } catch (NumberFormatException ex) {
                            // Ignored
                        }
                    }

                    ItemStack item = new ItemStack(type, amount, data);
                    this.requiredItems.add(item);
                } catch (NumberFormatException ex) {
                    // Ignored
                }
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

    public void setFlags(Collection<String> flags) {
        this.flags.clear();

        for (String flag : flags) {
            try {
                this.flags.add(ItemFlag.valueOf(flag));
            } catch (IllegalArgumentException ex) {
                // Ignored
            }
        }
    }

    public void setFlags(String... flags) {
        this.flags.clear();

        for (String flag : flags) {
            try {
                this.flags.add(ItemFlag.valueOf(flag));
            } catch (IllegalArgumentException ex) {
                // Ignored
            }
        }
    }

    public Collection<ItemFlag> getFlags() {
        return flags;
    }

    public void runActions(InterfaceMakerAPI api, Player player) {
        api.runActions(player, this.getActions());
    }

    public void onClick(Player player, Inventory clickedInventory) {
        // Overriden by super class
    }

    public void onRightClick(Player player, Inventory clickedInventory) {
        // Overriden by super class
    }

    public void onLeftClick(Player player, Inventory clickedInventory) {
        // Overriden by super class
    }

    public void onInteract(Player player) {
        // Overriden by super class
    }

    public void onRightInteract(Player player) {
        // Overriden by super class
    }

    public void onLeftInteract(Player player) {
        // Overriden by super class
    }
}
