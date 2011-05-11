package de.gwarband.privatchat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PrivatChatWorker
{
  public PrivatChat plugin;
  
  public PrivatChatWorker (PrivatChat tplugin)
  {
	plugin = tplugin;
  }
  
  public boolean chatopen(Player player, String playerName)
  {
    if(!PrivatChat.perm(player, "privatchat"))
    {
      player.sendMessage(ChatColor.RED + "You don't have Permissions to use this Command!");
      return true;
    }
    Player player2 = plugin.getServer().getPlayer(playerName);
    plugin.conservation.put(player.getName().toLowerCase(), player2);
    plugin.conservationopen.put(player.getName().toLowerCase(), true);
    player.sendMessage(ChatColor.GOLD+"You have a new privat chat");
    player.sendMessage(ChatColor.GOLD+"To close the privat chat please write again /chatexit");
    return true;
  }
  
  public boolean chatclose(Player player)
  {
	plugin.conservation.remove(player.getName().toLowerCase());
	plugin.conservationopen.put(player.getName().toLowerCase(), false);
	player.sendMessage(ChatColor.GOLD+"Privat chat close");
	return true;
  }
}