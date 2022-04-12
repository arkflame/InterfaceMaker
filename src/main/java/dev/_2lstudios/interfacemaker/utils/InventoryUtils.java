package dev._2lstudios.interfacemaker.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean contains(Inventory inventory, ItemStack item) {
        int amount = item.getAmount();

        for (ItemStack inventoryItem : inventory.getContents()) {
            if (inventoryItem != null && item.isSimilar(inventoryItem)) {
                amount -= inventoryItem.getAmount();

                if (amount <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean contains(Inventory inventory, ItemStack... items) {
        for (ItemStack item : items) {
            if (!contains(inventory, item)) {
                return false;
            }
        }

        return true;
    }

    public static void remove(Inventory inventory, ItemStack item) {
        int amount = item.getAmount();

        for (ItemStack inventoryItem : inventory.getContents()) {
            if (inventoryItem != null && item.isSimilar(inventoryItem)) {
                int inventoryItemAmount = inventoryItem.getAmount();

                inventoryItem.setAmount(inventoryItemAmount - amount);

                amount -= inventoryItemAmount;

                if (amount <= 0) {
                    return;
                }
            }
        }
    }

    public static void remove(Inventory inventory, ItemStack... items) {
        for (ItemStack item : items) {
            remove(inventory, item);
        }
    }
}
