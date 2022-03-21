package dev._2lstudios.interfacemaker.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceInventory;
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

            for (InterfaceInventory inventory : api.getConfiguredInventories()) {
                Action action = event.getAction();
                boolean isActionLeft = action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
                boolean isActionRight = action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;

                if (inventory.getOpenWithItemMaterial() == material &&
                        ((inventory.isOpenWithItemLeftClick() && isActionLeft) ||
                                (inventory.isOpenWithItemRightClick() && isActionRight))) {
                    inventory.build(player);
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

                interfaceItem.onInteract(player);
            }
        }
    }
}
