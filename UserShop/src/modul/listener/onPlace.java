package modul.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.unknown.main.Shop;
import modul.Utils.RegionUtil;

public class onPlace implements Listener {

	@EventHandler
	public void onPlaceListener(BlockPlaceEvent e) {
		if(RegionUtil.isRegion(e.getBlock())) {
			if(RegionUtil.isUserOwner(e.getPlayer()) || RegionUtil.getUser(e.getPlayer()).contains(e.getPlayer().getUniqueId().toString())) {
				
			}else {if(!e.getPlayer().hasPermission("shop.bypass")) {e.setCancelled(true); e.getPlayer().sendMessage(Shop.prefix + "§cDieser Shop gehört dir nicht!");}}
		}
	}
	
	@EventHandler
	public void onBrackeBlock(BlockBreakEvent e) {
		if(RegionUtil.isRegion(e.getBlock())) {
			if(RegionUtil.isUserOwner(e.getPlayer()) || RegionUtil.getUser(e.getPlayer()).contains(e.getPlayer().getUniqueId().toString())) {
				
			}else {if(!e.getPlayer().hasPermission("shop.bypass")) {e.setCancelled(true); e.getPlayer().sendMessage(Shop.prefix + "§cDieser Shop gehört dir nicht!");}}
		}
	}
}
