package modul.create;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.unknown.main.Shop;

public class creatShop {

	private static Shop main;

	@SuppressWarnings("static-access")
	public creatShop(Shop shop) {
		this.main = shop;
	}

	public static void create(Player p,String name, Location loc, Location loc1, World world) {
		File f = new File("plugins/UserShop/shops", name+".cfg");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		File configs = new File(main.getDataFolder().getPath()+"/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		if(!configs.exists()) {
			cfg1.set("Config.shops", null);
		}
		
		List<String>Shops = cfg1.getStringList("Config.shops");
		Shops.add(name);
		cfg1.set("Config.shops", Shops);
		
		try{cfg1.save(configs);}catch(Exception e) {}
		
		if(!f.exists()) {
			
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			
			cfg.set("Config.Location.world", world.getName());
			cfg.set("Config.Location.x1", loc.getBlockX());
			cfg.set("Config.Location.y1", loc.getBlockY());
			cfg.set("Config.Location.z1", loc.getBlockZ());
			
			cfg.set("Config.Location.x2", loc1.getBlockX());
			cfg.set("Config.Location.y2", loc1.getBlockY());
			cfg.set("Config.Location.z2", loc1.getBlockZ());
			
			cfg.set("Config.Name", name);
			cfg.set("Config.User", null);
			cfg.set("Config.Owner", "default");
			cfg.set("Config.Umsatzt", 0);
			cfg.set("Config.Schulden", 0);
			cfg.set("Config.Display", "default");
			cfg.set("Config.Create", df.format(new Date().getTime()));
			cfg.set("Config.Ownername", "default");
			p.sendMessage(Shop.prefix + "Der Shop §c" + name + "§7 wurde erstellt!");
			try {cfg.save(f);}catch(Exception e) {}
		}else {p.sendMessage(Shop.prefix + "§cDer Shop §e" + name + "§c ist bereits vorhanden!");}
	}
	
	public static void setDisplay(Player p, String name) {
		File f = new File("plugins/UserShop/shops", name+".cfg");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		if(f.exists()) {
			cfg.set("Config.Display", p.getLocation());
			try {cfg.save(f);}catch(Exception e) {}
			p.sendMessage(Shop.prefix + "Der Display für den Shop §c" + name + "§7 wurde gesetzt!!");
		}else {p.sendMessage(Shop.prefix + "§cDer Shop §e" + name + "§c ist nicht erstellt!");}
	}
	
	public static void setPrice(Player p, String name,Integer price) {
		File f = new File(main.getDataFolder().getPath()+"/shops", name+".cfg");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		if(f.exists()) {
			cfg.set("Config.Price", price);
			try {cfg.save(f);}catch(Exception e) {}
			p.sendMessage(Shop.prefix + "Der Preis für den Shop §c" + name + "§7 beträgt §c" + price + "§7$");
		}else {p.sendMessage(Shop.prefix + "§cDer Shop §e" + name + "§c ist nicht erstellt!");}
	}
}
