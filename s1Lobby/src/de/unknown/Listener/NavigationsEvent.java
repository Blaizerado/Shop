package de.unknown.Listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.unknown.main.Lobby;
import net.md_5.bungee.api.ChatColor;

public class NavigationsEvent implements Listener {

	private final Lobby main;
	private Map<String, Integer>lastTeleports = new HashMap<>();
	
	public NavigationsEvent(Lobby lobby) {
		this.main = lobby;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		this.lastTeleports.put(p.getName(), Integer.valueOf(1));
		
		p.getInventory().setHeldItemSlot(0);
		p.getInventory().clear();
		
		if(this.main.getConfig().isSet("navigator.hotbar.default"))
		for(int i = 0; i < 9; i++) {
			ConfigurationSection itemData = this.main.getConfig().getConfigurationSection("navigator.hotbar.default");
			
			if(this.main.getConfig().isSet("navigator.hotbar." + i)) {
				itemData = this.main.getConfig().getConfigurationSection("navigator.hotbar." + i);
			}
			
			String itemName = itemData.getString("item");
			String itemTitle = itemData.getString("title");
			ItemStack item;
			
			if(itemName.contains(";")) {
				int damage = Integer.parseInt(itemName.replaceAll(".*;", ""));
				itemName = itemName.replaceAll(";.*", "");
				item = new ItemStack(Material.valueOf(itemName.toUpperCase()), 1, (short)damage);
			}else {
				item = new ItemStack(Material.valueOf(itemName.toUpperCase()), 1);
			}
			if((itemTitle != null) && (!itemTitle.isEmpty())) {
				ItemMeta im = item.getItemMeta();
				itemTitle = ChatColor.translateAlternateColorCodes('&', itemTitle);
				im.setDisplayName(ChatColor.RESET + itemTitle);
				item.setItemMeta(im);
			}
			p.getInventory().setItem(i, item);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		if(e.getCurrentItem() == null) {return;}
		if(e.getInventory() == null) {return;}
		if(e.getInventory().getType().equals(InventoryType.PLAYER)) {e.setCancelled(true);}
		if(e.getCurrentItem().getItemMeta() == null) {return;}
		
		if(e.getInventory().getName().equalsIgnoreCase("§eTeleporter")) {
			e.setCancelled(true);
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eCity-Build")) {
				Teleporter(player, "citybuildportal");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSkyBlock")) {
				Teleporter(player, "skyblockportal");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Kreative")) {
				Teleporter(player, "creativeportal");
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cSpawn")) {
				Teleporter(player, "spawn");
			}
			player.closeInventory();
		}
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(!player.hasPermission("durchchillen.permissions.bypass")) {
			e.setCancelled(true);
		}
		
		if(!e.getAction().toString().startsWith("RIGHT_CLICK_")) {
			return;
		}
		if(player.getInventory().getItemInHand().getType().equals(Material.COMPASS)) {
			Inventory inv = Bukkit.createInventory(null, 54, "§eTeleporter");
			
			ItemStack[] items = {new ItemStack(Material.DIAMOND_PICKAXE),new ItemStack(Material.GOLD_PICKAXE),new ItemStack(Material.BRICK),new ItemStack(Material.BARRIER)};
			String[] names = {"§eCity-Build","§aSkyBlock","§3Kreative","§cSpawn"};
			HashMap<Integer, Integer>Slots = new HashMap<>();
			
			Slots.put(0, 19);
			Slots.put(1,22);
			Slots.put(2,25);
			Slots.put(3,40);
			for(int i = 0; i < 4; i++) {
				ItemStack it = items[i];
				ItemMeta im = it.getItemMeta();
				im.setDisplayName(names[i]);
				im.setLore(null);
				im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				it.setItemMeta(im);
				inv.setItem(Slots.get(i), it);
			}
			player.openInventory(inv);
		}
	}
	
	public void Teleporter(Player p, String s) {
		ConfigurationSection teleportData = this.main.getConfig().getConfigurationSection("navigator.teleports." + s);
		if ((teleportData == null) || (teleportData.getKeys(false).size() == 0)) {
		      String message = this.main.getConfig().getString("navigator.messages.teleport-not-exist");
		      message = message.replace("<prefix>", this.main.getConfig().getString("durchchillen.prefix"));
		      message = message.replace("<name>", "citybuildportal");
		      message = ChatColor.translateAlternateColorCodes('&', message);
		      p.sendMessage(message);
		      return;
		  }
		
		Integer timeDiff = Integer.valueOf((int)(System.currentTimeMillis() / 1000L) - ((Integer)this.lastTeleports.get(p.getName())).intValue());
		
		if(timeDiff.intValue() <= this.main.getConfig().getInt("navigator.teleport-cooldown")) {
			int seconds = this.main.getConfig().getInt("navigator.teleport-cooldown") - timeDiff.intValue() + 1;

		      String message = this.main.getConfig().getString("navigator.messages.please-wait");
		      message = message.replace("<prefix>", this.main.getConfig().getString("durchchillen.prefix"));
		      message = message.replace("<seconds>", String.valueOf(seconds));
		      message = ChatColor.translateAlternateColorCodes('&', message);
		      p.sendMessage(message);
		      return;
		}
		
		World world = Bukkit.getWorld(teleportData.getString("world"));
		Double x = Double.valueOf(teleportData.getDouble("x"));
		Double y = Double.valueOf(teleportData.getDouble("y"));
		Double z = Double.valueOf(teleportData.getDouble("z"));
		Float yaw = Float.valueOf((float)teleportData.getDouble("yaw"));
		Float pitch = Float.valueOf((float)teleportData.getDouble("pitch"));
		p.teleport(new Location(world, x.doubleValue(), y.doubleValue(), z.doubleValue(),yaw.floatValue(),pitch.floatValue()));
		
		String message = this.main.getConfig().getString("navigator.messages.teleported");
	    message = message.replace("<prefix>", this.main.getConfig().getString("durchchillen.prefix"));
	    message = ChatColor.translateAlternateColorCodes('&', message);
	    p.sendMessage(message);

	    this.lastTeleports.put(p.getName(), Integer.valueOf((int)(System.currentTimeMillis() / 1000L)));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if(this.lastTeleports.containsKey(e.getPlayer().getName())) {
			this.lastTeleports.remove(e.getPlayer().getName());
		}
	}
	
}
