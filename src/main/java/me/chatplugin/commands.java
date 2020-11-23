package me.chatplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String commandName = command.getName();
        util.chatChannel currentChannel = util.getPlayerChatChannel(player);
        util.chatChannel newChannel = util.chatChannel.valueOf(commandName.toUpperCase());
        if (newChannel.equals(util.chatChannel.SC) && !player.hasPermission("staffChat.Access"))  {player.sendMessage(ChatColor.RED + "You do not have access to this chat channel"); return true;}
        if (newChannel.equals(util.chatChannel.AD) && main.getEconomy().getBalance(player) < config.getCustomChatConfig().getInt("AD price"))
            {player.sendMessage(util.getFromConfig("Cannot afford an add message")); return true;}
        if (!newChannel.equals(util.chatChannel.AD) && args.length == 0) {
            if (currentChannel.equals(newChannel)) {
                util.setPlayerChatChannel(player, util.chatChannel.TALK);
                player.sendMessage(util.getFromConfig("Entered new channel message").replace("CHANNEL_NAME", "TALK"));
            } else {
                util.setPlayerChatChannel(player, newChannel);
                player.sendMessage(util.getFromConfig("Entered new channel message").replace("CHANNEL_NAME", newChannel.toString()));
            }
            return true;
        }
        util.setPlayerChatChannel(player, newChannel);
        player.chat(String.join(" ",args));
        util.setPlayerChatChannel(player, currentChannel);
        return true;
    }
}
