package trapthecat;

import java.util.LinkedList;
import java.util.Random;

public class ScreenBoard {
    // cant of rows and columns
    private int cantHexagon = 11; 

    //matrix to represent the board
    private HexagonValue[][] board = new HexagonValue[cantHexagon][cantHexagon];

    // value of position of the Cat (first value is 00 not 01)
    private int catPosition = 60;
 
    public ScreenBoard() { 
        for(int i = 0; i < cantHexagon; i++){
            for(int j = 0; j < cantHexagon; j++){
                board[i][j] =  HexagonValue.FREE;
            }
        }

        int cantBloqueadas = 15;
        int rango = 121;
        Random random = new Random();

        for (int i = 0; i < cantBloqueadas; i++){
            int aleatorio = random.nextInt(rango);

            int row = getRow(aleatorio);
            int column = getColumn(aleatorio);

            board[row][column] = HexagonValue.BLOCK;
        }

        board[5][5] = HexagonValue.CAT;
        catPosition = 60;
    }

    public HexagonValue[][] getBoard(){
        return board;
    }

    public void setCatPosition(int catPosition){
        this.catPosition = catPosition;
    }

    public int getCatPosition(){
        return catPosition;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int hexagonPos;
        for (int i = 0; i < cantHexagon; i++) {
            if(i % 2 != 0){
               sb.append(" ");
            } 
            for (int j = 0; j < cantHexagon; j++){
                HexagonValue currentHexagonValue = board[i][j];
                switch (currentHexagonValue) {
                    case FREE: 
                        hexagonPos = (i * 11) + j;
                        sb.append(String.format("%03d", hexagonPos + 1));
                        break;
                    case BLOCK:
                        sb.append("XXX");
                        break;
                    case CAT:
                        sb.append(" C ");
                        break;
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * CHEQUEAR PERO SE PODRÍA HACER UN CHEQUEO DEL NUMERO ANTES DE HACER 
     * TAMBIEN SE PODRIA HACER UN CHEQUEO SI QUEREMOS BLOQUEAR DONDE ESTA EL GATO
     * mark a hexagon in the board
     * @param hexagonValue hexagon to be marked
     * @param value the value of the hexagon to be marked
     */
    public void markHexagon(int hexagonValue, HexagonValue value){

        int row = getRow(hexagonValue);
        int column = getColumn(hexagonValue);

            switch (value) {
              case  FREE:
                  board[row][column] = HexagonValue.FREE;
                  break;
              case BLOCK: 
                  board[row][column] = HexagonValue.BLOCK;
                  break;
              case CAT:
                  //if (isAdjacent(hexagonValue, catPosition)){
                    //aca debería hacer un chequeo para ver que no esté bloqueada la posición
                    markHexagon(catPosition, HexagonValue.FREE);
                    board[row][column] = HexagonValue.CAT;
                    catPosition = hexagonValue;
                  //}
                  break;
              }
      }

    /**
     * @param hexagonValue value of hexagon
     * @return value of the row of hexagonValue
     */
    public int getRow(int hexagonValue){
        return hexagonValue / 11;
    }

    /**
     * @param hexagonValue value of hexagon
     * @return value of the column of hexagonValue
     */
    public int getColumn(int hexagonValue){
        return hexagonValue % 11;
    }

    /**
     * indicate if the cat is in a frontier hexagon
     * @param catHexagon current hexagon of the cat 
     * @return true if the cat is in a frontier position
     */
    public boolean catInFrontier(){

        if((getRow(catPosition) == 0 || getRow(catPosition) == 10)
        || (getColumn(catPosition) == 0 || getColumn(catPosition) == 10)) {
            return true;
        }
        return false;
    }
    
    /**
     * return a list of the adjacents hxagons of the cat
     * @param catHexagon actual hexagon position of the cat
     * @return list of adjacents hexagons of the cat
    */
    public LinkedList<Integer> adjacentValues(int hexagon) {
        LinkedList<Integer> adjacent = new LinkedList<Integer>();
        int catRow = getRow(hexagon);
        int catColumn  = getColumn(hexagon);
        if(!catInFrontier()) {
            //si la fila es impar
            if(catRow % 2 == 0) {
                adjacent.add(catRow * 11 + (catColumn - 1));
                adjacent.add(catRow * 11 + (catColumn + 1));
                adjacent.add((catRow - 1) * 11 + catColumn);
                adjacent.add((catRow + 1) * 11 + catColumn);
                adjacent.add((catRow - 1) * 11 + (catColumn - 1));
                adjacent.add((catRow + 1) * 11 + (catColumn - 1));
            //si la fila es par
            } else {
                adjacent.add(catRow * 11 + (catColumn - 1));
                adjacent.add(catRow * 11 + (catColumn + 1));
                adjacent.add((catRow - 1) * 11 + catColumn);
                adjacent.add((catRow + 1) * 11 + catColumn);
                adjacent.add((catRow - 1) * 11 + (catColumn + 1));
                adjacent.add((catRow + 1) * 11 + (catColumn + 1));
            }
        }

        return adjacent;
    }

    public LinkedList<Integer> freeAdjacentValues (int hexagon){
        LinkedList<Integer> allAdjacent = adjacentValues(hexagon);
        LinkedList<Integer> result = new LinkedList<Integer>();

        for (int i = 0; i < allAdjacent.size(); i++){
            int currentValue = allAdjacent.get(i);
            if (showHexagonValue(currentValue) == HexagonValue.FREE){
                result.add(currentValue);
            }
        }

        return result;
    }

    /**
     * show the current value of the hexagon
     * @param hexagonNumber hexagon to be showed
     * @return the value of the hexagon passed as a parameter
     */
    public HexagonValue showHexagonValue(int hexagonNumber){
        //acá podría hacer un chequeo del numero tambien
        // Row of the hexagon
        int row = getRow(hexagonNumber);
        // Column of the hexagon
        int column = getColumn(hexagonNumber);

        return board[row][column];
    }
}

    // public static void main(String[] args){
    //     HexagonValue value = HexagonValue.BLOCK;
    //     ScreenBoard s = new ScreenBoard();
    //     System.out.println(s.toString());
    //     System.out.println(s.showHexagonValue(32));
    //     s.markHexagon(32, value);
    //     System.out.println( );
    //     System.out.println(s.toString());
    //     System.out.println( );
    //     System.out.println(s.showHexagonValue(32));
    //     value = HexagonValue.FREE;
    //     s.markHexagon(32, value);
    //     System.out.println(s.toString());
    //     System.out.println(s.showHexagonValue(32));
    //     value = HexagonValue.CAT;
    //     s.markHexagon(62, value);
    //     System.out.println(s.toString());
    //     System.out.println(s.showHexagonValue(62));
    //     s.markHexagon(71, value);
    //     System.out.println(s.toString());
    //     System.out.println(s.showHexagonValue(72));
    //     s.markHexagon(82, value);
    //     System.out.println(s.toString());
    //     System.out.println(s.showHexagonValue(83));
    // }


