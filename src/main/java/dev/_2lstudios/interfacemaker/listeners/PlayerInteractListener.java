package dev._2lstudios.interfacemaker.listeners;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;
import dev._2lstudios.interfacemaker.placeholders.Formatter;
import dev._2lstudios.interfacemaker.player.InterfacePlayer;
import dev._2lstudios.interfacemaker.utils.InventoryUtils;
import dev._2lstudios.interfacemaker.vault.VaultProvider;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class PlayerInteractListener implements Listener {
    private InterfaceMakerAPI api;

    public PlayerInteractListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        InterfaceHotbar interfaceHotbar = api.getHotbar(player);
        PlayerInventory playerInventory = player.getInventory();
        int slot = playerInventory.getHeldItemSlot();
        ItemStack item = playerInventory.getItem(slot);

        if (item != null) {
            Material material = item.getType();

            for (InterfaceMenu inventory : api.getConfiguredMenusValues()) {
                Action action = event.getAction();
                boolean isActionLeft = action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
                boolean isActionRight = action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;

                if (inventory.getOpenWithItemMaterial() == material &&
                        ((inventory.isOpenWithItemLeftClick() && isActionLeft) ||
                                (inventory.isOpenWithItemRightClick() && isActionRight))) {
                    InterfacePlayer interfacePlayer = api.getInterfacePlayerManager().get(player);

                    if (interfacePlayer.isInteractCooling()) {
                        Formatter.sendMessage(player, api.getConfig().getString("messages.interact-cooldown"));
                    } else {
                        inventory.build(player);
                    }

                    return;
                }
            }
        }

        if (interfaceHotbar != null) {
            InterfaceItem interfaceItem = interfaceHotbar.getItem(slot);

            if (interfaceItem != null) {
                if (!interfaceItem.allowsInteraction()) {
                    event.setCancelled(true);
                }

                InterfacePlayer interfacePlayer = api.getInterfacePlayerManager().get(player);

                if (interfacePlayer.isInteractCooling()) {
                    Formatter.sendMessage(player, api.getConfig().getString("messages.interact-cooldown"));
                } else {
                    int levels = interfaceItem.getLevels();

                    if (levels > 0) {
                        int playerLevel = player.getLevel();

                        if (playerLevel >= levels) {
                            player.setLevel(playerLevel - levels);
                        } else {
                            return;
                        }
                    }

                    String permission = interfaceItem.getPermission();

                    if (permission != null && !player.hasPermission(permission)) {
                        String permissionMessage = interfaceItem.getPermissionMessage();

                        if (permissionMessage != null) {
                            Formatter.sendMessage(player, permissionMessage);
                        }

                        return;
                    }

                    Collection<ItemStack> requiredItems = interfaceItem.getRequiredItems();

                    if (!requiredItems.isEmpty()) {
                        ItemStack[] requiredItemsArray = requiredItems.toArray(new ItemStack[0]);
                        PlayerInventory inventory = player.getInventory();

                        if (!InventoryUtils.contains(inventory, requiredItemsArray)) {
                            Formatter.sendMessage(player,
                                    api.getConfig().getString("messages.no-items"));
                            return;
                        }

                        InventoryUtils.remove(inventory, requiredItemsArray);

                        player.updateInventory();
                    }

                    int price = interfaceItem.getPrice();

                    if (price > 0) {
                        VaultProvider vaultProvider = api.getVaultProvider();

                        if (!vaultProvider.isEconomyRegistered()) {
                            Formatter.sendMessage(player, api.getConfig().getString("messages.no-economy"));
                            return;
                        } else if (!vaultProvider.getEconomy().has(player, price)) {
                            Formatter.sendMessage(player, api.getConfig().getString("messages.no-balance")
                                    .replace("%price%", String.valueOf(price)));
                            return;
                        }
                    }

                    interfacePlayer.setLastInteract();
                    interfaceItem.runActions(api, player);
                    interfaceItem.onInteract(player);
                }
            }
        }
    }
}
