package dev._2lstudios.interfacemaker.listeners;

import java.util.Collection;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.interfaces.contexts.MenuBuildContext;
import dev._2lstudios.interfacemaker.placeholders.Formatter;
import dev._2lstudios.interfacemaker.player.InterfacePlayer;
import dev._2lstudios.interfacemaker.utils.InventoryUtils;
import dev._2lstudios.interfacemaker.vault.VaultProvider;

public class InventoryClickListener implements Listener {
    private InterfaceMakerAPI api;

    public InventoryClickListener(InterfaceMakerAPI api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            InventoryView view = event.getView();
            Inventory clickedInventory = event.getClickedInventory();

            if (clickedInventory != null) {
                Inventory bottomInventory = view.getBottomInventory();
                int slot = event.getSlot();

                if (clickedInventory == bottomInventory) {
                    InterfaceHotbar interfaceHotbar = api.getHotbar(player);

                    if (interfaceHotbar != null) {
                        InterfaceItem interfaceItem = interfaceHotbar.getItem(slot);

                        if (interfaceItem != null) {
                            if (!interfaceHotbar.allowsMovement() || !interfaceItem.allowsMovement()) {
                                event.setCancelled(true);
                            }

                            InterfacePlayer interfacePlayer = api.getInterfacePlayerManager().get(player);

                            if (interfacePlayer.isClickCooling()) {
                                Formatter.sendMessage(player,
                                        api.getConfig().getString("messages.click-cooldown"));
                            } else {
                                interfacePlayer.setLastClick();

                                ClickType click = event.getClick();

                                interfaceItem.onClick(player, clickedInventory);

                                if (click == ClickType.LEFT) {
                                    interfaceItem.onLeftClick(player, clickedInventory);
                                }

                                if (click == ClickType.RIGHT) {
                                    interfaceItem.onRightClick(player, clickedInventory);
                                }
                            }
                        }
                    }
                } else {
                    InventoryHolder inventoryHolder = clickedInventory.getHolder();

                    if (inventoryHolder instanceof MenuBuildContext) {
                        MenuBuildContext menuBuildContext = (MenuBuildContext) inventoryHolder;

                        if (menuBuildContext != null) {
                            if (!menuBuildContext.getMenu().allowsMovement()) {
                                event.setCancelled(true);
                            }

                            InterfaceItem interfaceItem = menuBuildContext.getItem(slot);

                            if (interfaceItem != null) {
                                if (!interfaceItem.allowsMovement()) {
                                    event.setCancelled(true);
                                }

                                InterfacePlayer interfacePlayer = api.getInterfacePlayerManager().get(player);

                                if (interfacePlayer.isClickCooling()) {
                                    Formatter.sendMessage(player,
                                            api.getConfig().getString("messages.click-cooldown"));
                                } else {
                                    int levels = interfaceItem.getLevels();

                                    if (levels > 0) {
                                        int playerLevel = player.getLevel();

                                        if (playerLevel >= levels) {
                                            player.setLevel(playerLevel - levels);
                                        } else {
                                            Formatter.sendMessage(player,
                                                    api.getConfig().getString("messages.no-levels")
                                                            .replace("%levels%", String.valueOf(levels)));
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
                                            Formatter.sendMessage(player,
                                                    api.getConfig().getString("messages.no-economy"));
                                            return;
                                        } else if (!vaultProvider.getEconomy().has(player, price)) {
                                            Formatter.sendMessage(player,
                                                    api.getConfig().getString("messages.no-balance")
                                                            .replace("%price%", String.valueOf(price)));
                                            return;
                                        }
                                    }

                                    interfacePlayer.setLastClick();

                                    ClickType click = event.getClick();

                                    interfaceItem.onClick(player, clickedInventory);
    
                                    if (click == ClickType.LEFT) {
                                        interfaceItem.onLeftClick(player, clickedInventory);
                                    }
    
                                    if (click == ClickType.RIGHT) {
                                        interfaceItem.onRightClick(player, clickedInventory);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
