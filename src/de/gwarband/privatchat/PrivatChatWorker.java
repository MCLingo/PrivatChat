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
	  if(!PrivatChat.perm(player, "privatchat")){
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
	player.sendMessage(ChatColor.GOLD+"Privat chat close");
	plugin.conservationopen.put(player.getName().toLowerCase(), false);
	return true;
  }
  
  public boolean chatmsg(Player player, String args[])
  {
	  String msg = "";
	  for(int i = 0; args.length > i; i++){
		  msg += args[i]+" ";
		  System.out.println(args[i]);
	  }
	  plugin.getServer().broadcastMessage(getPrefix(player)+"["+getGroup(player)+"] "+ChatColor.WHITE+player.getName()+": "+msg);
	  return true;
  }
  
  public String getGroup(Player player) {
	  if (PrivatChat.permissionHandler != null) {
	  String group = PrivatChat.permissionHandler.getGroup(player.getWorld().getName(), player.getName());
	  return group;
	  }
	  return null;
  }
  
  public String getPrefix(Player player) {
	  	if (PrivatChat.permissionHandler != null) {
		  // Check for user prefix first
		  String userPrefix = (PrivatChat.permissionHandler).getUserPermissionString(player.getWorld().getName(), player.getName(), "prefix");
	  	if (userPrefix != null && !userPrefix.isEmpty()) {
		  return userPrefix;
	  	}
	  	// Check if the group has a prefix.
	  	String group = PrivatChat.permissionHandler.getGroup(player.getWorld().getName(), player.getName());
	  	if (group == null) return null;
	  	String groupPrefix = PrivatChat.permissionHandler.getGroupPrefix(player.getWorld().getName(), group);
	  	return groupPrefix;
	  }
	  return null;
  }
}