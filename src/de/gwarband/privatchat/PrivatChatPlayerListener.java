package de.gwarband.privatchat;

import org.bukkit.ChatColor;
import org.bukkit.event.player.*;
import org.bukkit.entity.Player;

public class PrivatChatPlayerListener extends PlayerListener
{
	private final PrivatChat plugin;
	
	public PrivatChatPlayerListener(PrivatChat instance)
	{
        plugin = instance;
    }
	
	public void onPlayerJoin(PlayerJoinEvent event)
	{
	  Player player = event.getPlayer();
	  plugin.conservationopen.put(player.getName().toLowerCase(), false);
	}
	
	public void onPlayerQuit(PlayerQuitEvent event)
	{
	  Player player = event.getPlayer();
      plugin.conservationopen.remove(player.getName().toLowerCase());
	}
	
	public void onPlayerChat(PlayerChatEvent event)
	{
	  Player player = event.getPlayer();
	  if(plugin.conservationopen.get(player.getName().toLowerCase()))
	  {
		String message = event.getMessage();
	    event.setCancelled(true);
	    Player player2 = plugin.conservation.get(player.getName().toLowerCase());
	    player.sendMessage(ChatColor.BLUE+"[PrivatChat] "+player.getName()+" => "+player2.getName()+": "+ChatColor.WHITE+message);
	    player2.sendMessage(ChatColor.BLUE+"[PrivatChat] "+player.getName()+": "+ChatColor.WHITE+message);
	  }
	}
}