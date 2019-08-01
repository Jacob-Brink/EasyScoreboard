import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class TimerEntry extends Entry {
	private String title;
	private double totalTime;
	private double timePassed = 0;
	private String finishText;
	
	private String getTimeString() {
		return title + " " + getTimeLeft() + "s";
	}
	
	private double getTimeLeft() {
		return totalTime - timePassed;
	}
	
	TimerEntry(Team team, Score score, int pos, String titleString, double duration, String finishString, CallbackWrapper removeCallback) {
		super(team, score, pos, removeCallback);
		title = titleString;
		totalTime = duration;
		finishText = finishString;
		updateEntry("");
	}

	@Override
	protected void updateTick() {
		timePassed += EasyScoreboard.tickDuration;
		if (getTimeLeft() < 0)
			updateEntry(finishText);
		updateEntry(getTimeString());
	}

}
