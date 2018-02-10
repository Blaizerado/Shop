package de.unknown.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import de.unknown.main.Lobby;
import net.md_5.bungee.api.ChatColor;

public class TeleportsCommand implements CommandExecutor {

	private Lobby plugin;
	private Player player;

	public TeleportsCommand(Lobby lobby) {
		this.plugin = lobby;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if ((sender instanceof Player)) {
	      this.player = ((Player)sender);

	      if (!this.player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.admin"))) {
	        return true;
	      }

	      if ((args.length == 2) && (args[0].equalsIgnoreCase("add"))) {
	        addTeleport(args[1]);
	        return true;
	      }if ((args.length == 2) && (args[0].equalsIgnoreCase("del"))) {
	        delTeleport(args[1]);
	        return true;
	      }if ((args.length == 2) && (args[0].equalsIgnoreCase("test"))) {
	        testTeleport(args[1]);
	        return true;
	      }if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
	        listTeleports();
	        return true;
	      }

	      return false;
	    }

	    return true;
	  }

	  private void addTeleport(String name) {
	    Location loc = this.player.getLocation();

	    this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).append(".world").toString(), loc.getWorld().getName());
	    this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).append(".x").toString(), Double.valueOf(loc.getX()));
	    this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).append(".y").toString(), Double.valueOf(loc.getY()));
	    this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).append(".z").toString(), Double.valueOf(loc.getZ()));
	    this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).append(".yaw").toString(), Float.valueOf(loc.getYaw()));
	    this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).append(".pitch").toString(), Float.valueOf(loc.getPitch()));

	    this.plugin.saveConfig();

	    String message = this.plugin.getConfig().getString("navigator.messages.teleport-set");
	    message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	    message = message.replace("<name>", name);
	    message = ChatColor.translateAlternateColorCodes('&', message);
	    this.player.sendMessage(message);
	  }

	  private void delTeleport(String name)
	  {
	    String message;
	    if (this.plugin.getConfig().isSet(new StringBuilder().append("navigator.teleports.").append(name).toString())) {
	      this.plugin.getConfig().set(new StringBuilder().append("navigator.teleports.").append(name).toString(), null);

	      if (this.plugin.getConfig().getConfigurationSection("navigator.teleports").getKeys(false).size() == 0) {
	        this.plugin.getConfig().set("navigator.teleports", null);
	      }

	      this.plugin.saveConfig();

	      message = this.plugin.getConfig().getString("navigator.messages.teleport-removed");
	    } else {
	      message = this.plugin.getConfig().getString("navigator.messages.teleport-not-exist");
	    }

	    message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	    message = message.replace("<name>", name);
	    message = ChatColor.translateAlternateColorCodes('&', message);

	    this.player.sendMessage(message);
	  }

	  private void testTeleport(String name)
	  {
	    String message;
	    if (this.plugin.getConfig().isSet(new StringBuilder().append("navigator.teleports.").append(name).toString())) {
	      ConfigurationSection teleportData = this.plugin.getConfig().getConfigurationSection(new StringBuilder().append("navigator.teleports.").append(name).toString());

	      World world = Bukkit.getWorld(teleportData.getString("world"));
	      Double x = Double.valueOf(teleportData.getDouble("x"));
	      Double y = Double.valueOf(teleportData.getDouble("y"));
	      Double z = Double.valueOf(teleportData.getDouble("z"));
	      Float yaw = Float.valueOf((float)teleportData.getDouble("yaw"));
	      Float pitch = Float.valueOf((float)teleportData.getDouble("pitch"));

	      this.player.teleport(new Location(world, x.doubleValue(), y.doubleValue(), z.doubleValue(), yaw.floatValue(), pitch.floatValue()));

	      message = this.plugin.getConfig().getString("navigator.messages.teleport-tested");
	    } else {
	      message = this.plugin.getConfig().getString("navigator.messages.teleport-not-exist");
	    }

	    message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	    message = message.replace("<name>", name);
	    message = ChatColor.translateAlternateColorCodes('&', message);

	    this.player.sendMessage(message);
	  }

	  private void listTeleports()
	  {
	    StringBuilder teleports = new StringBuilder();
	    String message;
	    if ((this.plugin.getConfig().isSet("navigator.teleports")) && (this.plugin.getConfig().getConfigurationSection("navigator.teleports").getKeys(false).size() > 0)) {
	      for (String teleport : this.plugin.getConfig().getConfigurationSection("navigator.teleports").getKeys(false)) {
	        teleports.append(teleport).append(" ");
	      }

	      message = this.plugin.getConfig().getString("navigator.messages.teleports");
	    } else {
	      message = this.plugin.getConfig().getString("navigator.messages.no-teleports");
	    }

	    message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	    message = message.replace("<teleports>", teleports);
	    message = ChatColor.translateAlternateColorCodes('&', message);

	    this.player.sendMessage(message);
	  }

}
