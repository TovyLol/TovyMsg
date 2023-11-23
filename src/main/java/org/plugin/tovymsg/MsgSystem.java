package org.plugin.tovymsg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MsgSystem implements CommandExecutor {

    private Map<UUID, UUID> lastMessageSenderMap = new HashMap<>();
    private ReplyCommand replyCommand;

    public MsgSystem() {
        this.replyCommand = new ReplyCommand(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 2) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);

                if (targetPlayer == null) {
                    p.sendMessage(ChatColor.RED + "Player not found, try again later");
                    return true;
                }

                StringBuilder message = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }

                String string = ChatColor.UNDERLINE.GRAY + message.toString();
                String formattedMessage = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + p.getName() + " -> You" + ChatColor.DARK_GRAY + "] " + ChatColor.UNDERLINE.GRAY + message.toString();

                targetPlayer.sendMessage(formattedMessage);

                p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "You -> " + targetPlayer.getName() + ChatColor.DARK_GRAY + "] " + ChatColor.UNDERLINE.GRAY + message.toString());
                lastMessageSenderMap.put(targetPlayer.getUniqueId(), p.getUniqueId());

                return true;

            } else if (args.length < 2) {
                p.sendMessage(ChatColor.BLUE + "Use /msg <player> <message>!");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            } else {
                p.sendMessage("You have to set an argument");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }

        return false;
    }

    public Map<UUID, UUID> getLastMessageSenderMap() {
        return lastMessageSenderMap;
    }

    public ReplyCommand getReplyCommand() {
        return replyCommand;
    }
}

