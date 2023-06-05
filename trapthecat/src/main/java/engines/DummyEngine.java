package engines;

import java.util.List;

import adversarysearch.EngineAdversary;
import adversarysearch.StateAdversary;
import adversarysearch.StateProblemAdversary;

/**
 * Engine implementing dummy moves.
 * @author sonia
 * @param <P> This describes a StateProblemAdversary subtype parameter
 * @param <S> This describes a StateAdversary subtype parameter
 */
public class DummyEngine<P extends StateProblemAdversary<S>, S extends StateAdversary> implements EngineAdversary<P, S> {
  /**
   * Problem representation.
   */
  private P sp;

  /**
   * indicates the maximum depth used for the search.
   */
  private int maxDepth;

  /**
   * Engine constructor.
   * @param sp  problem representation.
   */
  public DummyEngine(P sp) {
    this.sp = sp;
  }

  /**
   * Computes move to take, according to first successor decision.
   * @param state is the state from which to move.
   * @return the resulting state to move to, according to first successor decision.
   */
  public S computeSuccessor(S state) {
    if (state == null) {
      throw new IllegalArgumentException("null state");
    }
    if (state.end()) {
      throw new IllegalArgumentException("can't move from a final state");
    }
    List<S> succ = sp.getSuccessors(state);
    if (succ.isEmpty()) {
      throw new IllegalArgumentException("There are no successors from current state");
    }
    return succ.get(0);
  }

  /**
   * {@inheritDoc}
   */
  public int getMaxDepth() {
    return this.maxDepth;

  }

  /**
   * {@inheritDoc}
   */
  public void setMaxDepth(int maxDepth) {
    this.maxDepth = maxDepth;

  }

  /**
   * {@inheritDoc}
   */
  public P getProblem() {
    return this.sp;
  }

  /**
   * {@inheritDoc}
   */
  public void setProblem(P p) {
    this.sp = p;
  }

  /**
   * {@inheritDoc}
   */
  public int computeValue(S state) {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public void report() {
    System.out.println("This is a Dummy engine, you should replace me");
  }

}
