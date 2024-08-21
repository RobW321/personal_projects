import org.junit.Test;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import static org.junit.Assert.*;

/**
 * Tests for TriangleSolitaireModel
 */
public class TriangleSolitaireModelTest {
  TriangleSolitaireModel model = new TriangleSolitaireModel();
  TriangleSolitaireModel model2 = new TriangleSolitaireModel(7);
  TriangleSolitaireModel model3 = new TriangleSolitaireModel(10);

  @Test
  /**
   * Tests the move method in TriangleSolitaireModel
   */
  public void testMove() {
    // tests movement going up and to the left
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(1,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(0,0));
    model.move(2,2,0,0);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0,0));

    // tests movement going right
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,2));
    model.move(2,0,2,2);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,2));

    // tests movement going up and to the right
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(4,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2,0));
    model.move(4,0,2,0);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(4,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3,0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2,0));

    // tests movement going left
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3,0));
    model.move(3,2,3,0);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3,2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3,0));
  }

  @Test
  /**
   * Tests the isGameOver method in TriangleSolitaireModel
   */
  public void testIsGameOver() {
    assertFalse(model.isGameOver());
    model.move(2,2,0,0);
    model.move(4, 4, 2, 2);
    model.move(3, 1, 1, 1);
    model.move(4, 2, 4, 4);
    model.move(4, 0, 4, 2);
    assertFalse(model.isGameOver());
    model.move(2, 0, 4, 0);
    model.move(1, 1, 3, 3);
    model.move(0, 0, 2, 0);
    model.move(4, 4, 2, 2);
    assertTrue(model.isGameOver());
  }

  @Test
  /**
   * Tests the getBoardSize method in TriangleSolitaireModel
   */
  public void testGetBoardSize() {
    assertEquals(5, model.getBoardSize());
    assertEquals(7, model2.getBoardSize());
    assertEquals(10, model3.getBoardSize());
  }

  @Test
  /**
   Tests the getSlotAt method in TriangleSolitaireModel
   */
  public void testGetSlotAt() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(4, 2));
  }

  @Test
  /**
   Tests the getScore method in TriangleSolitaireModel
   */
  public void testGetScore() {
    assertEquals(model.getScore(), 14);
    model.move(2,2,0,0);
    assertEquals(model.getScore(), 13);
    model.move(4,4,2,2);
    assertEquals(model.getScore(), 12);
    assertEquals(model2.getScore(), 27);
    assertEquals(model3.getScore(), 54);
  }

  @Test
  /**
   Tests initializeBoard method in TriangleSolitaireModel
   */
  public void testIntializeBoard() {
    assertEquals(model.getSlotAt(0,0), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(4,4), MarbleSolitaireModelState.SlotState.Marble);
  }

  @Test
  /**
   Tests the exception for the second constructor
   */
  public void testSecondConstructor() {
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(-3));
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(0));
  }

  @Test
  /**
   Tests the exception for the third constructor
   */
  public void testThirdConstructor() {
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(5, 1));
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(4, 17));
  }

  @Test
  /**
   Tests the exception for the fourth constructor
   */
  public void testFourthConstructor() {
    assertThrows(NegativeArraySizeException.class,
            () -> new TriangleSolitaireModel(-5, 3,3));
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(2, 4, 4));
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(7, 17, 0));
    assertThrows(IllegalArgumentException.class,
            () -> new TriangleSolitaireModel(5, 42, 5));
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
