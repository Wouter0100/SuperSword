package me.Wouter0100.SuperSword;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SuperSwordEntityListener implements Listener {

    private static SuperSword plugin;
    
    public SuperSwordEntityListener(SuperSword instance) 
    {
   	 plugin = instance;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) 
	{
		if (event instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent edbye = (EntityDamageByEntityEvent) event;
			if (edbye.getDamager() instanceof Player)
			{
				Player p = (Player)edbye.getDamager();
				
					if(plugin.Check(p)){
						LivingEntity entity = (LivingEntity) event.getEntity();
						entity.setHealth(1);
						entity.damage(1);
					}
			}
		}	
    }
}
