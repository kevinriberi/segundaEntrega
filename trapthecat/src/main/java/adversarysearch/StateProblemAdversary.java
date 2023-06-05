package adversarysearch;

import java.util.List;


/**
 * Interface which defines the basic elements necessary for
 * characterising a problem as a search. Instances of these
 * problems should implement this interface, to be able to use
 * the adversary search strategies.
 * @param <S> is the class characterising the state for the problem.
 * @author Nazareno Aguirre
 */
public interface StateProblemAdversary<S extends StateAdversary> {
  /**
   * Returns the initial state corresponding to the problem.
   * Concrete implementations of AdversarySearchProblem must
   * implement this routine, to indicate the starting point for
   * the (adversary) search.
   * @return the initial state for the problem.
   */
  S initialState();

  /**
   * Returns the list of successor states for a given state, in the
   * context of the current problem. Concrete implementations of
   * StateProblemAdversary must implement this routine, to indicate
   * the 'advance' rules (or game rules) for the search.
   * @param s is the state for which its successors are being
     computed.
   * @return the list of successor states of state.
   * @throws IllegalArgumentException if state==null.
   */
  List<S> getSuccessors(S s);



  /**
   * Indicates the least possible value for a state in the problem.
   * Together with maxValue(), it determines an interval in which
   * values for states must range. This value must be
   * strictly smaller than maxValue().
   * @return an integer value, representing the least possible value
      for the problem.
   */
  int minValue();

  /**
   * Indicates the greatest possible value for a state in the problem.
   * Together with minValue(), it determines an interval in which
   * values for states must range.
   * @return an integer value, representing the greatest possible value
     for the problem.
     This value must be strictly greater than minValue().
   */
   int maxValue();

}
