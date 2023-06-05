package trapthecat;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TrapTheCatProblemTest {
	
	private static TrapTheCatState initialState = null;
	
	@BeforeClass
	public static void setUp() {
		TrapTheCatProblem problem = new TrapTheCatProblem();
		initialState = problem.initialState();
	}

	
	@Test
	public void testCatInitialSpot() {		
		HexagonValue hexagon = initialState.getHexagonValue(61);
		assertTrue("Wrong initial spot for the Cat", hexagon == HexagonValue.CAT);
	}
	
	
	@Test
	public void testNumberOfInitialBlocked() {
		assertTrue("Wrong number of initial blocked cells", numberOfBlockHexagon(initialState) <= 15);
	}
	
	
	private int numberOfBlockHexagon(TrapTheCatState state) {
		int count =0;
		for (int i = 1; i <= 121; i++) {
			HexagonValue hexagon = state.getHexagonValue(i);
			if (hexagon == HexagonValue.BLOCK) {
				count++;
			}
		}
		
		return count;
	}
}
