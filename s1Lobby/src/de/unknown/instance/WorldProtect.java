package de.unknown.instance;

import de.unknown.Listener.WorldProtectorEvents;
import de.unknown.main.Lobby;

public class WorldProtect {

	public WorldProtect(Lobby lobby) {
		if (lobby.getConfig().getBoolean("worldprotector.enabled"))
			lobby.getServer().getPluginManager().registerEvents(new WorldProtectorEvents(lobby), lobby);
	}

}
