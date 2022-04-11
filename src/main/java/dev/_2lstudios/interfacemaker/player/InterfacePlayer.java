package dev._2lstudios.interfacemaker.player;

import org.bukkit.configuration.Configuration;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class InterfacePlayer {
    private int clickCooldown = 0;
    private int interactCooldown = 0;

    private long lastClick = 0;
    private long lastInteract = 0;
    
    public InterfacePlayer(InterfaceMakerAPI api) {
        Configuration config = api.getConfig();

        clickCooldown = config.getInt("anti-click-spam-delay");
        interactCooldown = config.getInt("anti-interact-spam-delay");
    }

    public long getLastClick() {
        return lastClick;
    }

    public void setLastClick() {
        this.lastClick = System.currentTimeMillis();
    }

    public long getLastInteract() {
        return lastInteract;
    }

    public void setLastInteract() {
        this.lastInteract = System.currentTimeMillis();
    }

    public boolean isClickCooling() {
        return System.currentTimeMillis() - lastClick <= clickCooldown;
    }

    public boolean isInteractCooling() {
        return System.currentTimeMillis() - lastInteract <= interactCooldown;
    }
}
