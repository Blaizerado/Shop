package de.unknown.Listener;

import java.util.List;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.unknown.main.Lobby;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ScoreboardEvents implements Listener {

	private Lobby plugin;

	public ScoreboardEvents(Lobby lobby) {
		this.plugin = lobby;
	}

	@SuppressWarnings("rawtypes")
	@EventHandler
	  public void onPlayerJoin(PlayerJoinEvent e) {
	    Player player = e.getPlayer();

	    String title = this.plugin.getConfig().getString("scoreboard.title");
	    List lines = this.plugin.getConfig().getStringList("scoreboard.lines");

	    Scoreboard board = new Scoreboard();
	    ScoreboardObjective obj = board.registerObjective("scoreboard", IScoreboardCriteria.b);
	    obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));

	    PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
	    PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
	    PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

	    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(removePacket);
	    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(createPacket);
	    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(display);

	    String rank = "";
	    PermissionUser user;
	    if (this.plugin.getConfig().isSet("ranks.default.scoreboard")) {
	      rank = this.plugin.getConfig().getString("ranks.default.scoreboard");

	      user = PermissionsEx.getUser(player);
	      for (String key : this.plugin.getConfig().getConfigurationSection("ranks").getKeys(false)) {
	        if (user.inGroup(key, false)) {
	          rank = this.plugin.getConfig().getString("ranks." + key + ".scoreboard");
	          break;
	        }
	      }
	    }

	    int count = lines.size();

	    for (int i = 0; i < count; i++) {
	      String line = (String)lines.get(i);

	      line = line.replace("<player>", player.getName());
	      line = line.replace("<server>", this.plugin.getServer().getServerName());
	      line = line.replace("<rank>", rank);
	      line = ChatColor.translateAlternateColorCodes('&', line);

	      ScoreboardScore ss = new ScoreboardScore(board, obj, line);
	      ss.setScore(count - i);
	      PacketPlayOutScoreboardScore pposs = new PacketPlayOutScoreboardScore(ss);
	      ((CraftPlayer)player).getHandle().playerConnection.sendPacket(pposs);
	    }
	  }
	
}
