package engines;

import java.util.List;

import adversarysearch.EngineAdversary;
import adversarysearch.StateAdversary;
import adversarysearch.StateProblemAdversary;

/**
 * Engine implementing adversary search with minimax tree with alpha-beta pruning.
 * @param <P> This describes a StateProblemAdversary subtype parameter
 * @param <S> This describes a StateAdversary subtype parameter
 */
public class MiniMaxABEngine<P extends StateProblemAdversary<S>, S extends StateAdversary> implements EngineAdversary<P, S> {
    /**
    * Problem representation.
    */
    private P sp;

    /**
    * indicates the maximum depth used for the search.
    */
    private int maxDepth = 1;

    /**
    * Engine constructor.
    * @param sp  problem representation.
    */
    public MiniMaxABEngine(P sp) {
        this.sp = sp;
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
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null.");
        }

        return maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }

    private int maxValue(S state, int alpha, int beta, int depth) {
        if (state.end() || depth == getMaxDepth()) {
            return state.value();
        }

        int value = Integer.MIN_VALUE;
        for (S childState : sp.getSuccessors(state)) {
            value = Math.max(value, minValue(childState, alpha, beta, depth + 1));
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                return value;
            }
        }
        return value;
    }

    private int minValue(S state, int alpha, int beta, int depth) {
        if (state.end() || depth == getMaxDepth()) {
            return state.value();
        }

        int value = Integer.MAX_VALUE;
        for (S childState : sp.getSuccessors(state)) {
            value = Math.min(value, maxValue(childState, alpha, beta, depth + 1));
            beta = Math.min(beta, value);
            if (alpha >= beta) {
                return value;
            }
        }
        return value;
    }

    /**
    * {@inheritDoc}
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
    
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int depth = 0;
    
        S bestSuccessor = null;
        int bestValue = Integer.MIN_VALUE;
    
        for (S successor : succ) {
            int value = minValue(successor, alpha, beta, depth + 1);
            if (value > bestValue) {
                bestValue = value;
                bestSuccessor = successor;
            }
            alpha = Math.max(alpha, bestValue);
        }
    
        return bestSuccessor;
    }

    /**
    * {@inheritDoc}
    */
    public void report() {
        System.out.println("Falta implementar este método!");
    }
}
