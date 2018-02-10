package de.unknown.Listener;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import de.unknown.main.Lobby;
import net.md_5.bungee.api.ChatColor;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TablistEvents implements Listener {

	private Lobby plugin;
	private Set<String>ranks;
	private Scoreboard sb;

	public TablistEvents(Lobby plugin)
	  {
	    this.plugin = plugin;

	    this.ranks = this.plugin.getConfig().getConfigurationSection("ranks").getKeys(false);
	    this.sb = Bukkit.getScoreboardManager().getNewScoreboard();

	    int i = 1;

	    for (String key : this.ranks) {
	      String num = i < 10 ? "0" + i : String.valueOf(i);
	      String prefix = this.plugin.getConfig().getString("ranks." + key + ".tablist");
	      this.sb.registerNewTeam(num + key).setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
	      i++;
	    }
	  }

	  @SuppressWarnings("deprecation")
	@EventHandler
	  public void onPlayerJoin(PlayerJoinEvent e) {
	    Player player = e.getPlayer();

	    PermissionUser user = PermissionsEx.getUser(player);

	    String rank = "";
	    int i = 1;

	    for (String key : this.ranks) {
	      String num = i < 10 ? "0" + i : String.valueOf(i);
	      rank = num + key;
	      if (user.inGroup(key, false)) {
	        break;
	      }
	      i++;
	    }

	    this.sb.getTeam(rank).addPlayer(player);
	    player.setDisplayName(this.sb.getTeam(rank).getPrefix() + player.getName());
	    for (Player p : Bukkit.getOnlinePlayers())
	      p.setScoreboard(this.sb);
	  }
	
}
