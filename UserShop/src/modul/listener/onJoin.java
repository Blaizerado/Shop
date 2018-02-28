package modul.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import modul.MySQL.Function;
import modul.holo.holo;

public class onJoin implements Listener {

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		System.out.println("Join");
		holo.showPlayer(e.getPlayer());
		if(!Function.playExists(e.getPlayer().getUniqueId().toString())) {
			Function.createPlayer(e.getPlayer().getUniqueId().toString());
		}
	}
	
}
