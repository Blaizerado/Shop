package de.unknown.instance;

import de.unknown.Listener.NavigationsEvent;
import de.unknown.main.Lobby;

public class Navigator {

	public Navigator(Lobby lobby) {
		lobby.getServer().getPluginManager().registerEvents(new NavigationsEvent(lobby), lobby);
	}

}
