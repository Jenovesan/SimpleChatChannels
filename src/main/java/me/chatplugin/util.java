package me.chatplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;

public class util {

    public enum chatChannel {TALK, GLOBAL, AD, SC}

    public static HashMap<Player, chatChannel> playerChatChannel = new HashMap<>();

    public static chatChannel getPlayerChatChannel(Player player) { return playerChatChannel.get(player); }

    public static void setPlayerChatChannel(Player player, chatChannel channel) { playerChatChannel.put(player, channel); }

    public static void removePlayerChatChennel(Player player) {playerChatChannel.remove(player);}

    public static String getFromConfig(String path) {return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getCustomChatConfig().getString(path)));}
}
