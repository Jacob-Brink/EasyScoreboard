package EasyScoreboard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Player player = (Player)sender;
    	if (command.getName().equalsIgnoreCase("easyscoreboard")) {
    		String com = args[0];
    		if(com.equalsIgnoreCase("test")) {
    			
    			ScoreboardPlayer p = EasyScoreboard.getScorePlayer(player);
    			if (p == null) {
    				EasyScoreboard.getPlugin().getLogger().info("p is null");
    			}
    			p.getTester().runTests();
    			return true;
    		}
    	}
    	return false;
	}
}

