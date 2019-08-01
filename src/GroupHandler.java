import java.util.ArrayList;

public abstract class GroupHandler implements Handler {
	private ArrayList<Handler> handlerList = new ArrayList<>();
	GroupHandler() {
		
	}
	
	protected final ArrayList<Handler> getHandlers() {
		return handlerList;
	}
	
	public abstract void addPlayer(ScoreboardPlayer player);
	
	protected void addPlayerHandler(Handler newHandler) {
		handlerList.add(newHandler);
	}
	
	protected void removePlayerEntry(Handler removedHandler) {
		handlerList.remove(removedHandler);
		removedHandler.remove();
	}

	@Override
	public void remove() {
		for (Handler handler : handlerList) {
			handler.remove();
		}
	}

	@Override
	public void setPosition(int newPosition) {
		for (Handler handler : handlerList) {
			handler.setPosition(newPosition);
		}
	}
}
