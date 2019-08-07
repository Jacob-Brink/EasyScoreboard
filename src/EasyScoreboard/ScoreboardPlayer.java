package EasyScoreboard;
import org.bukkit.entity.Player;

public class ScoreboardPlayer {
	private ScoreDisplay sDisplay;
	private Tester test;
	
	ScoreboardPlayer(Player p, String title) {
		sDisplay = new ScoreDisplay(p, title);
		test = new Tester(p, sDisplay);
	}
	
	public Tester getTester() {
		return test;
	}
	
	public ScoreDisplay getDisplay() {
		return sDisplay;
	}
}
