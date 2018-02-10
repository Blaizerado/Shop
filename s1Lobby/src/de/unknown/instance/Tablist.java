package de.unknown.instance;

import de.unknown.Listener.TablistEvents;
import de.unknown.main.Lobby;

public class Tablist {

	public Tablist(Lobby lobby) {
		if ((lobby.getConfig().getBoolean("tablist.enabled")) && (lobby.getConfig().isSet("ranks.default")))
		      lobby.getServer().getPluginManager().registerEvents(new TablistEvents(lobby), lobby);
	}

}
