package EasyScoreboard;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tester {
	private Player p;
	private ScoreDisplay scoreDisplay;
	private ArrayList<CallbackWrapper> tests = new ArrayList<>();
	
	public abstract class CallbackWrapper {
		public abstract void Function();
	}
	
	public void message(String message) {
		p.sendMessage("" + ChatColor.BOLD + "Test: " + ChatColor.RESET + message);
	}
	
	public void testInsertRemoval() {
		message("Testing insertion and removal...");
		scoreDisplay.setLine("First Line", 0);
		scoreDisplay.setLine("Third Line", 2);
		scoreDisplay.setLine("Second Line", 1);
		scoreDisplay.setLine("Failed Removal Test", 3);
		scoreDisplay.removeEntry(3);
		scoreDisplay.setLine("Last Line", 14);
		scoreDisplay.setLine("", 13);
		scoreDisplay.setLine("Next line should be blank", 12);
	}
	
	public void testSpacingAfterRemoval() {
		scoreDisplay.removeEntry(14);
		message("Removing 14");
	}
	
	
	public void testClearBoard() {
		message("Testing clear scoreboard...");
		scoreDisplay.clearScoreBoard();
	}
	
	private void testInsertAfterClearing() {
		message("Testing inserting lines after clearing board...");
		scoreDisplay.setLine("First Line", 0);
		scoreDisplay.setLine("Last Line", 14);
	}
	
	public void testSetTitle() {
		message("Testing set title...");
		scoreDisplay.setTitle("New Title");
	}
	
	public void testErrors() {
		message("Testing range errors...");
		try {
			scoreDisplay.setLine("StackOverflowError", 16);
			message("Exceeding upper range limit failed");
		} catch (IndexOutOfBoundsException e) {
			message("Upper limit passed.");
		}
		
		try {
			scoreDisplay.setLine("StackOverflowError", -1);
			message("Exceeding lower range limit failed");
		} catch (IndexOutOfBoundsException e) {
			message("Lower limit passed");
		}
	}
	
	public void testTitleSpaceBug() {
		message("Testing title space bug");
		scoreDisplay.setTitle("");
	}
	
	private void setupTests() {
		tests = new ArrayList<>();
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testInsertRemoval();
			}
			
		});
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testSpacingAfterRemoval();
			}
			
		});
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testSetTitle();
			}
			
		});
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testClearBoard();
			}
			
		});		
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testInsertAfterClearing();
			}
			
		});
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testErrors();
			}
			
		});
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				testTitleSpaceBug();
			}
			
		});
		
		tests.add(new CallbackWrapper() {

			@Override
			public void Function() {
				scoreDisplay.clearScoreBoard();
				message("Testing complete!");
			}
			
		});
		
	}
	
	private void runTest(int testNum) {
		final int num = testNum;
		Bukkit.getScheduler().runTaskLater(EasyScoreboard.getPlugin(), new Runnable() {
			public void run() {
				tests.get(num).Function();
				if ((num+1) < tests.size()) {
					runTest(num+1);
				}
				
			}
		}, 3*20);
	}
	
	Tester(Player player, ScoreDisplay scoreDisplayArg) {
		scoreDisplay = scoreDisplayArg;
		p = player;
	}
	
	public void runTests() {
		message("" + ChatColor.BOLD + "Running EasyScoreboard Tests");
		setupTests();
		runTest(0);
	}

	
}
