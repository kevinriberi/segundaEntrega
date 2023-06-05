package trapthecat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.InputMismatchException;

import engines.MiniMaxABEngine;
import adversarysearch.EngineAdversary;

/**
 *
 *
 */
public class App {
	/**
	 * Trap the cat Game.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {

		TrapTheCatProblem problem = new TrapTheCatProblem();
		TrapTheCatState state = problem.initialState();
		EngineAdversary<TrapTheCatProblem, TrapTheCatState> miniMaxAB = new MiniMaxABEngine<>(problem);
		int option = readMenuInput();
		switch (option) {
			case 1:
				humanCatMode(state, miniMaxAB);
				break;
			case 2:
				automaticMode(state, miniMaxAB);
				break;
			default:
				System.out.println("INVALID OPTION");
				break;
		}


	}

	private static void automaticMode(TrapTheCatState state, EngineAdversary<TrapTheCatProblem, TrapTheCatState> engine) {
		boolean catTurn = false; // max player is computer.
		while (!state.end()) {
			if (catTurn) {
				state.setMax(false);
				System.out.println("Cat turn");
				state = engine.computeSuccessor(state);
			} else {
				state.setMax(true);
				System.out.println("Computer block turn");
				state = engine.computeSuccessor(state);
			}
			catTurn = !catTurn;
			//Show move
			System.out.println(state.toString());
		}
	}

	private static void humanCatMode(TrapTheCatState state, EngineAdversary<TrapTheCatProblem, TrapTheCatState> engine) {
		boolean catTurn = false; // max player is human.
		while (!state.end()) {
			if (catTurn) {
				System.out.println("Cat turn");
				state = engine.computeSuccessor(state);
			} else {
				System.out.println("Human turn");
				int hexagon = readMarkInput();
				state.setHexagonValue(hexagon, HexagonValue.BLOCK);
				state.setMax(false);
			}
			catTurn = !catTurn;
			//Show move
			System.out.println(state.toString());
		}
	}

	private static int readMenuInput() {
		Scanner scan = new Scanner(System.in);
		int option;
		do {
			System.out.println();
			System.out.println("============= Options =============");
			System.out.println("1- Player VS Cat");
			System.out.println("2- Automatic Play");
			System.out.println();
			System.out.print("Write Option: ");
			try {
				option = scan.nextInt();
				if (option < 1 || option > 2) {
					option = -1;
					System.out.println("\"Wrong input!!!, try again\"");
				}
			} catch (InputMismatchException e) {
				option = -1;
				System.out.println("\"Wrong input!!!, try again\"");
				scan.nextLine();
			}
		} while (option < 0);
		return option;

	}

	private static int readMarkInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
		int hexagon = -1;
		System.out.print("Mark hexagon, write number position (1-121)");
		try {
			s = br.readLine();
		} catch (IOException e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		if (s == null) {
			System.out.println("Wrong input!");
		} else {
			try {
				hexagon = Integer.parseInt(s);
				if (hexagon < 1 || hexagon > 121) {
					System.out.println("Wrong input!!!, try again");
					readMarkInput();
				}
			} catch (NumberFormatException nfe) {
				System.err.println("Invalid input form! \n try again...");
				readMarkInput();
			}
		}
		System.out.println("You have played in the hexagon number: " + hexagon);
		return hexagon;
	}
}