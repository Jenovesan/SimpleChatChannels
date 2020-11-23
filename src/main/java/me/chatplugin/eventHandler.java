package me.chatplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class eventHandler implements Listener {

    @EventHandler
    private void playerChats(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        util.chatChannel channel = util.getPlayerChatChannel(player);
        event.setFormat(util.getFromConfig(channel + " Prefix") + ChatColor.RESET + " %s" + util.getFromConfig("Separator between name and message") + util.getFromConfig(channel + " message color") + " %s");
        switch (channel) {
            case TALK:
                event.getRecipients().clear();
                event.getRecipients().add(player);
                int talkRadius = config.getCustomChatConfig().getInt("TALK Radius");
                for (Entity entity : player.getNearbyEntities(talkRadius, talkRadius, talkRadius)) { if (entity instanceof Player) { event.getRecipients().add((Player) entity); } }
                if (event.getRecipients().size() == 1) {player.sendMessage(util.getFromConfig("Message when no one hears in TALK chat")); event.setCancelled(true);}
                break;
            case SC:
                event.getRecipients().clear();
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {if (onlinePlayer.hasPermission("staffChat.Access")) {event.getRecipients().add(onlinePlayer);}}
                break;
            case AD:
                main.getEconomy().withdrawPlayer(player, config.getCustomChatConfig().getInt("AD price"));
                break;
        }
    }

    @EventHandler
    private void playerJoin(PlayerJoinEvent event) { util.setPlayerChatChannel(event.getPlayer(), util.chatChannel.TALK); }

    @EventHandler
    private void playerLeave(PlayerQuitEvent event) { util.removePlayerChatChennel(event.getPlayer());}
}
