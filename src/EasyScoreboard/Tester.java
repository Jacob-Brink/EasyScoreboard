package EasyScoreboard;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tester {
	private static Player p;
	private static ScoreDisplay scoreDisplay;
	private static ArrayList<CallbackWrapper> tests = new ArrayList<>();
	
	public static abstract class CallbackWrapper {
		public abstract void Function();
	}
	
	public static void message(String message) {
		p.sendMessage("" + ChatColor.BLUE + "EasyScoreboard: " + message);
	}
	
	public static void testInsertRemoval() {
		message("Testing insertion and removal...");
		scoreDisplay.setLine("First Line", 0);
		message("\"First Line\"");
		
		scoreDisplay.setLine("Third Line", 2);
		message("\"Third Line\"");
		
		scoreDisplay.setLine("Second Line", 1);
		message("\"Second Line\"");
		
		scoreDisplay.setLine("Failed Removal Test", 3);
		scoreDisplay.removeEntry(3);
		
		scoreDisplay.setLine("Last Line", 14);
		message("\"Last Line\"");
		
	}
	
	public static void testSpacingAfterRemoval() {
		scoreDisplay.removeEntry(14);
		message("There should be no spaces unless it is in between two lines");
	}
	
	
	public static void testClearBoard() {
		message("" + ChatColor.BOLD + "Testing clear scoreboard...");
		scoreDisplay.clearScoreBoard();
		message("scoreboard should not have anything on it and should not be visible");
	}
	
	private static void testInsertAfterClearing() {
		message("Testing inserting lines after clearing board...");
		scoreDisplay.setLine("First Line", 0);
		message("\"First Line\"");
		
		scoreDisplay.setLine("Last Line", 14);
		message("\"Last Line\"");
	}
	
	public static void testSetTitle() {
		message("Testing set title...");
		scoreDisplay.setTitle("New Title");
	}
	
	public static void testErrors() {
		message("Testing range errors...");
		try {
			scoreDisplay.setLine("StackOverflowError", 16);
			message("Exceeding upper range limit failed");
		} catch (IndexOutOfBoundsException e) {
			message("Upper limit passed.");
			message("Error Message: " + e.getMessage());
		}
		
		try {
			scoreDisplay.setLine("StackOverflowError", -1);
			message("Exceeding lower range limit failed");
		} catch (IndexOutOfBoundsException e) {
			message("Lower limit passed");
			message("Error Message: " + e.getMessage());
		}
	}
	
	private static void setupTests() {
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
				scoreDisplay.cleanup();
				message("Testing complete!");
			}
			
		});
		
	}
	
	public static void runTest(int testNum) {
		final int num = testNum;
		Bukkit.getScheduler().runTaskLater(EasyScoreboard.getPlugin(), new Runnable() {
			public void run() {
				tests.get(num).Function();
				if ((num+1) < tests.size()) {
					runTest(num+1);
				}
				
			}
		}, 5*20);
	}
	
	public static void runTests(Player player) {
		scoreDisplay = EasyScoreboard.getDisplay(player);
		p = player;
		message("Running EasyScoreboard Tests");
		setupTests();
		runTest(0);
	}

	
}
