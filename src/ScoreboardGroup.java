import java.util.ArrayList;

public class ScoreboardGroup extends ScoreManager {
	private ArrayList<ScoreboardPlayer> scoreboardPlayers = new ArrayList<>();
	private ArrayList<GroupHandler> groups = new ArrayList<>();
	
	ScoreboardGroup() {
		
	}

	@Override
	public TitleHandler makeTitle(String title) {
		TitleGroup newGroup = new TitleGroup(title);
		for (ScoreboardPlayer sPlayer : scoreboardPlayers) {
			newGroup.addPlayer(sPlayer);
		}
		groups.add(newGroup);
		return newGroup;
	}

	@Override
	public ScoreHandler makeScore(String name, double score) {
		// TODO Auto-generated method stub
		ScoreGroup newGroup = new ScoreGroup(name, score);
		for (ScoreboardPlayer sPlayer : scoreboardPlayers) {
			newGroup.addPlayer(sPlayer);
		}
		groups.add(newGroup);
		return newGroup;
	}
	
	public void addPlayer(ScoreboardPlayer sPlayer) {
		scoreboardPlayers.add(sPlayer);
		for (GroupHandler group : groups) {
			if (group instanceof ScoreGroup) {
				ScoreGroup scoreGroup = (ScoreGroup) group;
				scoreGroup.addPlayer(sPlayer);
			} else if (group instanceof TitleGroup) {
				TitleGroup titleGroup = (TitleGroup) group;
				titleGroup.addPlayer(sPlayer);
			}
		}
	}
	
	
}
