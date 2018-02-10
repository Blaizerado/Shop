package de.unknown.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.unknown.main.Lobby;

public class WorldProtectorEvents implements Listener {

	private Lobby plugin;

	public WorldProtectorEvents(Lobby lobby) {
		this.plugin = lobby;
	}

	@EventHandler
	  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
	    if (e.getPlayer().hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    e.setCancelled(true);
	  }

	  @EventHandler
	  public void onPlayerInteract(PlayerInteractEvent e) {
	    if (e.getPlayer().hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    e.setCancelled(true);
	  }

	  @EventHandler
	  public void onPlayerDropItem(PlayerDropItemEvent e) {
	    if (e.getPlayer().hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    e.setCancelled(true);
	  }

	  @EventHandler
	  public void onInventoryClick(InventoryClickEvent e) {
	    if (e.getWhoClicked().hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    e.setCancelled(true);
	  }

	  @EventHandler
	  public void onInventoryDrag(InventoryDragEvent e) {
	    if (e.getWhoClicked().hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    e.setCancelled(true);
	  }

	  @EventHandler
	  public void onEntityDamage(EntityDamageEvent e) {
	    e.setCancelled(true);

	    if ((e.getEntity() instanceof Player))
	      e.getEntity().setFireTicks(0);
	  }
	
}
