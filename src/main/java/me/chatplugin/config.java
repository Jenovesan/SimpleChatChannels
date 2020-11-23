package me.chatplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class config {
    public static FileConfiguration CustomChatConfig;
    public static File fileCustomChat;

    public void setup() {
        fileCustomChat = new File(Bukkit.getServer().getPluginManager().getPlugin(main.getInstance().getName()).getDataFolder(), "CustomChatConfig.yml");
        if (!fileCustomChat.exists()) {
            try {
                fileCustomChat.createNewFile();
            }catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "A new CustomChatConfig file has been created");
            }
        }
        CustomChatConfig = YamlConfiguration.loadConfiguration(fileCustomChat);
        loadCustomChatDefaults();
    }

    public static FileConfiguration getCustomChatConfig() {
        return CustomChatConfig;
    }

    public static void saveCustomChatConfig() {
        try {
            CustomChatConfig.save(fileCustomChat);
        }catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error when saving CustomChat configuration file");
        }
    }

    public static void reloadCustomChatConfig() {
        CustomChatConfig = YamlConfiguration.loadConfiguration(fileCustomChat);
    }

    private void loadCustomChatDefaults() {
        getCustomChatConfig().options().header("The permission for seeing and sending messages in staff chat is: staffChat.Access");
        getCustomChatConfig().addDefault("Entered new channel message", "&aYou have entered CHANNEL_NAME chat");
        getCustomChatConfig().addDefault("Separator between name and message", "&f:");
        getCustomChatConfig().addDefault("TALK Radius", 15);
        getCustomChatConfig().addDefault("TALK Prefix", "&9[Talk]");
        getCustomChatConfig().addDefault("TALK message color", "&7");
        getCustomChatConfig().addDefault("Message when no one hears in TALK chat", "&cNo one hears you");
        getCustomChatConfig().addDefault("GLOBAL Prefix", "&2[G]");
        getCustomChatConfig().addDefault("GLOBAL message color", "&7");
        getCustomChatConfig().addDefault("SC Prefix", "&3[SC]");
        getCustomChatConfig().addDefault("SC message color", "&7");
        getCustomChatConfig().addDefault("AD Prefix", "&6[AD]");
        getCustomChatConfig().addDefault("AD message color", "&6&l");
        getCustomChatConfig().addDefault("AD price", 100);
        getCustomChatConfig().addDefault("Cannot afford an add message", "&4You don't have the money to post an advertisement");
        getCustomChatConfig().options().copyDefaults(true);
        saveCustomChatConfig();
    }
}
