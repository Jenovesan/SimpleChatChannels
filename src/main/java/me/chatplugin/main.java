package me.chatplugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    private static Economy econ = null;
    private static main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        main.instance = this;
        (new config()).setup();
        getServer().getPluginManager().registerEvents(new eventHandler(), this);
        setupCommands();
        if (!setupEconomy() ) {
            getServer().getConsoleSender().sendMessage(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Chat Plugin has successfully been enabled");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void setupCommands() {
        this.getCommand("global").setExecutor(new commands());
        this.getCommand("talk").setExecutor(new commands());
        this.getCommand("sc").setExecutor(new commands());
        this.getCommand("ad").setExecutor(new commands());
    }

    public static Economy getEconomy() { return econ; }

    public static main getInstance() {return main.instance;}
}
