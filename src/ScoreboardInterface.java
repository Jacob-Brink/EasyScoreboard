
public interface ScoreboardInterface {
	public String addTitleLine(String title);
	public void setTitleLine(String key, String newTitle);
	
	public String addScoreLine(String name, double value);
	public void setScoreLine(String key, double newValue);
	
	public String addTimerLine(String name, double duration);
	
	public void removeEntry(String key);
}
