package me.Wouter0100.SuperSword;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SuperSwordPlayerListener implements Listener {
    private static SuperSword plugin;
    
    public SuperSwordPlayerListener(SuperSword instance) 
    {
    	plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
      if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
    	  Player player = event.getPlayer();
    	  if(player.hasPermission("supersword.use") && plugin.Sword(player) && plugin.configRightClickToggle){
    		  if (plugin.hasEnabled(player))
    			  plugin.removePlayer(player);
    		  else
    			  plugin.addPlayer(player);
    	  }
      }
    }
}
