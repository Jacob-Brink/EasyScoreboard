
public class ScoreGroup extends GroupHandler implements ScoreHandler {
	private double value;
	private String name;
	ScoreGroup(String n, double v) {
		super();
		name = n;
		value = v;
	}
	
	@Override
	public void remove() {
		super.remove();
	}

	@Override
	public void setPosition(int newPosition) {
		super.setPosition(newPosition);
	}

	@Override
	public void setScore(double newValue) {
		value = newValue;
		for (Handler handler : getHandlers()) {
			ScoreEntry scoreEntry = (ScoreEntry) handler;
			scoreEntry.setScore(newValue);
		}
	}

	@Override
	public void setName(String newName) {
		name = newName;
		for (Handler handler : getHandlers()) {
			ScoreEntry scoreEntry = (ScoreEntry) handler;
			scoreEntry.setName(newName);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void addPlayer(ScoreboardPlayer player) {
		ScoreHandler newHandler = player.getDisplay().makeScore(name, value);
		addPlayerHandler(newHandler);
	}

}
