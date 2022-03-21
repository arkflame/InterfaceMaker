package dev._2lstudios.interfacemaker.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InterfaceItem {
    private Material type = Material.DIRT;
    private String name = "InterfaceMaker";
    private List<String> lore = new ArrayList<>();
    private int amount = 0;
    private boolean movement = false;
    private boolean interaction = false;

    public ItemStack build(Player player) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        if (!lore.isEmpty()) {
            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);
        item.setAmount(amount);

        return item;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setLore(String[] lore) {
        setLore(Arrays.asList(lore));
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
