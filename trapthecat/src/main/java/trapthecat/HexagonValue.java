package trapthecat;
/**
 * Captures the Status of a specific hexagon.
 * It can be:
 * FREE: represent the empty hexagon.
 * BLOCK: mark for block hexagon, the player move.
 * CAT: mark for cat position.
 */
public enum HexagonValue {
  /**
   * Representing the empty hexagon.
   */
  FREE,
  /**
   * Representing the mark for block hexagon, the player move.
   */
  BLOCK,
  /**
   * Representing the mark for cat position.
   */
  CAT
}
