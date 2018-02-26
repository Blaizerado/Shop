package modul.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import moduls.holo.Holo;

public class onJoin implements Listener {

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		System.out.println("Join");
		Holo.showPlayer(e.getPlayer());
	}
	
}
