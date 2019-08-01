import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class TitleEntry extends Entry implements TitleHandler {
	TitleEntry(String titleString, int pos, Team team, Score s, CallbackWrapper removeCallback) {
		super(team, s, pos, removeCallback);
		updateEntry(titleString);
	}
	
	@Override
	protected void updateTick() {
		
	}

	public String getTitle() {
		return getString();
	}

	@Override
	public void setTitle(String newTitle) {
		updateEntry(newTitle);
	}
}
