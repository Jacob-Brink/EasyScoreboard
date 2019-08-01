
public class FlashEffect implements TextEffect {	
	private String firstColor, secondColor;
	private int tickDuration = 10;
	private int ticks = 0;
	private boolean firstOn = true;
	
	FlashEffect(String first, String second, double durationSeconds) {
		firstColor = first;
		secondColor = second;
		ticks = 0;//(int) (durationSeconds / EasyScoreboard.tickDuration);
	}
	
	@Override
	public String tickProcess(String inputString) {
		EasyScoreboard.getPlugin().getLogger().info("Ticks for flash effeect are " + ticks);
		if (++ticks >= tickDuration) {
			firstOn = !firstOn;
			ticks = 0;
		}
		
		if (firstOn) {
			return "" + firstColor + inputString;
		} else {
			return "" + secondColor + inputString;
		}
		
	}
	
}
