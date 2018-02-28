package modul.Utils;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.unknown.main.Shop;

public class RegionUtil {


	private static Shop main;

	@SuppressWarnings("static-access")
	public RegionUtil(Shop shop) {
		this.main = shop;
	}

	public static String getRegionOwner(Block b) {
		
		String name = null;
		
		File configs = new File(main.getDataFolder().getPath()+"/shop", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(b.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= b.getX() && x2 >= b.getX()) || (x1 >= b.getX() && x2 <= b.getX())) && (( y1 <= b.getY() && y2 >= b.getY()) || (y1 >= b.getY() && y2 <= b.getY())) &&
							(( z1 <= b.getZ() && z2 >= b.getZ()) || (z1 >= b.getZ() && z2 <= b.getZ()))){
						name = cfg.getString("Config.Owner");
						break;
					}
				}
			}
		}
		return name;
	}
	
	public static boolean isRegion(Block b) {
		
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(b.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= b.getX() && x2 >= b.getX()) || (x1 >= b.getX() && x2 <= b.getX())) && (( y1 <= b.getY() && y2 >= b.getY()) || (y1 >= b.getY() && y2 <= b.getY())) &&
							(( z1 <= b.getZ() && z2 >= b.getZ()) || (z1 >= b.getZ() && z2 <= b.getZ()))){
						return true;
						
					}
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void getRegionInfo(Location loc, Player p) {
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		System.out.println("test");
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(loc.getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= loc.getX() && x2 >= loc.getX()) || (x1 >= loc.getX() && x2 <= loc.getX())) && (( y1 <= loc.getY() && y2 >= loc.getY()) || (y1 >= loc.getY() && y2 <= loc.getY())) &&
							(( z1 <= loc.getZ() && z2 >= loc.getZ()) || (z1 >= loc.getZ() && z2 <= loc.getZ()))){
						
						Integer price = cfg.getInt("Config.Price");
						Integer umsatzt = cfg.getInt("Config.Umsatzt");
						Integer schulden = cfg.getInt("Config.Schulden");
						String create = cfg.getString("Config.Create");
						p.sendMessage(main.prefix + "Besitzer: §3" + Bukkit.getOfflinePlayer(cfg.getString("Config.Ownername")).getName());
						p.sendMessage(main.prefix + "Shop: §3" + s);
						p.sendMessage(main.prefix + "Preis: §3" + price);
						p.sendMessage(main.prefix + "Umsatzt: §3" + umsatzt);
						p.sendMessage(main.prefix + "Schulden: §3" + schulden);
						p.sendMessage(main.prefix + "Erstellt: §3" + create);
					}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!");}
				}else {p.sendMessage(main.prefix + "§cWorld ist nicht erstellt!");}
			}else {p.sendMessage(main.prefix + "§cDer shop wurde nicht erstellt!");}
		}
	}
	
	public static boolean isUserInRegion(Player p) {
		
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(p.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= p.getLocation().getX() && x2 >= p.getLocation().getX()) || (x1 >= p.getLocation().getX() && 
							x2 <= p.getLocation().getX())) && (( y1 <= p.getLocation().getY() && y2 >= p.getLocation().getY()) || (y1 >= p.getLocation().getY() 
							&& y2 <= p.getLocation().getY())) &&
							(( z1 <= p.getLocation().getZ() && z2 >= p.getLocation().getZ()) || (z1 >= p.getLocation().getZ() && z2 <= p.getLocation().getZ()))){
						return true;
						
					}
				}
			}
		}
		
		return false;
		
	}
	
	public static String getRegion(Player p) {
		String region = null;
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(p.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= p.getLocation().getX() && x2 >= p.getLocation().getX()) || (x1 >= p.getLocation().getX() && 
							x2 <= p.getLocation().getX())) && (( y1 <= p.getLocation().getY() && y2 >= p.getLocation().getY()) || (y1 >= p.getLocation().getY() 
							&& y2 <= p.getLocation().getY())) &&
							(( z1 <= p.getLocation().getZ() && z2 >= p.getLocation().getZ()) || (z1 >= p.getLocation().getZ() && z2 <= p.getLocation().getZ()))){
							region = cfg.getString("Config.Name");
					}
				}
			}
		}
		
		return region;
		
	}
	
	public static boolean isUserOwner(Player p) {
		
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				String owner = cfg.getString("Config.Owner");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(p.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= p.getLocation().getX() && x2 >= p.getLocation().getX()) || (x1 >= p.getLocation().getX() && 
							x2 <= p.getLocation().getX())) && (( y1 <= p.getLocation().getY() && y2 >= p.getLocation().getY()) || (y1 >= p.getLocation().getY() 
							&& y2 <= p.getLocation().getY())) &&
							(( z1 <= p.getLocation().getZ() && z2 >= p.getLocation().getZ()) || (z1 >= p.getLocation().getZ() && z2 <= p.getLocation().getZ()))){
						
						if(owner.toString().equalsIgnoreCase(p.getUniqueId().toString())) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
		
	}
	
    public static List<String> getUser(Player p) {
		
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		List<String>User = null;
		List<String>Shops = cfg1.getStringList("Config.shops");
		
		for(String s : Shops) {
			File f = new File(main.getDataFolder().getPath()+"/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				String world = cfg.getString("Config.Location.world");
				int x1 = cfg.getInt("Config.Location.x1");
				int y1 = cfg.getInt("Config.Location.y1");
				int z1 = cfg.getInt("Config.Location.z1");
				
				
				int x2 = cfg.getInt("Config.Location.x2");
				int y2 = cfg.getInt("Config.Location.y2");
				int z2 = cfg.getInt("Config.Location.z2");
				
				
				if(p.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
					if((( x1 <= p.getLocation().getX() && x2 >= p.getLocation().getX()) || (x1 >= p.getLocation().getX() && 
							x2 <= p.getLocation().getX())) && (( y1 <= p.getLocation().getY() && y2 >= p.getLocation().getY()) || (y1 >= p.getLocation().getY() 
							&& y2 <= p.getLocation().getY())) &&
							(( z1 <= p.getLocation().getZ() && z2 >= p.getLocation().getZ()) || (z1 >= p.getLocation().getZ() && z2 <= p.getLocation().getZ()))){
						
						User = cfg.getStringList("Config.User");
					}
				}
			}
		}
		
		return User;
		
	}
	
}
