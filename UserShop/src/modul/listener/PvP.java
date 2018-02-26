package modul.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import modul.Utils.RegionUtil;

public class PvP implements Listener {

	@EventHandler
	public void onPVPListener(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(RegionUtil.isUserInRegion(p)) {
				e.setCancelled(true);
			}
		}
	}
	
}
