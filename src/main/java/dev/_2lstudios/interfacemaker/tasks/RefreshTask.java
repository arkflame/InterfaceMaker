package dev._2lstudios.interfacemaker.tasks;

import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;
import dev._2lstudios.interfacemaker.interfaces.contexts.HotbarBuildContext;
import dev._2lstudios.interfacemaker.interfaces.contexts.MenuBuildContext;

public class RefreshTask implements Runnable {
    private InterfaceMakerAPI api;
    private int ticks = 0;

    public RefreshTask(InterfaceMakerAPI api) {
        this.api = api;
    }

    @Override
    public void run() {
        ticks++;

        for (Entry<Inventory, MenuBuildContext> entry : api.getOpenedMenuContexts().entrySet()) {
            MenuBuildContext context = entry.getValue();
            Player player = context.getPlayer();
            int autoRefresh = context.getMenu().getAutoRefresh();

            if (autoRefresh > 0 && ticks % autoRefresh == 0) {
                context.populateItems(player, context.getInventory());
            }
        }

        for (Entry<Player, HotbarBuildContext> entry : api.getOpenedHotbarContexts().entrySet()) {
            Player player = entry.getKey();
            HotbarBuildContext context = entry.getValue();
            int autoRefresh = context.getHotbar().getAutoRefresh();

            if (autoRefresh > 0 && ticks % autoRefresh == 0) {
                context.populateItems(player, context.getInventory());
            }
        }
    }
}
