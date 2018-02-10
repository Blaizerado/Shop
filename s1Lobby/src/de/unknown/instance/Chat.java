package de.unknown.instance;

import de.unknown.Listener.ChatEvents;
import de.unknown.main.Lobby;

public class Chat {

	public Chat(Lobby lobby) {
		if (lobby.getConfig().getBoolean("chat.enabled"))
		      lobby.getServer().getPluginManager().registerEvents(new ChatEvents(lobby), lobby);
	}

}
