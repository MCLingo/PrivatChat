package de.gwarband.privatchat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;

import com.nijikokun.bukkit.Permissions.Permissions;
import com.nijiko.permissions.PermissionHandler;

import java.util.HashMap;
import java.util.logging.Logger;

public class PrivatChat extends JavaPlugin
{
	public static Logger log = Logger.getLogger("Minecraft");
	private final PrivatChatWorker worker = new PrivatChatWorker(this);
	public HashMap<String, Boolean> conservationopen = new HashMap<String, Boolean>();
	public HashMap<String, Player> conservation = new HashMap<String, Player>();
	private final PrivatChatPlayerListener playerListener = new PrivatChatPlayerListener(this);
	public static PermissionHandler permissionHandler;
	public static boolean permFound;

	public void onDisable() 
	{
	  log.info("[PrivatChat] Plugin disable");
	}

	public void onEnable()
	{
	  setupPermissions();
	  PluginManager pm = getServer().getPluginManager();
	  pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	  pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
	  pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Monitor, this);
	  log.info("[PrivatChat] Plugin enable");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
	  String commandName = command.getName().toLowerCase();
	  Player player = (Player) sender;
	  if(args.length == 1)
	  {
        if(commandName.equals("pchat"))
	    {
	    return worker.chatopen(player, args[0]);
	    }
	  }
	  else if(commandName.equals("pchat"))
	  {
	    player.sendMessage(ChatColor.RED + "/pchat <player>");
	    return true;
	  }
	  if(commandName.equals("chatexit"))
	  {
		return worker.chatclose(player);
	  }
	  return false;
	}
	
	private void setupPermissions()
	{
      Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");        
      if (permissionHandler == null)
      {
        if (permissionsPlugin != null)
        {
          permissionHandler = ((Permissions)permissionsPlugin).getHandler();
          log.info("[PrivatChat] Permission enabled");
          permFound = true;
        }
        else 
        {
          log.info("[PrivatChat] Permission system not detected");
          permFound = false;
        }
      }
    }
	
	public static boolean perm(Player player, String perm)
	{
	  if (permFound)
	  {
	    return PrivatChat.permissionHandler.has((Player)player, perm);
	  }
	  else 
	  {
	    return true;
	  }
	}
}