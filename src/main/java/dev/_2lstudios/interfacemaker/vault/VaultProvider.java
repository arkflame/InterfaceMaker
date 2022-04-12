package dev._2lstudios.interfacemaker.vault;

import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class VaultProvider {
    private Economy economy = null;

    public VaultProvider(Server server) {
        if (server.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);

            if (rsp != null) {
                economy = rsp.getProvider();
            }
        }
    }

    public boolean isEconomyRegistered() {
        return economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }
}
