package de.unknown.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.unknown.main.Lobby;

public class DurchchillenEvents implements Listener {
	private final Lobby plugin;
	  private final List<String> blackList;

	  public DurchchillenEvents(Lobby plugin)
	  {
	    this.plugin = plugin;

	    this.blackList = this.plugin.getConfig().getStringList("durchchillen.blacklist");
	  }

	  @EventHandler
	  public void onPlayerJoin(PlayerJoinEvent e) {
	    final Player player = e.getPlayer();

	    e.setJoinMessage(null);

	    player.setOp(false);
	    player.setFlying(false);
	    player.setFlySpeed(0.1F);
	    player.setWalkSpeed(0.2F);
	    player.setFireTicks(0);

	    if (player.getGameMode() != GameMode.CREATIVE) {
	      player.setAllowFlight(false);
	    }

	    final String gamemodeOnJoin = this.plugin.getConfig().getString("durchchillen.gamemode-on-join");

	    if (!gamemodeOnJoin.isEmpty()) {
	      new BukkitRunnable() {
	        public void run() {
	          player.setGameMode(GameMode.valueOf(gamemodeOnJoin.toUpperCase()));
	        }
	      }
	      .runTaskLater(this.plugin, 2L);
	    }

	    Boolean spawnOnJoin = Boolean.valueOf(this.plugin.getConfig().getBoolean("durchchillen.spawn-on-join"));
	    String teleport = this.plugin.getConfig().getString("durchchillen.spawn");

	    if ((spawnOnJoin.booleanValue()) && (!teleport.isEmpty()) && (this.plugin.getConfig().isSet(teleport))) {
	      ConfigurationSection teleportData = this.plugin.getConfig().getConfigurationSection(teleport);

	      World world = Bukkit.getWorld(teleportData.getString("world"));
	      Double x = Double.valueOf(teleportData.getDouble("x"));
	      Double y = Double.valueOf(teleportData.getDouble("y"));
	      Double z = Double.valueOf(teleportData.getDouble("z"));
	      Float yaw = Float.valueOf((float)teleportData.getDouble("yaw"));
	      Float pitch = Float.valueOf((float)teleportData.getDouble("pitch"));

	      final Location loc = new Location(world, x.doubleValue(), y.doubleValue(), z.doubleValue(), yaw.floatValue(), pitch.floatValue());

	      new BukkitRunnable() {
	        public void run() {
	          player.teleport(loc);
	        }
	      }
	      .runTaskLater(this.plugin, 2L);
	    }
	  }

	  @EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e)
	  {
	    e.setQuitMessage(null);
	  }

	  @EventHandler
	  public void onPlayerInteract(PlayerInteractEvent e) {
	    Player player = e.getPlayer();

	    if (player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    boolean itemIsRedstone = player.getItemInHand().getType().toString().toUpperCase().startsWith("REDSTONE_");

	    if ((itemIsRedstone) && (player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.redstone")))) {
	      return;
	    }

	    if (this.blackList.contains(player.getItemInHand().getType().toString().toUpperCase()))
	      e.setCancelled(true);
	  }

	  @EventHandler
	  public void onPlayerDropItem(PlayerDropItemEvent e)
	  {
	    Player player = e.getPlayer();

	    if (player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    if (this.blackList.contains(e.getItemDrop().getItemStack().getType().toString().toUpperCase()))
	      e.setCancelled(true);
	  }

	  @EventHandler
	  public void onPlayerEditBook(PlayerEditBookEvent e)
	  {
	    Player player = e.getPlayer();

	    if (player.hasPermission(this.plugin.getConfig().getString("durchchillen.permissions.bypass"))) {
	      return;
	    }

	    e.setCancelled(true);
	  }

	  @EventHandler
	  public void onPlayerTeleport(PlayerTeleportEvent e) {
	    if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL)
	      e.setCancelled(true);
	  }

	  @SuppressWarnings({ "unchecked", "rawtypes" })
	@EventHandler
	  public void onCreatureSpawn(CreatureSpawnEvent e)
	  {
	    List blockedReasons = new ArrayList();

	    blockedReasons.add(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
	    blockedReasons.add(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG);
	    blockedReasons.add(CreatureSpawnEvent.SpawnReason.CUSTOM);
	    blockedReasons.add(CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM);
	    blockedReasons.add(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN);
	    blockedReasons.add(CreatureSpawnEvent.SpawnReason.BUILD_WITHER);

	    if (blockedReasons.contains(e.getSpawnReason())) {
	      e.setCancelled(true);
	    }

	    if (e.getEntityType() == EntityType.ARMOR_STAND) {
	      ArmorStand as = (ArmorStand)e.getEntity();
	      try
	      {
	        as.getItemInHand();
	        as.getHelmet();
	        as.getChestplate();
	        as.getLeggings();
	        as.getBoots();
	        as.getEquipment();
	        as.getEquipment().getArmorContents();
	        as.getEquipment().getHelmet();
	        as.getEquipment().getChestplate();
	        as.getEquipment().getLeggings();
	        as.getEquipment().getBoots();
	        as.getCustomName();
	      } catch (IllegalArgumentException ex) {
	        as.getEquipment().clear();
	        as.setCustomName(null);
	      }

	      String customName = as.getCustomName();

	      if ((customName != null) && (!customName.isEmpty()) && (!Pattern.matches("^[\\w\\ ]+$", customName)))
	        as.setCustomName(null);
	    }
	  }

	  @EventHandler
	  public void onWeatherChange(WeatherChangeEvent e)
	  {
	    if (!this.plugin.getConfig().getBoolean("durchchillen.weather"))
	      e.setCancelled(true);
	  }
}
