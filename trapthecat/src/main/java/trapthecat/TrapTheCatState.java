package trapthecat;

import java.util.LinkedList;

import adversarysearch.StateAdversary;

public class TrapTheCatState implements StateAdversary {

  /**
   * representation of the ScreenBoard.
   */
  private ScreenBoard screenBoard;

  private StateAdversary parent = null;

  private boolean maxTurn;

  /**
   * Default Constructor class.
   */
  public TrapTheCatState() {
    screenBoard = new ScreenBoard();
    maxTurn = false;
  }

  public TrapTheCatState(ScreenBoard screenBoard, TrapTheCatState parent){
    this.screenBoard = screenBoard;
    this.parent = parent;
  }
  /**
   * {@inheritDoc}
   */
  public boolean isMax() {
    return maxTurn;
  }

  /**
   * {@inheritDoc}
   */
  public StateAdversary getParent() {
    
    return parent;
  }

  /**
   * {@inheritDoc}
   */
  public boolean end() {
    if (screenBoard.catInFrontier()){
      return true;
    }
    
    // verify if the cat is blocked
    LinkedList<Integer> adjHexagons = screenBoard.adjacentValues(screenBoard.getCatPosition());
    //si el gato no está en el borde, va a tener 6 celdas adyacentes
    boolean blocked = true;
    for (int i = 0; i < adjHexagons.size(); i++){
      int currentPosition = adjHexagons.get(i);
      if (screenBoard.showHexagonValue(currentPosition) == HexagonValue.FREE){
        blocked = false;
        break;
      }
    }
    if (blocked){
      return true;
    }

    //si llego hasta aca es porque el estado no es un estado final
    return false;
  }

  public LinkedList<Integer> freeAdjacent (){
    int catPosition = screenBoard.getCatPosition();
    
    return screenBoard.freeAdjacentValues(catPosition);
  }

  /**
   * {@inheritDoc}
   */
  public int value() {
    if (this.end()){
      if (screenBoard.catInFrontier()){
        return 1000;
      }else{
        return 0;
      }
    }

    int result;
    int distance;

    if (! this.isMax()){
      //System.out.println("entro a value por falso");
      //en teoria mientras mas alejado del centro es mejor
      int catPosition = screenBoard.getCatPosition();
      int row = screenBoard.getRow(catPosition);
      int column = screenBoard.getColumn(catPosition);

      //en principio no hace falta calcular la raiz cuadrada
      distance = (5-row)*(5-row) + (5-column)*(5-column);

      result = distance;
    } else {
      //System.out.println("Entro a value por verdadero");
      LinkedList<Integer> adjacent = this.screenBoard.adjacentValues(this.getScreenBoard().getCatPosition());
      int bestValue = 0;

      for (int i = 0; i < adjacent.size(); i++){
        int value = adjacent.get(i);
        if (screenBoard.showHexagonValue(value) == HexagonValue.BLOCK){
          int row = screenBoard.getRow(value);
          int column = screenBoard.getColumn(value);

          distance = (5-row)*(5-row) + (5-column)*(5-column);
          if (distance > bestValue){
            bestValue = distance;
          }
        }
      }
      //acá mientras "mejor bloquee" menor es la valoración del estado
      result = bestValue;
      //BORRAR ESTE COMENTARIO
      //System.out.println("Llegué hasta acá: value es" + result);
      //this.getScreenBoard().toString();
      //System.out.println("Acá no imprimo un tablero de mas?");
    }

    return result;
  }

  public void setHexagonValue(int hexagon, HexagonValue value){
    screenBoard.markHexagon(hexagon-1, value);
  }

  public void setMax(boolean value){
    maxTurn = value;
  }
  
  /**
   * Creates and returns a copy of this State.
   * @return a copy of this State.
   */
  public TrapTheCatState clone() {
    TrapTheCatState clone = new TrapTheCatState();
    clone.getScreenBoard().setCatPosition(this.screenBoard.getCatPosition());

    for (int i = 0; i < 121; i++){
      HexagonValue value = this.screenBoard.showHexagonValue(i);
      clone.screenBoard.markHexagon(i, value);
    }

    return clone;
  }

  /**
   * Returns the content of a specific hexagon.
   * @param hexagonNumber  position.
   * @return the content of a specific hexagon.
   */
  public HexagonValue getHexagonValue(int hexagonNumber) {
    //como comenzamos numerando desde el cero en la matriz, hay que restar uno
    HexagonValue value =  screenBoard.showHexagonValue(hexagonNumber-1);
    return value;
  }

  /**
   * {@inheritDoc}
   */
  public Object ruleApplied() {
    throw new UnsupportedOperationException("method not yet implemented");
  }

  /**
   * Produces string representation of the state.
   * prints Screen from this State, and additional relevant information.
   * @return a string representing the current State.
     For instance, a screenBoard with only two occupied hexagon:
     hexagon number 1 = blocked and hexagon number 13 = the cat
     should be something like this:
     [XXX,002,003,004,005,006,007,008,009,010,011]
       [012, C ,014,015,016,017,018,019,020,021,022]
     [023,024,025,026,027,028,029,030,031,032,033]
       [034,035,036,037,038,039,040,041,042,043,044]
     [045,046,047,048,049,050,051,052,053,054,055]
       [056,057,058,059,060,061,062,063,064,065,066]
     [067,068,069,070,071,072,073,074,075,076,077]
       [078,079,080,081,082,083,084,085,086,087,088]
     [089,090,091,092,093,094,095,096,097,098,099]
       [100,101,102,103,104,105,106,107,108,109,110]
     [111,112,113,114,115,116,117,118,119,120,121]
*/
  @Override
  public String toString() {
    return screenBoard.toString();
    //TODO:You can add relevant information from this State.
  }

  public ScreenBoard getScreenBoard(){
    return screenBoard;
  }

}
