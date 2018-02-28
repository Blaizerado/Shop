package modul.holo;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.unknown.main.Shop;

public class loadholos {

	@SuppressWarnings("unused")
	private static Shop main;

	@SuppressWarnings("static-access")
	public loadholos(Shop shop) {
		this.main = shop;
	}

	public static void loadHolo() {
		File configs = new File("plugins/UserShop/shops", "config.cfg");
		YamlConfiguration cfg1 = YamlConfiguration.loadConfiguration(configs);
		
		for(String s : cfg1.getStringList("Config.shops")) {
			File f = new File("plugins/UserShop/shops", s+".cfg");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(f.exists()) {
				if(!cfg.getString("Config.Display").equalsIgnoreCase("default")) {
					Location loc = (Location) cfg.get("Config.Display");
					String Owner = cfg.getString("Config.Ownername").replace("default", "Frei");
					Integer Preis = cfg.getInt("Config.Price");
					
					String[] text = {"§cDieser Shop gehört: §e" + Owner, "§cName des Shops: §e" + cfg.getString("Config.Name"), "§cPreis: §e" + Preis+"$"};
					holo h = new holo(text, loc);
					h.showAll();
				}
			}else {}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void loadGreetHolo(Player p, String Name) {
		File f = new File("plugins/UserShop/shops", Name+".cfg");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		if(f.exists()) {
			Location loc = (Location) cfg.get("Config.Display");
			String Owner = cfg.getString("Config.Ownername").replace("default", "Frei");
			Integer Preis = cfg.getInt("Config.Price");
			Owner = Owner.replace("default", "Frei");
			Owner = Bukkit.getOfflinePlayer(Owner).getName();
			String[] text = {"§cDieser Shop gehört: §e" + Owner, "§cName des Shops: §e" + Name, "§cPreis: §e" + Preis+"$"};
			
			holo h = new holo(text, loc);
			h.showAll();
			p.sendMessage(Shop.prefix + "Der Display für den Shop §e" + Name + "§7 wurde erstellt!");
		}else {p.sendMessage(Shop.prefix + "§cAchtung der Shop §e" + Name + "§c ist nicht erstellt!");}
	}
	
}
