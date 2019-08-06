package EasyScoreboard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Player player = (Player)sender;
    	player.sendMessage("Command name: " + command.getName());
    	if (command.getName().equalsIgnoreCase("easyscoreboard")) {
    		String com = args[0];
    		if(com.equalsIgnoreCase("test")) {;
    			Tester.runTests(player);
    			return true;
    		}
    	}
    	return false;
	}
}

