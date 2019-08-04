import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EasyScoreboard extends JavaPlugin {
	private static JavaPlugin plugin;
	private static HashMap<Player, ScoreboardPlayer> scorePlayers = new HashMap<>();
	public final static double tickDuration = .05;
	public final static int maxTextLength = 14; 
	public static int maxWidth;
	
	public static class MainPass { private MainPass() {} };
	
	public static JavaPlugin getPlugin() {
		return plugin;
	}
	
	public static ScoreboardPlayer getScorePlayer(Player player) {
		if (scorePlayers.containsKey(player)) {
			return scorePlayers.get(player);
		}
		return null;
	}
	
	public static ScoreDisplay getDisplay(Player p) {
		if (scorePlayers.containsKey(p)) {
			return scorePlayers.get(p).getDisplay();
		}
		ScoreboardPlayer scorePlayer = new ScoreboardPlayer(p);
		scorePlayers.put(p, scorePlayer);
		return scorePlayer.getDisplay();
	}
	
	public static void setPlugin(JavaPlugin pluginArg, int mWidth) {
		plugin = pluginArg;
		maxWidth = mWidth;
	}
	
	public static void cleanup() {
		for (Map.Entry<Player, ScoreboardPlayer> entry : scorePlayers.entrySet()) {
			entry.getValue().getDisplay().cleanup();
		}
	}
}
