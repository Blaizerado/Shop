package de.unknown.main;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.unknown.modul.ModulListener;
import modul.MySQL.MySQL;
import modul.commands.Shops;
import net.md_5.bungee.api.ChatColor;

public class Shop extends JavaPlugin{

	public static String prefix = ChatColor.translateAlternateColorCodes('&', "&9&lMc-Guardians.NET &r&8➡&7 ");
	private Shop s;
	public MySQL mysql;
	@Override
	public void onEnable() {
		
		s = this;
		
		new ModulListener(this);
		loadCommandModul();
		super.onEnable();
	}

	public Plugin getInstance() {
		return s;
	}
	
	private void loadCommandModul() {
		Shops cShop = new Shops();
		getCommand("shop").setExecutor(cShop);
	}
	
}
