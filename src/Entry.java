import java.util.ArrayList;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public abstract class Entry implements Handler {
	private String entryString;
	private Team team;
	private Score score;
	
	private int textFirstIndex = 0;
	private int textMaxLength = 30;
	private int textLastIndex;
	
	private final int tickDuration = (int) ((int) 1 / EasyScoreboard.tickDuration);
	private int tickCount = 0;
	
	private ArrayList<TextEffect> effects = new ArrayList<>();
	private CallbackWrapper removeCallback;
	
	public void addEffect(TextEffect newEffect) {
		effects.add(newEffect);
	}
	
	protected abstract void updateTick();
	
	private boolean isScrolling() {
		return entryString.length() > textMaxLength;
	}
	
	
	protected void renderText() {
		String displayedString = "";
		tickCount++;
		if (isScrolling()) {
			if (tickCount >= 2) {
				tickCount = 0;
				textFirstIndex = (++textFirstIndex) % (entryString.length()-1);
				
			}

			textLastIndex = (textMaxLength-1+textFirstIndex)%(entryString.length()-1);
			
			if (textLastIndex <= textFirstIndex) {
				String firstPart = entryString.substring(textFirstIndex, entryString.length()-1);
				String secondPart = entryString.substring(0, textLastIndex);
				displayedString = firstPart+secondPart;
			} else {
				displayedString = entryString.substring(textFirstIndex, textLastIndex);
			}
			
		} else {
			displayedString = entryString;
		}
		
		for (TextEffect textEffect : effects) {
			displayedString = textEffect.tickProcess(displayedString);
		}
		
		team.setPrefix("" + ChatColor.RESET + displayedString);
		
	}
	
	/* tick
	 * Precondition: runs every 1/10 second
	 * 
	 */
	public void tick(ScoreDisplay.Pass pass) {
		Objects.requireNonNull(pass);
		updateTick();
		renderText();
	}
	
	public void remove() {
		team.unregister();
		removeCallback.CallbackFunction();
	}
	
	public String getString() {
		return entryString;
	}
	
	public void setPosition(int pos) {
		score.setScore(pos);
	}
	
	protected void updateEntry(String newDisplayText) {		
		if (newDisplayText.length() < textMaxLength) {
			int spaceCount = textMaxLength - newDisplayText.length();
			for (int i = 0; i < spaceCount; ++i) {
				newDisplayText += " ";	
			}
		}
		entryString = newDisplayText;
	}
	
	Entry(Team t, Score s, int pos, CallbackWrapper rCallback) {
		team = t;
		score = s;
		setPosition(pos);
		removeCallback = rCallback;
	}
}