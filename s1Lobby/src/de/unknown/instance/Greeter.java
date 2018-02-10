package de.unknown.instance;

import de.unknown.Listener.GreeterEvents;
import de.unknown.main.Lobby;

public class Greeter {

	public Greeter(Lobby lobby) {
		if(lobby.getConfig().getBoolean("greeter.enabled")) {
			lobby.getServer().getPluginManager().registerEvents(new GreeterEvents(lobby), lobby);
		}
	}

}
