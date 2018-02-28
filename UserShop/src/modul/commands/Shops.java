package modul.commands;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;

import de.unknown.main.Shop;
import modul.MySQL.Function;
import modul.Utils.RegionUtil;
import modul.create.creatShop;
import modul.holo.holo;
import modul.holo.loadholos;

public class Shops implements CommandExecutor {
	
	private Shop main;
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player p = (Player) sender;
		if(args.length == 0) {
			p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
			p.sendMessage("§3/shop user add [Name]");
			p.sendMessage("§3/shop user remove [Name]");
			p.sendMessage("§3/shop schulden");
			p.sendMessage("§3/shop umsatzt");
			p.sendMessage("§3/shop sell");
			p.sendMessage("§3/shop buy");
			if(p.hasPermission("shop.admin")) {
				p.sendMessage("§3/shop create [Name]");
				p.sendMessage("§3/shop setDisplay [Name]");
				p.sendMessage("§3/shop setprice [Name] [Price]");
				p.sendMessage("§3/shop rdy [Name]");
				p.sendMessage("§3/shop info");
				p.sendMessage("§3/shop setowner [Player]");
				p.sendMessage("§3/shop reset");
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
				p.sendMessage("§3/shop user add [Name]");
				p.sendMessage("§3/shop user remove [Name]");
			}else if(args[0].equalsIgnoreCase("buy")) {
				if(RegionUtil.isUserInRegion(p)) {
					String regionfile = RegionUtil.getRegion(p);
					File f = new File("plugins/UserShop/shops", regionfile+".cfg");
					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
					String Owner = cfg.getString("Config.Owner");
					if(Owner.equalsIgnoreCase("default")) {
						if(Function.getMoney(p.getUniqueId().toString()) >= cfg.getInt("Config.Price")) {
							cfg.set("Config.Owner", p.getUniqueId().toString());
							cfg.set("Config.Ownername", p.getName());
							try {cfg.save(f);}catch(Exception e) {}
							holo.hideAll();
							loadholos.loadHolo();
							Function.removeMoney(p.getUniqueId().toString(), cfg.getInt("Config.Price"));
							p.sendMessage(main.prefix + "Du hast den Shop §e" + cfg.getString("Config.Name") + "§7 für §e" + cfg.getInt("Config.Price") + "§7$ gekauft");
						}else {p.sendMessage(main.prefix + "§cAchtung du hast nicht genügend Money!");}
					}else {p.sendMessage(main.prefix + "§cDer Shop ist schon an §e" + Owner + "§c vergeben!");}
				}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!");}
			}else if(args[0].equalsIgnoreCase("sell")) {
				if(RegionUtil.isUserInRegion(p)) {
					if(RegionUtil.isUserOwner(p)) {
						String filename = RegionUtil.getRegion(p);
						File f = new File("plugins/UserShop/shops", filename+".cfg");
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
						BukkitWorld bwf = new BukkitWorld(p.getWorld());
						int x1 = cfg.getInt("Config.Location.x1");
						int y1 = cfg.getInt("Config.Location.y1");
						int z1 = cfg.getInt("Config.Location.z1");
						
						
						int x2 = cfg.getInt("Config.Location.x2");
						int y2 = cfg.getInt("Config.Location.y2");
						int z2 = cfg.getInt("Config.Location.z2");
						
						Vector p1 = new Vector(x1,y1,z1);
						Vector p2 = new Vector(x2,y2,z2);
						CuboidRegion cube = new CuboidRegion(p1, p2);
						cube.setWorld(bwf);
						EditSession session = new EditSession(bwf, cube.getArea());
						
						cfg.set("Config.Owner", "default");
						cfg.set("Config.Ownername", "default");
						try{session.setBlocks(cube, new BaseBlock(0)); cfg.save(f);}catch(Exception e) {}
						p.sendMessage(main.prefix + "Du hast den Shop §c" + cfg.getString("Config.Name") + "§7 verkauft, der Kaufpreis wurde dir wieder gut geschrieben!");
						Function.addMoney(p.getUniqueId().toString(), cfg.getInt("Config.Price"));
						holo.hideAll();
						loadholos.loadHolo();
					}else {p.sendMessage(main.prefix + "§cAchtung du bist nicht der Besitzer des Shops!"); return true;}
				}else {p.sendMessage(main.prefix + "§cDu bist in keinem Shop!"); return true;}
			}else if(args[0].equalsIgnoreCase("schulden")) {
				if(RegionUtil.isUserInRegion(p)) {
					if(RegionUtil.isUserOwner(p)) {
						String regionfile = RegionUtil.getRegion(p);
						File f = new File("plugins/UserShop/shops", regionfile+".cfg");
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
						p.sendMessage(main.prefix + "Dein Shop hat bis jetzt §c" + cfg.getInt("Config.Schulden") + "§7$ Schulden");
					}else {p.sendMessage(main.prefix + "§cDu bist nicht der besitzer des Shop´s");}
				}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!");}
			}else if(args[0].equalsIgnoreCase("umsatzt")) {
				if(RegionUtil.isUserInRegion(p)) {
					if(RegionUtil.isUserOwner(p)) {
						String regionfile = RegionUtil.getRegion(p);
						File f = new File("plugins/UserShop/shops", regionfile+".cfg");
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
						
						p.sendMessage(main.prefix + "Dein Shop hat bis jetzt §c" + cfg.getInt("Config.Umsatzt") + "§7$ Umsatzt!");
					}else {p.sendMessage(main.prefix + "§cDu bist nicht der besitzer des Shop´s");}
				}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!");}
			}else if(args[0].equalsIgnoreCase("reset")) {
				if(p.hasPermission("shop.admin")) {
					if(RegionUtil.isUserInRegion(p)) {
						String filename = RegionUtil.getRegion(p);
						File f = new File("plugins/UserShop/shops", filename+".cfg");
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
						BukkitWorld bwf = new BukkitWorld(p.getWorld());
						int x1 = cfg.getInt("Config.Location.x1");
						int y1 = cfg.getInt("Config.Location.y1");
						int z1 = cfg.getInt("Config.Location.z1");
						
						
						int x2 = cfg.getInt("Config.Location.x2");
						int y2 = cfg.getInt("Config.Location.y2");
						int z2 = cfg.getInt("Config.Location.z2");
						
						Vector p1 = new Vector(x1,y1,z1);
						Vector p2 = new Vector(x2,y2,z2);
						CuboidRegion cube = new CuboidRegion(p1, p2);
						cube.setWorld(bwf);
						EditSession session = new EditSession(bwf, cube.getArea());
						
						cfg.set("Config.Owner", "default");
						cfg.set("Config.Ownername", "default");
						try{session.setBlocks(cube, new BaseBlock(0)); cfg.save(f);}catch(Exception e) {}
						p.sendMessage(main.prefix + "§cAchtung der Shop wurde resetet!");
						holo.hideAll();
						loadholos.loadHolo();
					}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!"); return true;}
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("user")) {
				p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
				p.sendMessage("§3/shop user add [Name]");
				p.sendMessage("§3/shop user remove [Name]");
			}
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("create")) {
				if(!p.hasPermission("shop.admin")) {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
				WorldEditPlugin w = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
				
				Selection s = w.getSelection(p);
				creatShop.create(p, args[1], s.getMinimumPoint(), s.getMaximumPoint(),s.getWorld());
				if(s.getMinimumPoint() == null) {p.sendMessage(Shop.prefix + "§cBitte wähle einen bereich aus!"); return true;}
			}else if(args[0].equalsIgnoreCase("setdisplay")) {
				if(p.hasPermission("shop.admin")) {
					creatShop.setDisplay(p, args[1]);
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("setprice")) {
				if(p.hasPermission("shop.admin")) {
					p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
					p.sendMessage("§3/shop setprice [Name] [Price]");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("rdy")) {
				if(p.hasPermission("shop.admin")) {
					holo.hideAll();
					loadholos.loadHolo();
					p.sendMessage(Shop.prefix + "Der Display für den Shop wurde gesetzt!");
				}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
			}else if(args[0].equalsIgnoreCase("user")) {
				p.sendMessage("§3____________§8[§cUser-Shop§8]§3____________");
				p.sendMessage("§3/shop user add [Name]");
				p.sendMessage("§3/shop user remove [Name]");
			}else if(args[0].equalsIgnoreCase("setowner")) {
				if(RegionUtil.isUserInRegion(p)) {
					String regionfile = RegionUtil.getRegion(p);
					File f = new File("plugins/UserShop/shops", regionfile+".cfg");
					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
					
					cfg.set("Config.Owner", p.getUniqueId().toString());
					cfg.set("Config.Ownername", p.getName());
					try {cfg.save(f);}catch(Exception e) {}
					holo.hideAll();
					loadholos.loadHolo();
					p.sendMessage(main.prefix + "Der Shop §c" + cfg.getString("Config.Name") + "§7 wurd an §c" + args[1] + "§7 gesetzt!");
				}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop");}
			}
		}else{if(args.length == 3) {
			if(args[0].equalsIgnoreCase("setprice")) {
				if(p.hasPermission("shop.admin")) {
					if(RegionUtil.isUserInRegion(p)) {
						String Name = args[1];
						int Price = Integer.valueOf(args[2]);
						if(Price >= 1 && Price <= 1000000 ) {
							creatShop.setPrice(p, Name, Price);
						}else {p.sendMessage(Shop.prefix +"§cAchtung der Preis muss zwischen 1-1000000 liegen!"); return true;}
					}else {p.sendMessage(Shop.prefix + "§cDer Zugriff auf diesen Command wurde verweigert!"); return true;}
					}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!"); return true;}
			}else if(args[0].equalsIgnoreCase("user")) {
				if(args[1].equalsIgnoreCase("add")) {
					String User = args[2];
					if(RegionUtil.isUserInRegion(p)) {
						if(RegionUtil.isUserOwner(p)) {
							String filename = RegionUtil.getRegion(p);
							File f = new File("plugins/UserShop/shops", filename+".cfg");
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
							
							List<String>Build = cfg.getStringList("Config.User");
							if(!Build.contains(Bukkit.getOfflinePlayer(User).getUniqueId().toString())) {
								if(!User.equalsIgnoreCase(p.getUniqueId().toString())) {
									Build.add(Bukkit.getOfflinePlayer(User).getUniqueId().toString());
									cfg.set("Config.User", Build);
									try{cfg.save(f);}catch (Exception e) {}
									p.sendMessage(main.prefix + "Du hast den Spieler §e" + args[2] + "§7 zum Mitarbeiter benannt!");
								}else {p.sendMessage(main.prefix + "§cDu kannst als Besitzer kein Mitarbeiter sein!"); return true;}
							}else {p.sendMessage(main.prefix + "§cDer Spieler §e" + args[2] + "§c ist bereits ein Mitarbeiter!"); return true;}
						}else {p.sendMessage(main.prefix + "§cDu bist nicht der Besitzer des Shops!"); return true;}
					}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!"); return true;}
				}else if(args[1].equalsIgnoreCase("remove")) {
					String User = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
					if(RegionUtil.isUserInRegion(p)) {
						if(RegionUtil.isUserOwner(p)) {
							String filename = RegionUtil.getRegion(p);
							File f = new File("plugins/UserShop/shops", filename+".cfg");
							YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
							
							List<String>Build = cfg.getStringList("Config.User");
							if(Build.contains(User)) {
								if(!User.equalsIgnoreCase(p.getUniqueId().toString())) {
									Build.remove(Bukkit.getOfflinePlayer(User).getUniqueId().toString());
									cfg.set("Config.User", Build);
									try{cfg.save(f);}catch (Exception e) {}
									p.sendMessage(main.prefix + "Du hast den Spieler §e" + args[2] + "§7 als Mitarbeiter gekündigt!!");
								}else {p.sendMessage(main.prefix + "§cDu kannst dich nicht selbst entfernen!"); return true;}
							}else {p.sendMessage(main.prefix + "§cDer Spieler §e" + args[2] + "§c ist bereits gekündigt!"); return true;}
						}else {p.sendMessage(main.prefix + "§cDu bist nicht der Besitzer des Shops!"); return true;}
					}else {p.sendMessage(main.prefix + "§cDu bist in keinen Shop!"); return true;}
				}
			}
		  }
		}
		return true;
	}
}
