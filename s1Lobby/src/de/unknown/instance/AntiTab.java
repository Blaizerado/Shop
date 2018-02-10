package de.unknown.instance;

import java.util.List;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.regex.Pattern;

import de.unknown.main.Lobby;

public class AntiTab {

	public AntiTab(Lobby plugin) {
		if (!plugin.getConfig().getBoolean("antitab.enabled")) {
		      return;
		    }

		    final List<String> allowedCommands = plugin.getConfig().getStringList("chat.allowed-commands");
		    final String permission = plugin.getConfig().getString("durchchillen.permissions.team");

		    ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		    protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
		      public void onPacketReceiving(PacketEvent event) {
		        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE)
		          try {
		            if (event.getPlayer().hasPermission(permission)) {
		              return;
		            }

		            PacketContainer packet = event.getPacket();
		            String msg = ((String)packet.getSpecificModifier(String.class).read(0)).toLowerCase().replaceAll("^\\s*", "");

		            if (!Pattern.matches("^/.*$", msg)) {
		              return;
		            }

		            event.setCancelled(true);

		            if (!Pattern.matches("^/\\w+ .*$", msg)) {
		              return;
		            }

		            String[] splittedMsg = msg.split(" ");

		            if (allowedCommands.contains(splittedMsg[0]))
		              event.setCancelled(false);
		          }
		          catch (Exception localException)
		          {
		          }
		      }
		    });
	}

}
