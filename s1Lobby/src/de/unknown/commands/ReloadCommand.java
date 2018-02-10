package de.unknown.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.unknown.main.Lobby;

public class ReloadCommand implements CommandExecutor {

	private Lobby plugin;

	public ReloadCommand(Lobby lobby) {
		this.plugin = lobby;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if ((sender instanceof Player)) {
	      Player player = (Player)sender;

	      if (!player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.admin"))) {
	        return true;
	      }
	    }

	    this.plugin.reloadConfig();

	    sender.sendMessage("Config reloaded.");

	    return true;
	  }

}
