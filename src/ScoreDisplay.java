import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class ScoreDisplay {
	public static final int maxLines = 15;
	
	private Player player;
	private Scoreboard board;
	private Objective objective;
	private ArrayList<Integer> linePositions = new ArrayList<>();
	private ArrayList<Line> lineUnits = new ArrayList<>();

	public class Line {
		private String key;
		private Team team;
		private int scoreValue;
		
		public void setLine(String text) {
			EasyScoreboard.getPlugin().getLogger().info(text);
			team.setPrefix("" + ChatColor.RESET + text);
			displayLine();
		}
		
		public void remove() {
			board.resetScores(key);
		}
		
		public void cleanup() {
			team.unregister();
			remove();
		}
		
		private void displayLine() {
			objective.getScore(key).setScore(scoreValue);
		}
		
		Line(String keyString, int sValue) {
			key = keyString;
			team = board.registerNewTeam(key);
			team.addEntry(key);
			scoreValue = sValue;
		}
	}
	
	private void setupLines() {
		int i = 0;
		for(ChatColor chatColor : ChatColor.values()) {
			if (chatColor != ChatColor.RESET) {
				
				lineUnits.add(new Line("" + chatColor, maxLines-i));
				if (++i > maxLines) {
					break;
				}
			}
		}
	}
	
	ScoreDisplay(Player p, String title) {	
		player = p;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = board.registerNewObjective("f", "f", "" + title);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		p.setScoreboard(board);
		setupLines();
		p.setScoreboard(board);
	}
	
	public void clearScoreBoard() {		
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	private boolean checkPosition(int newPosition) {
		return newPosition <= maxLines;
	}
	
	private void updateLists() {
		Collections.sort(linePositions);
		if (!linePositions.isEmpty()) {
			int lastLineIndex = linePositions.get(linePositions.size()-1);
			for (int line = 0; line < lastLineIndex; line++) {
				if (!linePositions.contains(line)) {
					lineUnits.get(line).setLine(" ");
				}
			} 
			for (int line = lastLineIndex+1; line < maxLines; line++) {
				lineUnits.get(line).remove();
			}
		}
	}	
	
	public void removeEntry(int position) {
		if (checkPosition(position)) {
			if (linePositions.contains(position)) {
				linePositions.remove(linePositions.indexOf(position));
				lineUnits.get(position).remove();
			}
			updateLists();
		};		
	}
	
	public void cleanup() {
		for (int i = 0; i < lineUnits.size(); i++) {
			lineUnits.get(i).cleanup();
		}
	}
	
	public void setLine(String text, int position) {
		if (checkPosition(position)) {
			if (!linePositions.contains(position)) {
				linePositions.add(position);
			}
			lineUnits.get(position).setLine(text);
			updateLists();
		};	
	}
}
