import org.bukkit.entity.Player;

public class ScoreboardPlayer {
	private ScoreDisplay sDisplay;
	private Player player;
	
	ScoreboardPlayer(Player p) {
		player = p;
		sDisplay = new ScoreDisplay(p);
	}
	
	public ScoreDisplay getDisplay() {
		return sDisplay;
	}
}
