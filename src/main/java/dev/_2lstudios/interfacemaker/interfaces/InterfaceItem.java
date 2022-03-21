package dev._2lstudios.interfacemaker.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class InterfaceItem {
    private Material type = Material.DIRT;
    private String name = "InterfaceMaker";
    private List<String> lore = new ArrayList<>();
    private int amount = 1;
    private boolean movement = false;
    private boolean interaction = false;

    public ItemStack build(Player player) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(Formatter.format(player, name));

        if (!lore.isEmpty()) {
            itemMeta.setLore(Formatter.format(player, lore));
        }

        item.setItemMeta(itemMeta);
        item.setAmount(amount);

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

    public void onClick(Player player, Inventory clickedInventory) {
        // Overriden by super class
    }

    public void onInteract(Player player) {
        // Overriden by super class
    }
}
