package me.Wouter0100.SuperSword;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.logging.Logger;


public class SuperSword extends JavaPlugin {
	

	public String configSwords;
	public Boolean configRightClickToggle = true;
	private final Logger log = Logger.getLogger("Minecraft");
	private final Listener entityListener = new SuperSwordEntityListener(this);
	private final Listener playerListener = new SuperSwordPlayerListener(this);
	private final HashSet<Integer> supersword = new HashSet<Integer>();
	
	public void onEnable() {
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvents(entityListener, this);
		pm.registerEvents(playerListener, this);
			
	
		analizeConfig();
		
		}
	
		public void onDisable() {
			log("v"+getDescription().getVersion()+" has been disabled.");
		}

		public void analizeConfig()
		{
			log("Analizing config file..");
			getConfig().addDefault("SuperSword.Swords", "268,272,267,283,276");
			getConfig().addDefault("SuperSword.RightClickToggle", true);
			
	        getConfig().options().copyDefaults(true);
	        saveConfig();
	        
		    configSwords = getConfig().getString("SuperSword.Swords");
		    configRightClickToggle = getConfig().getBoolean("SuperSword.RightClickToggle");
		    log("Analizing finished.");
		}
		
		public boolean checkpermissions(Player player, String string)
		{
        		if(player.hasPermission(string)){
        			return true;
        		}else{
        			player.sendMessage(ChatColor.RED + "You don't have the permissions for this command.");
        			return false;
        		}    		
		}
		
	
		public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
			if (sender instanceof ConsoleCommandSender) {
				sender.sendMessage("You need to be a player!");
			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				if (command.getName().equalsIgnoreCase("sw") || command.getName().equalsIgnoreCase("supersword")) {
					if(checkpermissions(player, "supersword.use")){
						if (hasEnabled(player))
							removePlayer(player);
						else
							addPlayer(player);
						return true;
					}
				}
			}
			return false;
		}
	
		public void log(String s){
			log.info("[SuperSword]: "+s);
		}
	
		public boolean Check(Player p){
			if(hasEnabled(p)){
				String[] split = configSwords.split(",");
				for(String str : split){
					try {
						if(p.getItemInHand().getTypeId() == Integer.parseInt(str)){
							return true;
						}
					} catch(NumberFormatException nfe){
	                	log("Config file error, One of the sword id's is not number!");
					}
				}
			}
			return false;
		}
		
		public boolean Sword(Player p){
			String[] split = configSwords.split(",");
			for(String str : split){
				try {
					if(p.getItemInHand().getTypeId() == Integer.parseInt(str)){
						return true;
					}
				} catch(NumberFormatException nfe){
                	log("Config file error, One of the sword id's is not number!");
				}
			}
			return false;
		}
	
		public void addPlayer(Player player) {
			supersword.add(player.getName().hashCode());
			player.sendMessage(ChatColor.GREEN + "SuperSword enabled.");
			log(player.getName() + " Has enabled SuperSword.");
		}

		public void removePlayer(Player player) {
			supersword.remove(player.getName().hashCode());
			player.sendMessage(ChatColor.GREEN + "SuperSword disabled.");
			log(player.getName() + " Has disabled SuperSword.");
		}

		public boolean hasEnabled(Player player) {
			return supersword.contains(player.getName().hashCode());
		}
}
