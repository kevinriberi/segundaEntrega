package trapthecat;

import adversarysearch.StateProblemAdversary;

import java.util.LinkedList;
import java.util.List;

public class TrapTheCatProblem implements StateProblemAdversary<TrapTheCatState> {

  /**
   * {@inheritDoc}
   */
  public TrapTheCatState initialState() {
    return new  TrapTheCatState();
  }

  /**
   * {@inheritDoc}
   */
  public List<TrapTheCatState> getSuccessors(TrapTheCatState s) {
    LinkedList<TrapTheCatState> result = new LinkedList<TrapTheCatState>();
    if (! s.isMax()){
      //System.out.println("Entro a getSuccessor por falso");
      LinkedList<Integer> freeAdjacent = s.freeAdjacent();
      int catPosition = s.getScreenBoard().getCatPosition();

      for (int i = 0; i < freeAdjacent.size(); i++){
        int newCatPosition = freeAdjacent.get(i);
        TrapTheCatState succ = s.clone();

        succ.getScreenBoard().markHexagon(catPosition, HexagonValue.FREE);
        succ.getScreenBoard().markHexagon(newCatPosition, HexagonValue.CAT);

        result.add(succ);
      }
    } else {
      //System.out.println("Entro a getSuccessors por verdadero");
      //BORRAR ESTE COMENTARIO
      //System.out.println("Entro por acá porque isMax es verdadero!");
      for (int i = 0; i < 121; i++){
        HexagonValue currentValue = s.getScreenBoard().showHexagonValue(i);
        if (currentValue == HexagonValue.FREE){
          int catPosition = s.getScreenBoard().getCatPosition();
          TrapTheCatState succ = s.clone();
          succ.setMax(! succ.isMax());


          succ.getScreenBoard().markHexagon(i, HexagonValue.BLOCK);
          succ.getScreenBoard().setCatPosition(catPosition);
          result.add(succ);
        }
      }
    }
    return result;
  }

  /**
  * {@inheritDoc}
  */
  public int minValue() {
    return 0;
  }

  /**
  * {@inheritDoc}
  */
  public int maxValue() {
    return 1000;
  }
}
