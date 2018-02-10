package de.unknown.main;

import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import de.unknown.Listener.DurchchillenEvents;
import de.unknown.commands.ChatClearCommand;
import de.unknown.commands.TeleportsCommand;
import de.unknown.instance.AntiTab;
import de.unknown.instance.Chat;
import de.unknown.instance.Greeter;
import de.unknown.instance.Navigator;
import de.unknown.instance.ScoreBoard;
import de.unknown.instance.Tablist;
import de.unknown.instance.WorldProtect;

public class Lobby extends JavaPlugin{

	@Override
	public void onEnable() {
		setConfigDefaults();
		loadInstance();
		getServer().getPluginManager().registerEvents(new DurchchillenEvents(this), this);
		getCommand("dcteleports").setExecutor(new TeleportsCommand(this));
		getCommand("cc").setExecutor(new ChatClearCommand(this));
	}
	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
	private void loadInstance() {
		new Navigator(this);
		new Greeter(this);
		new WorldProtect(this);
		new ScoreBoard(this);
		new Tablist(this);
		new Chat(this);
		new AntiTab(this);
	}
	
	private void setConfigDefaults() {
		initConfig("durchchillen.prefix", "&9&lDurchChillen.NET &r&8➜&7");
	    initConfig("durchchillen.permissions.admin", "durchchillen.admin");
	    initConfig("durchchillen.permissions.bypass", "durchchillen.bypass");
	    initConfig("durchchillen.permissions.team", "durchchillen.team");
	    initConfig("durchchillen.permissions.redstone", "durchchillen.redstone");
	    initConfig("durchchillen.permissions.chatclear", "durchchillen.chatclear");
	    initConfig("durchchillen.spawn-on-join", Boolean.valueOf(true));
	    initConfig("durchchillen.spawn", "");
	    initConfig("durchchillen.gamemode-on-join", "adventure");
	    initConfig("durchchillen.weather", Boolean.valueOf(false));
	    initConfig("durchchillen.blacklist", new String[0]);
	    initConfig("navigator.enabled", Boolean.valueOf(true));
	    initConfig("navigator.teleport-cooldown", Integer.valueOf(3));
	    initConfig("navigator.hotbar.default.item", "stained_glass_pane;15");
	    initConfig("navigator.hotbar.default.title", "Willkommen auf DurchChillen.NET");
	    initConfig("navigator.hotbar.default.teleport", "");
	    initConfig("navigator.messages.teleport-set", "<prefix> Teleport <name> wurde gesetzt.");
	    initConfig("navigator.messages.teleport-not-exist", "<prefix> Teleport <name> gibt es nicht.");
	    initConfig("navigator.messages.teleport-removed", "<prefix> Teleport <name> wurde entfernt.");
	    initConfig("navigator.messages.teleport-tested", "<prefix> Du wurdest zum Teleport <name> teleportiert.");
	    initConfig("navigator.messages.teleports", "Teleports: <teleports>");
	    initConfig("navigator.messages.teleported", "<prefix> Teleportiere...");
	    initConfig("navigator.messages.please-wait", "<prefix> Bitte warte noch <seconds> Sekunde(n)...");
	    initConfig("greeter.enabled", Boolean.valueOf(true));
	    initConfig("greeter.screenmessages.line1", "&7Willkommen auf");
	    initConfig("greeter.screenmessages.line2", "&9&lDurchChillen.NET");
	    initConfig("greeter.chatmessages", Arrays.asList(new String[] { "&8&m-----------------------------------------------------", "<prefix> Willkommen &l<player>", "&8&m-----------------------------------------------------" }));

	    initConfig("worldprotector.enabled", Boolean.valueOf(true));
	    initConfig("scoreboard.enabled", Boolean.valueOf(true));
	    initConfig("scoreboard.title", "&9&lDurchChillen.NET");
	    initConfig("scoreboard.lines", Arrays.asList(new String[] { "line1", "line2", "line3" }));

	    initConfig("tablist.enabled", Boolean.valueOf(true));
	    initConfig("chat.enabled", Boolean.valueOf(true));
	    initConfig("chat.separator", "&8➜&7");
	    initConfig("chat.blocked-words", new String[0]);
	    initConfig("chat.allowed-commands", new String[0]);
	    initConfig("chat.messages.once-only", "<prefix> Bitte sende jede Nachricht nur einmal.");
	    initConfig("chat.messages.bad-word", "<prefix> Bitte achte auf deine Wortwahl.");
	    initConfig("chat.messages.chatclear", "<prefix> Der Chat wurde von &9<player> &7gecleart.");
	    initConfig("antitab.enabled", Boolean.valueOf(true));
	    initConfig("ranks.default.scoreboard", "User");
	    initConfig("ranks.default.tablist", "");
	    initConfig("ranks.default.chat", "");

	}
	
	private void initConfig(String key, Object value) {
		if(!getConfig().isSet(key)) {
			getConfig().set(key, value);
			saveConfig();
		}
	}
	
}
