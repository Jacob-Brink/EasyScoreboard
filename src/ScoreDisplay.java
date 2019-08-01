import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class ScoreDisplay extends ScoreManager {
	public class Pass {
		private Pass() {};
	}

	private Player player;
	private Scoreboard board;
	private Objective objective;
	private ArrayList<Entry> entryList = new ArrayList<>();
	private ArrayList<String> chatColors = new ArrayList<>();
	private ArrayList<String> teamEntryStrings = new ArrayList<>();
	private ArrayList<String> availableEntryStrings = new ArrayList<>();
	
	public ScoreboardGroup makeScoreGroup() {
		return new ScoreboardGroup();
	}

	private void setEntryPosition(int position, Entry entry) {
		entry.setPosition(position);
	}
	
	private void resetPositions() {
		int position = entryList.size()-1;
		for (Entry thisEntry : entryList) {
			setEntryPosition(position, thisEntry);
			position--;
		}
	}
	
	/* addEntry
	 * Precondition: entryList is initialized
	 * Postcondition: positions of entry's are set (scoreboard automatically orders position from top to bottom from max to min score (position))
	 */
	private void addEntry(Entry entry) {
		if (entry instanceof ScoreEntry) {
			ScoreEntry sEntry = (ScoreEntry) entry;
			EasyScoreboard.getPlugin().getLogger().info("Score Entry: Name: " + sEntry.getName() + " Score: " +sEntry.getScore());
		}
		entryList.add(entry);
		resetPositions();
	}
	
	private Team makeTeam(String newEntry) {
		Team team = board.registerNewTeam(newEntry);
		team.addEntry(newEntry);
		return team;
	}
	
	private Score makeScore(String newEntry) {
		return objective.getScore(newEntry);
	}
	
	/* makeNewTeamString
	 * Precondition: Only called ONCE for each added entry
	 * Returns: Unique team name made from only combination of chat colors.
	 */
	private String getTeamString() {
		return availableEntryStrings.remove(0);
	}
	
	private void setupTeamStrings() {
		for (int i = 0; i < chatColors.size(); i++) {
			teamEntryStrings.add(chatColors.get(i));
			for (int j = 0; j < chatColors.size(); j++) {
				teamEntryStrings.add(chatColors.get(i) + "" + chatColors.get(j));
			}
		}
		availableEntryStrings = (ArrayList<String>) teamEntryStrings.clone();
	}
	
	public void setScoreboard() {
		player.setScoreboard(board);
	}
	
	ScoreDisplay(Player p) {	
		for (ChatColor cColor : ChatColor.values()) {
			if (cColor != ChatColor.RESET) {
				chatColors.add("" + cColor);
			}
		}
		setupTeamStrings();
		player = p;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = board.registerNewObjective("f", "f", "VertX");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		setScoreboard();
	}
	
	public void clearScoreBoard() {		
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public void tick(EasyScoreboard.MainPass pass) {
		Objects.requireNonNull(pass);
		for (Entry entry : entryList) {
			entry.tick(new Pass());
		}
	}

	
	public void removeEntry(String key, int i) {
		availableEntryStrings.add(key);
		entryList.remove(i);
		board.resetScores(key);
		resetPositions();
	}

	@Override
	public TitleHandler makeTitle(String title) {
		String newEntry = getTeamString();
		int i = entryList.size();
		TitleEntry titleEntry = new TitleEntry(title, 0, makeTeam(newEntry), makeScore(newEntry), new CallbackWrapper() {

			@Override
			public void CallbackFunction() {
				removeEntry(newEntry, i);
			}
			
		});
		addEntry(titleEntry);
		return titleEntry;
	}

	@Override
	public ScoreHandler makeScore(String name, double score) {
		String newEntry = getTeamString();
		int i = entryList.size();
		ScoreEntry scoreEntry = new ScoreEntry(name, score, makeTeam(newEntry), makeScore(newEntry), 0, new CallbackWrapper() {

			@Override
			public void CallbackFunction() {
				removeEntry(newEntry, i);
			}
			
		});
		addEntry(scoreEntry);
		return scoreEntry;
	}
}
