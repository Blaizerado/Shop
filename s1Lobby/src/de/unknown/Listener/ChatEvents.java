package de.unknown.Listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.unknown.main.Lobby;
import net.md_5.bungee.api.ChatColor;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatEvents implements Listener {


	private final Lobby plugin;
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, String> lastMessages = new HashMap();

	  public ChatEvents(Lobby plugin) {
	    this.plugin = plugin;
	  }

	  @EventHandler
	  public void onCommand(PlayerCommandPreprocessEvent e) {
	    Player player = e.getPlayer();
	    String msg = e.getMessage();

	    if (player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.team"))) {
	      return;
	    }

	    e.setCancelled(true);

	    if (this.plugin.getConfig().isSet("chat.allowed-commands")) {
	      List<String>allowedCommands = this.plugin.getConfig().getStringList("chat.allowed-commands");

	      String[] splittedMsg = msg.split(" ");

	      if (allowedCommands.contains(splittedMsg[0]))
	        e.setCancelled(false);
	    }
	  }

	  @SuppressWarnings("rawtypes")
	  @EventHandler
	  public void onChat(AsyncPlayerChatEvent e)
	  {
	    Player player = e.getPlayer();
	    String msg = e.getMessage().replace("%", "%%");

	    String rank = this.plugin.getConfig().getString("ranks.default.chat", "");
	    PermissionUser user;
	    Iterator localIterator;
	    String key;
	    
	    if (this.plugin.getConfig().isConfigurationSection("ranks")) {
	      user = PermissionsEx.getUser(player.getName());

	      for (localIterator = this.plugin.getConfig().getConfigurationSection("ranks").getKeys(false).iterator(); localIterator.hasNext(); ) { key = (String)localIterator.next();
	        if (user.inGroup(key, false)) {
	          rank = this.plugin.getConfig().getString("ranks." + key + ".chat");
	          break;
	        }
	      }
	    }
	    String format = rank + player.getName() + " " + this.plugin.getConfig().getString("chat.separator") + " " + msg;
	    format = ChatColor.translateAlternateColorCodes('&', format);
	    e.setFormat(format);

	    if (player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    List<String>blockedWords = this.plugin.getConfig().getStringList("chat.blocked-words");

	    if ((blockedWords != null) && (blockedWords.size() > 0)) {
	      for (String word : blockedWords) {
	        if (msg.toLowerCase().contains(word.toLowerCase())) {
	          e.setCancelled(true);

	          String message = this.plugin.getConfig().getString("chat.messages.bad-word");
	          message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	          message = ChatColor.translateAlternateColorCodes('&', message);
	          player.sendMessage(message);

	          return;
	        }
	      }
	    }

	    if ((this.lastMessages.containsKey(player.getName())) && (((String)this.lastMessages.get(player.getName())).equalsIgnoreCase(msg))) {
	      e.setCancelled(true);

	      String message = this.plugin.getConfig().getString("chat.messages.once-only");
	      message = message.replace("<prefix>", this.plugin.getConfig().getString("durchchillen.prefix"));
	      message = ChatColor.translateAlternateColorCodes('&', message);
	      player.sendMessage(message);

	      return;
	    }

	    this.lastMessages.put(player.getName(), msg);
	  }
	
}
