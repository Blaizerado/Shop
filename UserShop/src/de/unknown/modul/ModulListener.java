package de.unknown.modul;

import de.unknown.main.Shop;
import modul.Utils.RegionUtil;
import modul.create.CreatShop;
import modul.holo.Holo;
import modul.holo.loadholos;
import modul.listener.PvP;
import modul.listener.onJoin;
import modul.listener.onPlace;

public class ModulListener {

	@SuppressWarnings("unused")
	private Shop main;

	public ModulListener(Shop shop) {
		this.main = shop;
		loadConfigModul();
		loadModul(shop);
		loadMySQLModul();
	}

	
	
	private void loadMySQLModul() {

	}


	private void loadConfigModul() {
		
	}

	private void loadModul(Shop shop) {
		loadholos.loadHolo();
		
		new CreatShop(shop);
		new RegionUtil(shop);
		new Holo(shop);
		new loadholos(shop);
		
		shop.getServer().getPluginManager().registerEvents(new onPlace(), shop);
		shop.getServer().getPluginManager().registerEvents(new PvP(), shop);
		shop.getServer().getPluginManager().registerEvents(new onJoin(), shop);
	}

}
