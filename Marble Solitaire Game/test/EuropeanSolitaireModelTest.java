import org.junit.Test;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
/**
 * Tests for EuropeanSolitaireModel
 */
public class EuropeanSolitaireModelTest {
  EuropeanSolitaireModel model = new EuropeanSolitaireModel();

  @Test
  /**
   * Tests the move method in EuropeanSolitaireModel
   */
  public void testMove() {
    // tests vertical movement
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(1,3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3,3));
    model.move(1,3,3,3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1,3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3,3));

    // tests horizontal movement
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,3));
    model.move(2,1,2,3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,3));
  }

  @Test
  /**
   * Tests the getBoardSize method in EuropeanSolitaireModel
   */
  public void testGetBoardSize() {
    assertEquals(7, model.getBoardSize());
  }

  @Test
  /**
   Tests the getSlotAt method in EuropeanSolitaireModel
   */
  public void testGetSlotAt() {
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(5, 3));
  }

  @Test
  /**
   Tests the getScore method in EuropeanSolitaireModel
   */
  public void testGetScore() {
    assertEquals(model.getScore(), 36);
    model.move(1,3,3,3);
    assertEquals(model.getScore(), 35);
    model.move(4,3,2,3);
    assertEquals(model.getScore(), 34);
  }

  @Test
  /**
   Tests initializeBoard method in EuropeanSolitaireModel
   */
  public void testIntializeBoard() {
    assertEquals(model.getSlotAt(3,3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(0,0), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(model.getSlotAt(1,1), MarbleSolitaireModelState.SlotState.Marble);
  }

  @Test
  /**
   Tests the exception for the second constructor in EuropeanSolitaireModel
   */
  public void testSecondConstructor() {
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(0, 0));
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(-5, 1));
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(4, 17));
  }

  @Test
  /**
   Tests the exception for the third constructor in EuropeanSolitaireModel
   */
  public void testThirdConstructor() {
    assertThrows(NegativeArraySizeException.class,
            () -> new EuropeanSolitaireModel(-3));
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(4));
  }

  @Test
  /**
   Tests the exception for the fourth constructor in EuropeanSolitaireModel
   */
  public void testFourthConstructor() {
    assertThrows(NegativeArraySizeException.class,
            () -> new EuropeanSolitaireModel(-5, 3,3));
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(2, 4, 4));
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(7, 17, 0));
    assertThrows(IllegalArgumentException.class,
            () -> new EuropeanSolitaireModel(5, 42, 5));
  }

  @Test
  /**
   Tests the exception in the move method
   */
  public void testMoveException() {
    assertThrows(IllegalArgumentException.class,
            () -> model.move(2,4,2,1));
    assertThrows(IllegalArgumentException.class,
            () -> model.move(1, 1,1,1));
  }

  @Test
  /**
   Tests the exception in the getSlotAt method
   */
  public void testGetSlotAtException() {
    assertThrows(IllegalArgumentException.class,
            () -> model.getSlotAt(12,4));
  }
}
