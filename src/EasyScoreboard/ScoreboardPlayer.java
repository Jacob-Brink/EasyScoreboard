package EasyScoreboard;
import org.bukkit.entity.Player;

public class ScoreboardPlayer {
	private ScoreDisplay sDisplay;
	
	ScoreboardPlayer(Player p, String title) {
		sDisplay = new ScoreDisplay(p, title);
	}
	
	public ScoreDisplay getDisplay() {
		return sDisplay;
	}
}
