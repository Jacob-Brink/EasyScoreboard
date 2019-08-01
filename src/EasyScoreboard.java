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
	private static MainPass mainPass;
	
	private static void setMainPass() {
		mainPass = new MainPass();
	}
	
	public static class MainPass { private MainPass() {} };
	
	public static JavaPlugin getPlugin() {
		return plugin;
	}
	
	public static ScoreboardGroup makeScoreboardGroup() {
		return new ScoreboardGroup();
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
	
	public static FlashEffect getFlash(ChatColor first, ChatColor second, double duration) {
		return new FlashEffect("" + first, "" + second, duration);
	}
	
	private static void tickLoop() {
		Bukkit.getScheduler().runTaskLater(EasyScoreboard.getPlugin(), new Runnable() {
			public void run() {
				for (Map.Entry<Player, ScoreboardPlayer> entry : scorePlayers.entrySet()) {
					entry.getValue().getDisplay().tick(mainPass);
				}
				tickLoop();
			}
		}, (long) (tickDuration*20));
	}
	
	public static void setPlugin(JavaPlugin pluginArg) {
		plugin = pluginArg;
		setMainPass();
		tickLoop();
	}
}
