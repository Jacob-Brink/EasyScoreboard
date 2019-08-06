package EasyScoreboard;
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
	public static final int maxLines = 14;
	
	private Player player;
	private Scoreboard board;
	private Objective objective;
	private ArrayList<Integer> linePositions = new ArrayList<>();
	private ArrayList<Line> lineUnits = new ArrayList<>();
	private boolean cleared = false;
	private String title = "EasyScoreboard";

	public class Line {
		private String key;
		private Team team;
		private int scoreValue;
		
		public void setLine(String text) {
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
		linePositions = new ArrayList<>();
		lineUnits = new ArrayList<>();
		for(ChatColor chatColor : ChatColor.values()) {
			if (chatColor != ChatColor.RESET) {
				
				lineUnits.add(new Line("" + chatColor, maxLines-i));
				if (++i > maxLines) {
					break;
				}
			}
		}
	}
	
	ScoreDisplay(Player p, String newTitle) {	
		player = p;
		title = newTitle;
		setupBoard();
		setupLines();
	}
	
	private void setupBoard() {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = board.registerNewObjective("f", "f", "" + title);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(board);
	}

	public void setTitle(String newTitle) {
		title = newTitle;
		board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(newTitle);
	}
	
	public void clearScoreBoard() {	
		for (Line line : lineUnits) {
			line.cleanup();
		}
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		cleared = true;
	}
	
	private boolean checkPosition(int newPosition) {
		if (newPosition > maxLines || newPosition < 0) {
			throw new IndexOutOfBoundsException("Line numbers range is 0-" + maxLines + ". " + newPosition + " was given instead.");
		}
		return true;
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
			if (cleared) {
				setupBoard();
				setupLines();
				cleared = false;
			}
			if (!linePositions.contains(position)) {
				linePositions.add(position);
			}
			lineUnits.get(position).setLine(text);
			updateLists();
		};	
	}
}
