package de.unknown.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.unknown.main.Lobby;
import net.md_5.bungee.api.ChatColor;

public class ChatClearCommand implements CommandExecutor {

	private Lobby plugin;

	public ChatClearCommand(Lobby lobby) {
		this.plugin = lobby;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if ((sender instanceof Player)) {
	      Player player = (Player)sender;

	      if (!player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.chatclear"))) {
	        return true;
	      }

	      for (Player p : Bukkit.getOnlinePlayers()) {
	        for (int i = 0; i < 100; i++) {
	          p.sendMessage("");
	        }
	      }

	      String message = this.plugin.getConfig().getString("chat.messages.chatclear");
	      message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	      message = message.replace("<player>", player.getName());
	      message = ChatColor.translateAlternateColorCodes('&', message);
	      Bukkit.broadcastMessage(message);
	    }

	    return true;
	  }

}
