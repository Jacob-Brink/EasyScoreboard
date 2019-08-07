package EasyScoreboard;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


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
		ScoreboardPlayer scorePlayer = new ScoreboardPlayer(player, " ");
		scorePlayers.put(player, scorePlayer);
		return scorePlayer;
	}
	
	public static ScoreDisplay getDisplay(Player player) {
		return getScorePlayer(player).getDisplay();
	}
	
	public static void setPlugin(JavaPlugin pluginArg, int mWidth) {
		plugin = pluginArg;
		maxWidth = mWidth;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		maxWidth = 32;
		getCommand("easyscoreboard").setExecutor(new CommandListener());
	}
	
	@Override
	public void onDisable() {
		EasyScoreboard.cleanup();
	}
	
	public static void cleanup() {
		for (Map.Entry<Player, ScoreboardPlayer> entry : scorePlayers.entrySet()) {
			entry.getValue().getDisplay().clearScoreBoard();
		}
	}
}
