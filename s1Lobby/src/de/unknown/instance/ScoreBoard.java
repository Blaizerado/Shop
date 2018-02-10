package de.unknown.instance;

import de.unknown.Listener.ScoreboardEvents;
import de.unknown.main.Lobby;

public class ScoreBoard {

	public ScoreBoard(Lobby lobby) {
		 if ((lobby.getConfig().getBoolean("scoreboard.enabled")) && (lobby.getConfig().isSet("ranks.default")))
		      lobby.getServer().getPluginManager().registerEvents(new ScoreboardEvents(lobby), lobby);
	}

}
