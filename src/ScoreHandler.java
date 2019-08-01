
public interface ScoreHandler extends Handler {
	public abstract void setScore(double newValue);
	public abstract void setName(String newName);
	public abstract String getName();
	public abstract double getValue();
}
