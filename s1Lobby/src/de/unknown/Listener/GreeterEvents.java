package de.unknown.Listener;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.unknown.main.Lobby;
import net.md_5.bungee.api.ChatColor;

public class GreeterEvents implements Listener {


	private Lobby main;

	public GreeterEvents(Lobby lobby) {
		this.main = lobby;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();
		
		e.setJoinMessage(null);
		
		List<String>message = this.main.getConfig().getStringList("greeter.chatmessages");
		
		if(message.size() > 0) {
			for(int i = 0; i < 100; i++) {
				player.sendMessage(" ");
			}
			for(String line : message) {
				line = line.replace("<prefix>", this.main.getConfig().getString("durchchillen.prefix"));
		        line = line.replace("<player>", player.getName());
		        line = ChatColor.translateAlternateColorCodes('&', line);
		        player.sendMessage(line);
			}
			for (int i = 0; i < 2; i++) {
		        player.sendMessage("");
		      }
		}
		
		String screenmessageline1 = this.main.getConfig().getString("greeter.screenmessages.line1");
	    String screenmessageline2 = this.main.getConfig().getString("greeter.screenmessages.line2");
	    
	    screenmessageline1 = ChatColor.translateAlternateColorCodes('&', screenmessageline1);
	    screenmessageline2 = ChatColor.translateAlternateColorCodes('&', screenmessageline2);
	    
	    if ((!screenmessageline1.isEmpty()) && (!screenmessageline2.isEmpty())) {
	        final String finalScreenmessageline1 = screenmessageline1;
	        final String finalScreenmessageline2 = screenmessageline2;

	        new BukkitRunnable() {
	          @SuppressWarnings("deprecation")
			public void run() {
	            player.sendTitle(finalScreenmessageline1, finalScreenmessageline2);
	          }
	        }
	        .runTaskLater(this.main, 30L);
	      }
	}
	
}
