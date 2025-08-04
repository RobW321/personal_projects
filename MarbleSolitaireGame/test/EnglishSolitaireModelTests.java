import org.junit.Test;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import static org.junit.Assert.*;

/**
 * Tests for EnglishSolitaireModel
 */
public class EnglishSolitaireModelTests {
  EnglishSolitaireModel model = new EnglishSolitaireModel();
  EnglishSolitaireModel model2 = new EnglishSolitaireModel(5);
  EnglishSolitaireModel model3 = new EnglishSolitaireModel(7);

  @Test
  /**
   * Tests the move method in EnglishSolitaireModel
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
   * Tests the isGameOver method in EnglishSolitaireModel
   */
  public void testIsGameOver() {
    assertFalse(model.isGameOver());
    model.move(5,3,3,3);
    model.move(2, 3, 4, 3);
    model.move(0, 3, 2, 3);
    assertFalse(model.isGameOver());
    model.move(3, 1, 3, 3);
    model.move(3, 4, 3, 2);
    model.move(3, 6, 3, 4);
    assertTrue(model.isGameOver());
  }

  @Test
  /**
   * Tests the getBoardSize method in EnglishSolitaireModel
   */
  public void testGetBoardSize() {
    assertEquals(7, model.getBoardSize());
    assertEquals(13, model2.getBoardSize());
    assertEquals(19, model3.getBoardSize());
  }

  @Test
  /**
  Tests the getSlotAt method in EnglishSolitiareModel
   */
  public void testGetSlotAt() {
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(5, 3));
  }

  @Test
  /**
  Tests the getScore method in EnglishSolitiareModel
   */
  public void testGetScore() {
    assertEquals(model.getScore(), 32);
    model.move(1,3,3,3);
    assertEquals(model.getScore(), 31);
    model.move(4,3,2,3);
    assertEquals(model.getScore(), 30);
    assertEquals(model2.getScore(), 104);
    assertEquals(model3.getScore(), 216);
  }

  @Test
  /**
  Tests initializeBoard method in EnglishSolitiareModel
   */
  public void testIntializeBoard() {
    assertEquals(model.getSlotAt(3,3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(1,1), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(model.getSlotAt(4,4), MarbleSolitaireModelState.SlotState.Marble);
  }

  @Test
  /**
  Tests the exception for the second constructor
   */
  public void testSecondConstructor() {
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(0, 0));
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(5, 1));
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(4, 17));
  }

  @Test
  /**
  Tests the exception for the third constructor
   */
  public void testThirdConstructor() {
    assertThrows(NegativeArraySizeException.class,
            () -> new EnglishSolitaireModel(-3));
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(4));
  }

  @Test
  /**
  Tests the exception for the fourth constructor
   */
  public void testFourthConstructor() {
    assertThrows(NegativeArraySizeException.class,
            () -> new EnglishSolitaireModel(-5, 3,3));
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(2, 4, 4));
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(7, 17, 0));
    assertThrows(IllegalArgumentException.class,
            () -> new EnglishSolitaireModel(5, 42, 5));
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
