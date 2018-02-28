package de.unknown.modul;

import de.unknown.main.Shop;
import modul.MySQL.Function;
import modul.MySQL.MySQL;
import modul.Utils.RegionUtil;
import modul.create.creatShop;
import modul.holo.holo;
import modul.holo.loadholos;
import modul.listener.PvP;
import modul.listener.onJoin;
import modul.listener.onPlace;

public class ModulListener {

	private Shop main;

	public ModulListener(Shop shop) {
		this.main = shop;
		loadConfigModul();
		loadModul(shop);
		loadMySQLModul();
	}

	
	
	private void loadMySQLModul() {
		String Host = main.getConfig().getString("Config.MySQL.Host");
		String DataBase = main.getConfig().getString("Config.MySQL.DataBase");
		String User = main.getConfig().getString("Config.MySQL.User");
		String Password = main.getConfig().getString("Config.MySQL.Password");
		
		if(!User.equalsIgnoreCase("Shop_User")) {
			main.mysql = new MySQL(Host, DataBase, User, Password);
			main.mysql.update("CREATE TABLE IF NOT EXISTS shop(UUID varchar(64),Money int);");
		}else {System.out.println("Achtung es kann keine Datenbank gefunden werden!");}
	}


	private void loadConfigModul() {
		initConfig("Config.MySQL.Host", "127.0.0.1");
		initConfig("Config.MySQL.DataBase", "Shop_DB");
		initConfig("Config.MySQL.User", "Shop_User");
		initConfig("Config.MySQL.Password", "Shop_Password");
	}

	private void loadModul(Shop shop) {
		loadholos.loadHolo();
		
		new creatShop(shop);
		new RegionUtil(shop);
		new holo(shop);
		new loadholos(shop);
		new Function(shop);
		
		shop.getServer().getPluginManager().registerEvents(new onPlace(), shop);
		shop.getServer().getPluginManager().registerEvents(new PvP(), shop);
		shop.getServer().getPluginManager().registerEvents(new onJoin(), shop);
	}

	private void initConfig(String key, Object value) {
		if(!main.getConfig().isSet(key)) {
			main.getConfig().set(key, value);
			main.saveConfig();
		}
	}
	
}
