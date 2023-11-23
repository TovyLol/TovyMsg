package org.plugin.tovymsg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.Map;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    private final MsgSystem plugin;

    public ReplyCommand(MsgSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            UUID senderUUID = p.getUniqueId();
            Map<UUID, UUID> lastMessageSenderMap = plugin.getLastMessageSenderMap();

            if (!lastMessageSenderMap.containsKey(senderUUID)) {
                p.sendMessage(ChatColor.RED + "No recent messages to reply to.");
                return true;
            }

            UUID lastSenderUUID = lastMessageSenderMap.get(senderUUID);
            Player lastSender = Bukkit.getPlayer(lastSenderUUID);

            if (lastSender == null) {
                p.sendMessage(ChatColor.RED + "The player you are replying to is no longer online.");
                lastMessageSenderMap.remove(senderUUID); // Remove outdated entry
                return true;
            }

            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }

            String formattedMessage = ChatColor.DARK_GRAY + "[You -> " + lastSender.getName() + "] " + ChatColor.UNDERLINE.GRAY + message.toString();
            lastSender.sendMessage(formattedMessage);

            p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "You -> " + lastSender.getName() + ChatColor.DARK_GRAY + "] " + ChatColor.UNDERLINE.GRAY + message.toString());

            return true;
        }

        return false;
    }
}

