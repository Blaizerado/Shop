package modul.commands;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import de.unknown.main.Shop;
import modul.Utils.RegionUtil;
import modul.create.CreatShop;
import modul.holo.Holo;
import modul.holo.loadholos;

public class Shops implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Shop main;
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player p = (Player) sender;
		if(args.length == 0) {
			p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
			p.sendMessage("/shop user add [Name]");
			p.sendMessage("/shop user remove [Name]");
			p.sendMessage("/shop schulden");
			p.sendMessage("/shop umsatzt");
			p.sendMessage("/shop sell");
			if(p.hasPermission("shop.admin")) {
				p.sendMessage("§3/shop create [Name]");
				p.sendMessage("§3/shop setDisplay [Name]");
				p.sendMessage("§3/shop setprice [Name] [Price]");
				p.sendMessage("§3/shop rdy [Name]");
				p.sendMessage("§3/shop info");
			}
		}else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("create")) {
				if(p.hasPermission("shop.admin")) {
					p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
					p.sendMessage("§3/shop create [Name]");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("setdisplay")) {
				if(p.hasPermission("shop.admin")) {
					p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
					p.sendMessage("§3/shop setDisplay [Name]");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("setprice")) {
				if(p.hasPermission("shop.admin")) {
					p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
					p.sendMessage("§3/shop setprice [Name] [Price]");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("rdy")) {
				if(p.hasPermission("shop.admin")) {
					p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
					p.sendMessage("§3/shop rdy [Name]");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("info")) {
				if(p.hasPermission("shop.admin")) {
					RegionUtil.getRegionInfo(p.getLocation(), p);
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("user")) {
				p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
				p.sendMessage("/shop user add [Name]");
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("create")) {
				if(!p.hasPermission("shop.admin")) {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
				WorldEditPlugin w = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
				Selection s = w.getSelection(p);
				CreatShop.create(p, args[1], s.getMinimumPoint(), s.getMaximumPoint(), s.getWorld());
				if(s.getMinimumPoint() == null) {p.sendMessage(Shop.prefix + "§cBitte wähle einen bereich aus!"); return true;}
			}else if(args[0].equalsIgnoreCase("setdisplay")) {
				if(p.hasPermission("shop.admin")) {
					CreatShop.setDisplay(p, args[1]);
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("setprice")) {
				if(p.hasPermission("shop.admin")) {
					p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
					p.sendMessage("§3/shop setprice [Name] [Price]");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("rdy")) {
				if(p.hasPermission("shop.admin")) {
					Holo.hideAll();
					loadholos.loadHolo();
					p.sendMessage(Shop.prefix + "Der Display für den Shop wurde gesetzt!");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("user")) {
				p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
				p.sendMessage("/shop user add [Name]");
			}
		}else{if(args.length == 3) {
			if(args[0].equalsIgnoreCase("setprice")) {
				if(p.hasPermission("shop.admin")) {
					String Name = args[1];
					int Price = Integer.valueOf(args[2]);
					if(Price >= 1 && Price <= 1000000 ) {
						CreatShop.setPrice(p, Name, Price);
					}else {p.sendMessage(Shop.prefix +"§cAchtung der Preis muss zwischen 1-1000000 liegen!"); return true;}
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("user")) {
				String User = args[2];
				if(RegionUtil.isUserInRegion(p)) {
					if(RegionUtil.isUserOwner(p)) {
						String filename = RegionUtil.getRegion(p);
						File f = new File(main.getDataFolder().getPath()+"/shops", filename+".cfg");
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
						
						List<String>Build = cfg.getStringList("Config.User");
						if(!Build.contains(User)) {
							if(!User.equalsIgnoreCase(p.getName())) {
								Build.add(User);
								try{cfg.save(f);}catch (Exception e) {}
								p.sendMessage(main.prefix + "Du hast den Spieler §e" + User + "§7 zum Mitarbeiter benannt!");
							}else {p.sendMessage(main.prefix + "§cDu kannst als Besitzer kein Mitarbeiter sein!"); return true;}
						}else {p.sendMessage(main.prefix + "§cDer Spieler §e" + User + "§c ist bereits ein Mitarbeiter!"); return true;}
					}else {p.sendMessage(main.prefix + "§cDu bist nicht der Besitzer des Shops!"); return true;}
				}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!"); return true;}
			}
		  }
		}
		return true;
	}
}
