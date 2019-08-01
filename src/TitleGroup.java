
public class TitleGroup extends GroupHandler implements TitleHandler{
	private String title;
	
	TitleGroup(String t) {
		title = t;
	}
	
	@Override
	public void setTitle(String newTitle) {
		title = newTitle;
	}

	@Override
	public void addPlayer(ScoreboardPlayer sPlayer) {
		TitleHandler titleHandler = sPlayer.getDisplay().makeTitle(title);
		addPlayerHandler(titleHandler);
	}

	@Override
	public String getTitle() {
		return title;
	}

}
