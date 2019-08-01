import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class ScoreEntry extends Entry implements ScoreHandler {
	private String scoreTitle;
	private double scoreValue, difference;
	
	private String getScoreEntry() {
		return "" + scoreTitle + ": " + ChatColor.RESET + ChatColor.GREEN + scoreValue;
	}
	
	ScoreEntry(String title, double d, Team team, Score score, int pos, CallbackWrapper removeCallback) {
		super(team, score, pos, removeCallback);
		scoreValue = d;
		scoreTitle = title;
		updateEntry(getScoreEntry());
	}

	@Override
	protected void updateTick() {

	}

	public double getScore() {
		return scoreValue;
	}
	
	public String getName() {
		return scoreTitle;
	}

	@Override
	public void setScore(double newValue) {
		String changeNotification = "";
		difference = newValue - scoreValue;
		
		if (difference == 0) {
			return;
		}
		
		if (difference < 0) {
			changeNotification += "" + ChatColor.DARK_RED;
		} else if (difference > 0) {
			changeNotification += "" + ChatColor.GREEN + "+";
		}
		
		changeNotification += difference;
		updateEntry(getScoreEntry()+changeNotification);
		
		Bukkit.getScheduler().runTaskLater(EasyScoreboard.getPlugin(), new Runnable() {
			public void run() {
					scoreValue = newValue;
					updateEntry(getScoreEntry());
			}
		}, 1*20);
	}

	@Override
	public void setName(String newName) {
		scoreTitle = newName;
	}

	@Override
	public double getValue() {
		return scoreValue;
	}
	
}
